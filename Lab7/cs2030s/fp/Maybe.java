package cs2030s.fp;

import java.util.NoSuchElementException;

/**
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

/**
 * Encapsulates a value that might be missing
 * Contains two inner classes, None and Some
 */

public abstract class Maybe<T> {

  private static class None extends Maybe<Object> {
    
    private static final Maybe<?> none = new None();

    @Override
    public String toString() {
      return "[]";
    }

    /**
     * @param Object The type of input value
     * @param m Input value
     * @return boolean, true if this instance value is equal to argument value
     */
    @Override
    public boolean equals(Object m) {
      if (this == m) {
        return true;
      }
      if (m instanceof None) {
        return true;
      }
      return false;
    }

    /**
     * @throws NoSuchElementException since None contains no value
     */
    @Override
    protected Object get() {
      throw new NoSuchElementException();
    }

    /**
     * @param BooleanCondition<Object> The Type of the input
     * @param cond The input value
     * @return Maybe<Object> A new instance of None
     */
    @Override
    public Maybe<Object> filter(BooleanCondition<Object> cond) {
      return Maybe.none();
    }

    /**
     * @param <U> The Type of output value
     * @param Transformer The type of input value
     * @param transformer The input value
     * @return A new instance of None
     */
    @Override
    public <U> Maybe<U> map(Transformer<? super Object, ? extends U> transformer) {
      return Maybe.none();
    }

    /**
     * @param <U> The Type of ouput value
     * @param Transformer The type of input value
     * @param transformer The input value
     * @return A new instance of None
     */
    @Override
    public <U> Maybe<U> flatMap(Transformer<? super Object,
        ? extends Maybe<? extends U>> transformer) {
      return Maybe.none();
    }

    /**
     * @param Object The input type
     * @param input The input value
     * @return An Object which is The input value
     */
    @Override
    public Object orElse(Object input) {
      return input;
    }

    /**
     * @param Producer The type of input
     * @param producer The input value
     * @return An Object which is produced from the producer
     */
    @Override
    public Object orElseGet(Producer<? extends Object> producer) {
      return producer.produce();
    }

    @Override
    public void ifPresent(Consumer<? super Object> consumer) {

    }

    @Override
    public void consumeWith(Consumer<? super Object> consumer) {

    }
  }

  /**
   * Encapsulates a final value t while being immutable
   */
  private static class Some<T> extends Maybe<T> {
    
    private final T t;

    private Some(T t) {
      this.t = t;
    }

    @Override
    public String toString() {
      return "[" + this.t.toString() + "]";
    }

    /**
     * @param Object The input type
     * @param m The input value
     * @return a boolean, true if t of Some is equal, false otherwise
     */
    @Override
    public boolean equals(Object m) {
      if (this == m) {
        return true;
      }
      if (m instanceof Some<?>) {
        Some<?> temp = (Some<?>) m;

        // check for primitve
        if (temp.t == this.t) {
          return true;
        }
        if (temp.t == null || this.t == null) {
          return false;
        }
        return temp.t.equals(this.t);
      }
      return false;
    }

    @Override
    protected T get() {
      return this.t;
    }

    /**
     * @param <T> The type of this instance of Some
     * @param BooleanCondition The input type
     * @param cond The input value
     * @return a None if condition evaluates to false, otherwise returns this instance
     */
    @Override
    public Maybe<T> filter(BooleanCondition<? super T> cond) {
      if (this.t != null && !cond.test(this.t)) {
        return Maybe.none();
      } 
      return this;
    }

    /**
     * @param <U> The type of output
     * @param <T> The type of this instance of Some
     * @param Transformer The input type
     * @param transformer The input value
     * @return A new instance of Some initialised by the transformed value of the current instance
     */
    @Override
    public <U> Maybe<U> map(
        Transformer<? super T, ? extends U> transformer) {
      // NullPointerException is thrown if this.t == null don't need 
      // to catch since runtime its unchecked exception
      return Maybe.some(transformer.transform(this.t));
    }

    /**
     * @param <U> The type of output
     * @param <T> The type of this instance of Some
     * @param Transformer The input type
     * @param transformer The input value
     * @return A new instance of Maybe which has been transformed by transformer
     */
    @Override 
    public <U> Maybe<U> flatMap(
        Transformer<? super T, ? extends Maybe<? extends U>> transformer) {
      // flatmap produce Maybe, and a Maybe is produced from T
      // can suppress since trnasform will return subtype of Maybe<U>
      // based on the ? extends Maybe<? extends U>
      @SuppressWarnings("unchecked")
      Maybe<U> temp = (Maybe<U>) transformer.transform(this.t);
      return temp;
    }

    /**
     * @param <T> Type of input and current instance
     * @param input The value of input
     * @return The t field of Some
     */
    @Override
    public T orElse(T input) {
      return this.t;
    }

    /**
     * @param <T> Type of inputa and output and current instance
     * @param Producer The type of input
     * @param producer The input value
     * @return The t field of this Some instance
     */
    @Override
    public T orElseGet(Producer<? extends T> producer) {
      return this.t;
    }

    /**
     * @param <T> Type parameter of current instance
     * @param Consumer Type of input
     * @param consumer Input value
     */
    @Override
    public void ifPresent(Consumer<? super T> consumer) {
      consumer.consume(this.t);
    }

    @Override
    public void consumeWith(Consumer<? super T> consumer) {
      consumer.consume(this.t);
    }
  }

  /**
   * @param <T> The input type
   * @param input The input value
   * @return A new instance of None if input == null, new instance of Some otherwise
   */
  public static <T> Maybe<T> of(T input) {
    if (input == null) {
      return none();
    }
    return some(input);
  }

  /**
   * @param <T> The input type
   * @return The cached instance of None
   */
  public static <T> Maybe<T> none() {
    // supress since it is guranteed to be None which has nothing,
    // so it can take be anything and it is final
    @SuppressWarnings("unchecked")
    Maybe<T> temp = (Maybe<T>) None.none;
    return temp;
  }

  public static <T> Maybe<T> some(T t) {
    return new Some<T>(t);
  }

  protected abstract T get();

  public abstract Maybe<T> filter(BooleanCondition<? super T>  cond);

  public abstract <U> Maybe<U> map(
      Transformer<? super T, ? extends U> transformer);

  public abstract <U> Maybe<U> flatMap(
      Transformer<? super T, ? extends Maybe<? extends U>> transformer);

  public abstract T orElse(T input);

  public abstract T orElseGet(Producer<? extends T> producer);

  public abstract void ifPresent(Consumer<? super T> consumer);

  public abstract void consumeWith(Consumer<? super T> consumer);
}
