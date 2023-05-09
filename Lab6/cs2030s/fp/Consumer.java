/**
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

package cs2030s.fp;

/** 
 * functional interface with method consume
 */
@FunctionalInterface
public interface Consumer<T> {
  /**
  * @param <T> Type parameter of input t
  * @param t is the value that will be consumed
  */
  void consume(T t);
}
