/** 
 * @author Chan Jia Jun (Group 12J)
 */

public class Bank {

  private Array<Counter> bankCounters;
  private Queue<Customer> mainQueue;
  private int queueSize;
  private int counterSize;

  public Bank(int counterSize, int queueSize) {
    this.mainQueue = new Queue<Customer>(queueSize);
    this.bankCounters = new Array<Counter>(counterSize);
  }

  public void addCounter(Counter counter) {
    int i = 0;
    while (this.bankCounters.get(i) != null) {
      i++;
    }
    this.bankCounters.set(i, counter);
  }

  public Counter getAvailableCounter() {
    int index = 0;
    while (this.bankCounters.get(index) != null) {
      if (this.bankCounters.get(index).isAvailable()) {
        return this.bankCounters.get(index);
      }
      index++;
    }
    return null;
  }

  // Checks if the counter QUEUES are available
  public Counter getAvailableCounterQueue() {
    Counter counterQueue = this.bankCounters.min();
    if (counterQueue.counterQueueIsFull()) {
      return null;
    }
    return this.bankCounters.min();
  }

  public boolean isMainQueueFull() {
    return this.mainQueue.isFull();
  }

  public boolean joinMainQueue(Customer e) {
    return this.mainQueue.enq(e);
  }

  public Customer removeMainQueue() {
    return this.mainQueue.deq();
  }

  public boolean isMainQueueEmpty() {
    return this.mainQueue.isEmpty();
  }

  public String showMainQueue() {
    return this.mainQueue.toString();
  }
}



