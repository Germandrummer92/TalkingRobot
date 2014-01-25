#!/bin/bash

if ping -W 5 -c 1 google.com >/dev/null; then
wget --http-user=kit_edu --http-passwd=aphaid1ooJexae --mirror www.studentenwerk-karlsruhe.de/json_external/kit_edu/canteen/$0
fi
