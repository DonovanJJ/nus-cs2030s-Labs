/**
 * Takes an item and return the item in a box.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

class BoxIt<T> implements Transformer<T, Box<T>> {
  @Override
  public Box<T> transform(T item) {
    return Box.of(item);
  }
}
