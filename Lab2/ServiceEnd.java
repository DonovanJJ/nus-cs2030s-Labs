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
    str = String.format(": %s done (by %s)", 
          this.customer.toStringServiceType(), this.counter.toString());
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {

    // The current event is a service-end event.  
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    this.counter.changeStatus();
    return new Event[] { 
      new Departure(this.getTime(), this.customer, this.bank),
    };
  }
}

