/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (Group 12J)
 * */
import java.util.Arrays;

public class ServiceEnd extends Event {
  
  // declare fields here
  private Customer customer;
  private Counter counter;
  private Bank bank;

  public ServiceEnd(double time, Customer customer, Counter counter, Bank bank) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.bank = bank;
  }
  
  @Override
  public String toString() {
    String str = "";
    str = String.format(": %s %s done (by %s %s)", 
          this.customer.toString(), this.customer.getTask(), this.counter.toString(), 
          this.counter.showCounterQueue());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    this.counter.changeStatus(true);
    return new Event[] { new Departure(this.getTime(), this.customer, this.bank, this.counter), };
  }
}

