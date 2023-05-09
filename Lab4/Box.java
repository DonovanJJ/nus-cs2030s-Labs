/**
 * A generic box storing an item.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Chan Jia Jun (Lab 12J)
 */

public class Box<T> {
  
  private final T content;
  private final static Box<?> EMPTY_BOX = new Box<Object>(null);

  private Box(T content) {
    this.content = content;
  }

  public static <T> Box<T> empty() {
    // supress since it is guranteed to be box containing null and it is final
    @SuppressWarnings("unchecked")
    Box<T> box = (Box<T>) EMPTY_BOX;
    return box;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {  
      return true;
    }

    if (obj instanceof Box<?>) {
      Box<?> b = (Box<?>) obj;
      // check for primitive types
      if (this.content == b.content) {
        return true;
      }
      if (this.content == null || b.content == null) {
        return false;
      }
      // check for reference types
      return this.content.equals(b.content);
    }
    return false;
  }

  @Override
  public String toString() {
    if (this.content == null) {
      return "[]";
    }
    return "[" + this.content.toString() + "]";
  }

  public static <T> Box<T> of(T obj) {
    if (obj == null) {
      return null;
    }
    return new Box<T> (obj);
  }

  public static <T> Box<T> ofNullable(T obj) {
    if (obj == null) {
      return empty();
    }
    // can use of since null case has been filtered
    return of(obj);
  }

  public boolean isPresent() {
    if (this.content != null) {
      return true;
    }
    return false;
  }

  public Box<T> filter(BooleanCondition<? super T> action) {
    if (this.isPresent()) {
      // there is content inside
      if (action.test(this.content)) {
        return this;
      }
    } 
    return empty();
  }

  public <U> Box<U> map(Transformer<? super T, U> transformer) {
    if (!this.isPresent() || this == null) {
      return empty();
    }
    return ofNullable((transformer.transform(this.content)));
  }
}
