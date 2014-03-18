# ------------------------------------
# sourcing
# ------------------------------------


set thisDir [file dirname [info script]]
source $thisDir/../Communication/messages.tcl
#-------------------------------------
# Settings
# ------------------------------------
set par(name) java
set par(groups) "nlin"
set par(comserver) one4all
set par(commHost) localhost
set par(commPort)   1917

if [catch {itfParseArgv java $argv [list \
    [ list -name         string {} par(name)         {} "module name"  ] \
    [ list -commHost     string {} par(commHost)     {} "comm server host"  ] \
    [ list -commPort     string {} par(commPort)     {} "comm server port"  ] \
    [ list -server       string {} par(comserver)    {} "server name"  ] \
    [ list -audiofile    string {} par(adc)          {} "name of the audiofile" ] \
    [ list -language     string {} par(lang)         {} "language: english" ] \
    [ list -channel      string {} par(channel)      {} "mono or stereo" ] \
    [ list -segmentation string {} par(segmentation) {} "speech segmentation" ] \
    [ list -collectDir   string {} collectDir        {} "dir for data collect" ] \
] } msg] { error "$msg" }

# ----------------------------------------
# entry procedure for comServer
# ----------------------------------------
proc actionReceiver {message} {
    array set arg $message

    set sender   $arg(:sender)
    set language $arg(:language)
    set content  $arg(:content)

    puts "-----------------------------------------------------"
    puts "I got a '$language' message from '$sender':\n'$content'"

    regsub -all {\(} $content "{" content
    regsub -all {\)} $content "}" content

    set content [lindex $content 0]
    set action  [lindex $content 0]
    set args    [lrange $content 1 end]
#    puts "--> '$action' \[[llength $args]\]: '$args'"
    switch -- $action {
	{recog-done}      { eval actionRecogDone      $args }
	{query}           { eval actionQuery          $args }
	{parsetree}       { eval actionParseTree      $args }
	{query-ngram}     { eval actionQueryNGram     $args }
	{segPutsFeatures} { eval actionSegGetFeatures $args }
	default    {
	    puts stderr "ERROR: unknown message type '$action'"
	}
    }
}
proc actionQuery {args} {
    global par

    set query    ""
    set language english

    if [catch {itfParseArgv actionQuery $args [list \
        [ list :text        string  {} query       {} ""  ] \
        [ list :language    string  {} language    {} ""  ] \
        [ list :tstart      string  {} tstart      {} ""  ] \
        [ list :tstop       string  {} tstop       {} ""  ] \
        [ list :enableRules string  {} enableRules {} ""  ] \
    ] } msg] {
        error "$msg"
    }

    set par(hypo) $query

    collect $par(adc) $query $language $tstart $tstop $enableRules
}
# ----------------------------------------
# connect to ComServer
# ----------------------------------------
putsInfo "CONNECT TO SERVER"

#build the interface
set agent [ComAgent "$par(name)" -server "$par(comserver)" -host "$par(commHost)" -port "$par(commPort)"]

#set call back function
$agent configure -action {actionReceiver $message}

#connect to the comServer
$agent connect

#subscribe to groups
$agent subscribe $par(groups)

