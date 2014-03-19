package one4all.fipa.net;

public class TypeValueString
{
  public String type;
  public String value;

  public TypeValueString (String paramString1, String paramString2)
  {
    this.type = paramString1;
    this.value = paramString2;
  }

  public String toString()
  {
    return this.type + " " + this.value;
  }
}

/* Location:           /Users/luizhsilva/Downloads/one4all/cebit12/one4all-base/lib/tapas/tapas.jar
 * Qualified Name:     one4all.fipa.net.TypeValueString
 * JD-Core Version:    0.6.2
 */