/** 
 * @author Chan Jia Jun (Group 12J)
 */

public class Bank {

  private Counter[] counters;
  private Queue queue;

  public Bank(Counter[] counters, Queue queue) {
    this.counters = counters;
    this.queue = queue;
  }

  public Counter getAvailableCounter() {
    int len = this.counters.length;
    Counter availCounter = null;
    for (int i = 0; i < len; i++) {
      if (this.counters[i].isAvailable()) {
        availCounter = this.counters[i];
        break;
      }
    }
    return availCounter;
  }

  public boolean isQueueFull() {
    return this.queue.isFull();
  }

  public boolean joinQueue(Object e) {
    return this.queue.enq(e);
  }

  public Customer removeQueue() {
    return (Customer) this.queue.deq();
  }

  public int queueLength() {
    return this.queue.length();
  }

  public boolean isQueueEmpty() {
    return this.queue.isEmpty();
  }

  public String showQueue() {
    return this.queue.toString();
  }
}



