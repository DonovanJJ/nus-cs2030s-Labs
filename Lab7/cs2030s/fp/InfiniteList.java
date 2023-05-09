package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Encapculates Lazy Maybe that enables lazy stream evaluation .
 * includes the following methods
 * (1) generate a InfiniteList using a producer
 * (2) iterate a InfiniteList using a seed and transformer
 * (3) two constructors
 * (4) head that returns the value in the head
 * (5) tail returns the next part of the infiniteList
 * (6) map lazily transform each head of the infiniteList
 * (7) filter lazily filters the infiniteList
 * (8) sentinel returns the cached Sentinel
 * (9) contains the inner Sentinel class
 * (10) limit converts the InfiniteList into a finite list
 * (11) count returns the length of the finite List
 * (12) toList converts the InfiniteList into a finite list
 * (13) toString shows the String representation fo the InfinteList
 */
public class InfiniteList<T> {

  /**
   * Stores the head of InfintieList.
   */
  private final Lazy<Maybe<T>> head;

  /**
   * Stores the tail of the InfiniteList.
   */
  private final Lazy<InfiniteList<T>> tail;

  /**
   * private constructor of the InfinteList.
   */
  private InfiniteList() { 
    this.head = null; 
    this.tail = null;
  }

  /**
   * Static method that generates an InfiniteList.
   *
   * @param <T> The type of Producer used
   * @param producer The Producer that is used to generate head of the InfinteList
   * @return An new instance of InfiniteList initialised by applying the Producer
   */
  public static <T> InfiniteList<T> generate(Producer<T> producer) {
    // TODO
    // use tihs constructor => InfiniteList(T head, Producer<InfiniteList<T>> tail)
    // // cannot evaluate the producer
    return new InfiniteList<T>(Lazy.of(() -> Maybe.some(producer.produce())),
        Lazy.of(() -> InfiniteList.<T>generate(producer)));
  }

  /**
   * Static method that produces an InfiniteList by apply function to seed.
   * @param <T> The type of seed used
   * @param seed The initial value used to apply the Transformer
   * @param next The Transformer that will be appleid to the seed
   * @return A new instance of  IniniteList initialised from transforming the seed
   */
  public static <T> InfiniteList<T> iterate(T seed, Transformer<T, T> next) {
    // TODO
    InfiniteList<T> lst = new InfiniteList<>(seed, 
        () -> InfiniteList.<T>iterate(next.transform(seed), next));
    lst.head();
    return lst;
  }

  /**
   * Private constructor #1.
   *
   * @param head Used as the head of the new InfiniteList
   * @param tail Used as the tail of the new InfiniteList
   */
  private InfiniteList(T head, Producer<InfiniteList<T>> tail) {
    // TODO
    this.head = Lazy.of(() -> Maybe.some(head));
    this.tail = Lazy.of(tail);
  }

