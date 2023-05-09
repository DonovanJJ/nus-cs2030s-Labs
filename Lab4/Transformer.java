/**
 * The Transformer interface that can transform a type T
 * to type U.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

interface Transformer<T, U> {

  U transform(T arg);

}

