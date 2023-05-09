/**
 * A boolean condition with an integer parameter y that can be 
 * apply to another integer x.  Returns true if x is divisible 
 * by y, false otherwise.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

class DivisibleBy implements BooleanCondition<Integer> {
  
  private int divisor;
  public DivisibleBy(int divisor) {
    this.divisor = divisor;
  }

  @Override
  public boolean test(Integer arg) {
    if (arg % divisor == 0) {
      return true;
    } 
    return false;
  }
}
