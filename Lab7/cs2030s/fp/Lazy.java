package cs2030s.fp;

/**
 * Encapsulates Maybe and a producer to achieve lazy evaluations by delaying computation.
 * includes methods
 * (1) two static factory methods
 * (2) get value of Lazy
 * (3) map value in lazy to another based on transformer
 * (4) flatMap transforms Lazy to 
 * (5) filter returns only Lazy containing value determined by Condition
 * (6) Override equals to check if content in two Lazy instances are equal
 * (7) combine values of two Lazy into a single Lazy
 *
 * @author Chan Jia Jun (Group 12J)
 */

public class Lazy<T> {

  /**
   * Stores the producer of Lazy.
   */
  private Producer<? extends T> producer;

  /**
   * Stores the value of Lazy.
   */
  private Maybe<T> value;

  /**
   * Private constructor #1.
   *
   * @param value Input value
   */
  private Lazy(T value) {
    this.value = Maybe.some(value);
    this.producer = () -> value;
  }

  /**
   * Private constructor #2.
   * Creates using Producer
   *
   * @param s Input value
   */
  private Lazy(Producer<? extends T> s) {
    this.producer = s;
    this.value = Maybe.of(null);
  }
  
  /** 
   *Public factory method that creates new lazy with value.
   *
   * @param <T> The type of the value in Lazy instance
   * @param v The value that will be wrapped in Lazy container
   * @return New Lazy instance initialised with value v
   */
  public static <T> Lazy<T> of(T v) {
    return new Lazy<T>(v);
  }

  /** 
   *Public factory method that creates new lazy with producer.
   *
   * @param <T>  The type of producer in Lazy instance
   * @param s The producer that will be wrapped in lazy container
   * @return A new Lazy instance initialised with producer p
   */
  public static <T> Lazy<T> of(Producer<? extends T> s) {
    return new Lazy<T>(s);
  }

  /**
   * Gets the value of Lazy instance.
   * If no value is present, producer produces a value
   *
   * @return T which is the value stored or evaluated in Lazy
   */
  public T get() {
    T tmp = this.value.orElseGet(this.producer);
    this.value = Maybe.of(tmp);
    return tmp;
  }

  /**
   * Returns a String depiction of Lazy.
   *
   * @return A String that shows the current value in Lazy or ? if none is present
   */
  @Override
  public String toString() {
    return this.value.map(String::valueOf).orElse("?");
  }

  /**
   * Creates a new Lazy.
   * Contains a producer that transform existing value
   *
   * @param <U> is the method call type parameter
   * @param transformer The Transformer that will transform this Lazy value
   * @return A New instance of Lazy initialised with a producer which applies the transformer
   */
  public <U> Lazy<U> map(Transformer<? super T, ? extends U> transformer) {
    Producer<U> producer = () -> transformer.transform(this.get());
    return Lazy.of(producer);
  }

  /**
   * Creates a new Lazy.
   * Contains a producer that flattens the current producer along with the input
   *
   * @param <U> is the method call type parameter
   * @param transformer The Transformer that will transform this lazy value
   * @return A New instance of Lazy initialised with a producer that applies the transformer
   */
  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> transformer) {
    Producer<U> producer = () -> transformer.transform(this.get()).get();
    return Lazy.of(producer);
  }

  /** 
   * Creates a new Lazy.
   * Contains a producer that tests for a condition
   *
   * @param condition The BooleanCondition that tests for a condition on the argument
   * @return A New instance of Lazy initialised with a producer checks condition on Lazy value
   */
  public Lazy<Boolean> filter(BooleanCondition<? super T> condition) {
    Producer<Boolean> producer = () -> condition.test(this.get());
    return Lazy.of(producer);
  }

  /**
   * Checks if this instance of Lazyy is equal to input object.
   * returns true only if input is Lazy and value are the same
   *
   * @param o The Object that will be compared against to this instance
   * @return a boolean, true if values in both Lazy are the same, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof Lazy<?>) {
      Lazy<?> temp = (Lazy<?>) o;
      // compare if contents are the same
      if (temp.get() == this.get()) {
        return true;
      }
      if (temp.get() == null || this.get() == null) {
        return false;
      }
      return this.get().equals(temp.get());
    }
    return false;
  }

  /**
   * Combines two Lazy into one.
   *
   * @param <S> Input type parameter 
   * @param <R> Input type parameter
   * @param lazy Input value
   * @param combiner A Combiner which combines two Lazy values together
   * @return A new instance of Lazy initialised with the result of combining two input values
   */
  public <S, R> Lazy<R> combine(Lazy<S> lazy, 
      Combiner<? super T, ? super S, ? extends R> combiner) {
    // combine will return R -> the last param
    Producer<R> result = () -> combiner.combine(this.get(), lazy.get());
    return Lazy.of(result);
  }
}

