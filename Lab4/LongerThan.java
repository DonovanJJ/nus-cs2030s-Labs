/**
 * A boolean condition with parameter x that can be applied to
 * a string.  Returns true if the string is longer than x; false
 * otherwise.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab Lab12J)
 */

class LongerThan implements BooleanCondition<String> {
  
  private int limit;
  public LongerThan(int limit) {
    this.limit = limit;
  }
  
  @Override
  public boolean test(String arg) {
    if (arg.length() > limit) {
      return true;
    }
    return false;
  }
}
