
/**
 * @author Chan Jia Jun (Group 12J)
*/

public class JoinCounterQueue extends Event {
  
  private Customer customer;
  private Bank bank;
  private Counter counter;

  public JoinCounterQueue(double time, Customer customer, Bank bank, Counter counter) {
    super(time);
    this.customer = customer;
    this.bank = bank;
    this.counter = counter;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": %s joined counter queue (at %s %s)",  
          this.customer.toString(), this.counter.toString(), this.counter.showCounterQueue());
    return super.toString() + str;
  }

  @Override 
  public Event[] simulate() {
    this.counter.joinCounterQueue(this.customer);
    return new Event[] {};
  }
}
