/**
 * The Array<T> for CS2030S 
 *
 * @author Chan Jia Jun (Group 12J)
 * @version CS2030S AY22/23 Semester 2
 */
import java.lang.Comparable;

class Array<T extends Comparable<T>> {
  private T[] array;

  public Array(int size) {
    @SuppressWarnings({"unchecked", "rawtypes"})
    T[] tmp = (T[]) new Comparable[size];
    this.array = tmp;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    if (index >= array.length) {
      return null;
    }
    return this.array[index];
  }
  
  // return the counter with shortest queue 
  // (May include a FULL queue  ->check when using in  Bank)
  public T min() {
    T min = array[0];
    for (T item : array) {
      if (item.compareTo(min) < 0) {
        min = item;
      }
    }
    return min;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
