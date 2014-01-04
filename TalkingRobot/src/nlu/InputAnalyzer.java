package nlu;

import java.util.List;

public abstract class InputAnalyzer {

  protected File grammarFile;

  protected PhoenixAdapter phoenix;

  public abstract List<String> analyze(String input);

}