package cs2030s.fp;

/**
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

public interface Transformer<T, U> {
  U transform(T arg);
}
