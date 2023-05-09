/**
 * A transformer with a parameter k that takes in an object x 
 * and outputs the last k digits of the hash value of x.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

class LastDigitsOfHashCode implements Transformer<Object, Integer> {

  private int k;

  public LastDigitsOfHashCode(int k)  {
    this.k = k;
  }

  @Override
  public Integer transform(Object obj) {
    int hashCode = obj.hashCode();
    // Type casting done here to return int from Mathpow
    return Math.abs(hashCode % (int) Math.pow(10, this.k));
  }
}
