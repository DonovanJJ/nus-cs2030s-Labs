/**
 * @author Chan Jia Jun (group 12J)
 * */
import java.lang.Comparable;

public class Counter implements Comparable<Counter> {

  // constructor here
  private int id;
  private boolean available;
  private Queue<Customer> counterQueue;
  
  // constructor
  public Counter(int id, boolean available, int queueSize) {
    this.id = id;
    this.available = available;
    this.counterQueue = new Queue<Customer>(queueSize);
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format("S%d", this.id);
    return str;
  }

  public void changeStatus(boolean b) {
    this.available = b;
  }

  public boolean isAvailable() {
    return this.available;
  }

  public boolean counterQueueIsFull() {
    return this.counterQueue.isFull();
  }

  public boolean counterQueueIsEmpty() {
    return this.counterQueue.isEmpty();
  }

  public int counterQueueLength() {
    return this.counterQueue.length();
  }

  public String showCounterQueue() {
    return this.counterQueue.toString();
  }

  public boolean joinCounterQueue(Customer customer) {
    return this.counterQueue.enq(customer);
  }

  public Customer deqCounterQueue() {
    return this.counterQueue.deq();
  }

  @Override
  public int compareTo(Counter counter) {
    if (this.counterQueueLength() > counter.counterQueueLength()) {
      return 1;
    } else if (this.counterQueueLength() < counter.counterQueueLength()) {
      return -1;
    } else {
      return 0;
    }
  }
}
