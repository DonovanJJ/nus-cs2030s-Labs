package cs2030s.fp;

import java.util.NoSuchElementException;

/**
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

public abstract class Maybe<T> {
  
  protected abstract T get();

  public abstract Maybe<T> filter(BooleanCondition<? super T>  cond);

  public abstract <U> Maybe<U> map(
      Transformer<? super T, ? extends U> transformer);

  public abstract <U> Maybe<U> flatMap(
      Transformer<? super T, ? extends Maybe<? extends U>> transformer);

  public abstract T orElse(T input);

  public abstract T orElseGet(Producer<? extends T> producer);

  public abstract void ifPresent(Consumer<? super T> consumer);

  private static class None extends Maybe<Object> {
    
    private static Maybe<?> none = new None();

    @Override
    public String toString() {
      return "[]";
    }

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

    @Override
    protected Object get() {
      throw new NoSuchElementException();
    }

    @Override
    public Maybe<Object> filter(BooleanCondition<Object> cond) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super Object, ? extends U> transformer) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super Object,
        ? extends Maybe<? extends U>> transformer) {
      return Maybe.none();
    }

    @Override
    public Object orElse(Object input) {
      return input;
    }

    @Override
    public Object orElseGet(Producer<? extends Object> producer) {
      return producer.produce();
    }

    @Override
    public void ifPresent(Consumer<? super Object> consumer) {

    }

  }

  private static class Some<T> extends Maybe<T> {
    
    private final T t;

    private Some(T t) {
      this.t = t;
    }

    @Override
    public String toString() {
      return "[" + this.t.toString() + "]";
    }

    @Override
    public boolean equals(Object m) {
      if (this == m) {
        return true;
      }
      if (m instanceof Some<?>) {
        // can suppress since we checked if its that instance
        @SuppressWarnings("unchecked")
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

    @Override
    public Maybe<T> filter(BooleanCondition<? super T> cond) {
      if (this.t != null && !cond.test(this.t)) {
        return Maybe.none();
      } 
      return this;
    }

    @Override
    public <U> Maybe<U> map(
        Transformer<? super T, ? extends U> transformer) {
      // NullPointerException is thrown if this.t == null don't need 
      // to catch since runtime its unchecked exception
      return Maybe.some(transformer.transform(this.t));
    }

    @Override 
    public <U> Maybe<U> flatMap(
        Transformer<? super T, ? extends Maybe<? extends U>> transformer) {
      // can suppress since trnasform will return subtype of Maybe<U>
      // based on the ? extends Maybe<? extends U>
      @SuppressWarnings("unchecked")
      Maybe<U> temp = (Maybe<U>) transformer.transform(this.t);
      return temp;
    }

    @Override
    public T orElse(T input) {
      return this.t;
    }

    @Override
    public T orElseGet(Producer<? extends T> producer) {
      return this.t;
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
      consumer.consume(this.t);
    }
  }

  public static <T> Maybe<T> of(T input) {
    if (input == null) {
      return none();
    }
    return some(input);
  }

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
}