  /**
   * Private constructor #2.
   *
   * @param head Used as the head of the new InfiniteList
   * @param tail Used as the tail of teh new InfiniteList
   */
  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    // TODO
    this.head = head;
    this.tail = tail;
  }

  /**
   * Evaluates the head of the Infinitelist.
   *
   * @return The evaluated value of the head of this InfiniteList
   */
  public T head() {
    Maybe<T> temp = this.head.get(); 
    return temp.orElseGet(() -> this.tail.get().head());
  }

  /**
   * Evaluates the head of the InfiniteList but returns the next cycle of the InfiniteList.
   *
   * @return An instance of the InfiniteList that is the next cycle of the current InfiniteList 
   */
  public InfiniteList<T> tail() {
    // evaluate the head and return the tail
    return this.head.get().map(x -> this.tail.get()).orElseGet(() -> this.tail.get().tail());
  }

  /**
   * Lazily transforms the head of the InfiniteList.
   *
   * @param <R> The return type of the transformed value
   * @param mapper The Transformer that is used to transform the head of the InfiniteList
   * @return An instance of InfiniteList initialised with a Lazy Maybe cntaining the 
   *        transformed head and tail of the next cycle
   */
  public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> mapper) {
    // cannot transform until needed
    return new InfiniteList<>(Lazy.of(() -> Maybe.some(this.head()).map(mapper)), 
        Lazy.of(() -> this.tail().map(mapper)));
  }

  /**
   * Lazily filters out the InfiniteList.
   *
   * @param predicate The condition that is used to filter the head value of the InfiniteList
   * @return An instance of InfiniteList whose head has been filtered out, 
   *        filtered values is now None
   */
  public InfiniteList<T> filter(BooleanCondition<? super T> predicate) {
    return new InfiniteList<>(Lazy.of(() -> this.head.get().filter(predicate)), 
        Lazy.of(() -> this.tail.get().filter(predicate)));
  }

  /**
   * Static method that returns the cached Sentinel.
   *
   * @param <T> The type fot Sentinel
   * @return An instance of InfiniteList that is the cached Sentinel
   */
  public static <T> InfiniteList<T> sentinel() {
    // suppress warning since field is final and immutable
    @SuppressWarnings("unchecked")
    InfiniteList<T> temp = (InfiniteList<T>) Sentinel.SENTINEL;
    return temp;
  }

  /**
   * Limits the length of the InfiniteList by adding a cached sentinel at the tail.
   *
   * @param n The size of the returned InfiniteList
   * @return An instance of InfiniteList with the cached sentinel at the tail
   */
  public InfiniteList<T> limit(long n) {
    // TODO
    if (n <= 0) {
      return sentinel();
    }
    return new InfiniteList<T>(this.head,
        Lazy.of(() -> this.head.get()
          .map(x -> this.tail().limit(n - 1)).orElseGet(() -> this.tail.get().limit(n))));
  }

  /**
   * Limits the length of the InfiniteList at the point when condition fails.
   *
   * @param predicate The Condition that is used to determine the endpoint of the InfiniteList
   * @return An instance of InfiniteList which length is limited at the 
   *         point where condition first fails
   */
  public InfiniteList<T> takeWhile(BooleanCondition<? super T> predicate) {
    
    Lazy<Maybe<T>> laze = Lazy.of(() -> Maybe.of(this.head()).filter(predicate));
    return new InfiniteList<T>(laze, 
        Lazy.of(() -> Maybe.of(this.head()).map(x -> laze.get() == Maybe.none() 
            ? InfiniteList.<T>sentinel() 
            : this.tail().takeWhile(predicate))
          .orElseGet(() -> InfiniteList.<T>sentinel())));
  }

  /**
   * Returns true if this InfiniteList is a Sentinel.
   *
   * @return Boolean value, if this is sentinel returns true else false
   */
  public boolean isSentinel() {
    return this == InfiniteList.<T>sentinel();
  }

  /**
   * Eagerly accumulates the items in the InfiniteList.
   *
   * @param <U> The input type of the initial indentity
   * @param identity The initial value that initiates the accumulation
   * @param accumulator The Combiner that combines the head of InifntieList with identity
   * @return The input type, which is accumulate(identity, this.head()) stacked repeatedly
   */
  public <U> U reduce(U identity, Combiner<U, ? super T, U> accumulator) {
    // TODOo
    return this.tail.get().reduce(this.head.get()
        .map(x -> accumulator.combine(identity, x))
        .orElse(identity), accumulator);
  }

  /**
   * Returns the number of items in the InfiniteList.
   *
   * @return A primitive long that is the number of items in the InfiniteList
   *        until Sentinel is found
   */
  public long count() {
    // TODO
    long counter = this.reduce(0, (x, y) -> x + 1);
    return counter;
  }

  /**
   * Returns a list representation of the current InfiniteList.
   *
   * @return A List that contains all the items of the InfiniteList
   */
  public List<T> toList() {
    InfiniteList<T> current = this;
    List<T> list = new ArrayList<T>();
    while (!current.isSentinel()) {
      current.head.get().consumeWith(x -> list.add(x));
      current = current.tail.get();
    }
    return list;
  }

  /**
   * Returns the String representation of the InfiniteList.
   *
   * @return A String representation of the InfiniteList
   */
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }

  /**
   * Inner class that extends from InfiniteList, typically used to show the end of the InfiniteList.
   */
  private static class Sentinel extends InfiniteList<Object> {
    
    /**
     * Field that stores the cached Sentinel.
     */
    private static final InfiniteList<?> SENTINEL = new Sentinel();

    /**
     * Constructor that calls the default constructor of InfiniteList.
     * Returns InfiniteList containing null in both head and tail
     */
    public Sentinel() {
      super();
    }

    /**
     * Returns the head of the sentinel.
     *
     * @return An Object that is null
     * @throws NoSuchElementException since Sentinel is empty
     */
    @Override
    public Object head() {
      throw new NoSuchElementException();
    }

    /**
     * Returns the tail of the Sentinel.
     *
     * @return An instance of InfiniteList, but in this case it will not
     * @throws NoSuchElementException since Sentinel is empty
     */
    @Override
    public InfiniteList<Object> tail() {
      throw new NoSuchElementException();
    }

    /**
     * Returns the String reprentation of Sentinel.
     *
     * @return String representation of Sentinel
     */
    @Override 
    public String toString() {
      return "-";
    }

    /**
     * Returns The mapped Sentinel, but will return the cached Sentinel.
     *
     * @param <R> The Transformed type of the head
     * @param mapper The Trasnformer supplised to the method
     * @return An instance of InfiniteList which is a sentinel
     */
    @Override
    public <R> InfiniteList<R> map(Transformer<? super Object, ? extends R> mapper) {
      return InfiniteList.sentinel();
    }

    /**
     * Returns a filtered Sentinel.
     *
     * @param predicate The input condition for determining if value should be included
     * @return The cached Sentinel
     */
    @Override
    public InfiniteList<Object> filter(BooleanCondition<? super Object> predicate) {
      return InfiniteList.sentinel();
    }
    
    /**
     * Returns the cached Sentinel since it cannot be limited.
     *
     * @param n The required length of InfiniteList
     * @return The cached Sentinel
     */
    @Override
    public InfiniteList<Object> limit(long n) {
      return InfiniteList.sentinel();
    }

    /**
     * Returns the cached Sentinel since it cannot be limited further.
     *
     * @param predicate The condition to determine when to stop the InfiniteList
     * @return The cached Sentinel
     */
    @Override
    public InfiniteList<Object> takeWhile(BooleanCondition<? super Object> predicate) {
      return InfiniteList.sentinel();
    }

    /**
     * Returns a long 0, since Sentinel indicates the end of the InfiniteList.
     *
     * @return 0L
     */
    @Override
    public long count() {
      return 0L;
    }

    /**
     * Returns the identity since Sentinel does not contain anything.
     *
     * @return Identity
     */
    @Override
    public <U> U reduce(U identity, Combiner<U, ? super Object, U> accumulator) {
      return identity;
    }
  }

}
