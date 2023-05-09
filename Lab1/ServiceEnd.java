/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (Group 12J)
 * */
import java.util.Arrays;

public class ServiceEnd extends Event {
  
  // declare fields here
  private Customer customer;
  private double serviceTime;
  private Counter[] counters;
  private Counter counter;

  public ServiceEnd(double time, Customer customer, 
      double serviceTime, Counter counter, Counter[] counters) {
    super(time);
    this.customer = customer;
    this.serviceTime = serviceTime;
    this.counters = counters;
    this.counter = counter;
  }
  
  @Override
  public String toString() {
    String str = "";
    str = String.format(": Customer %d service done (by Counter %d)", 
          this.customer.getId(), this.counter.getId());
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {

      // The current event is a service-end event.  
      // Mark the counter is available, then schedule
      // a departure event at the current time.
      this.counters[this.counter.getId()].changeStatus(true);
      return new Event[] { 
        new Departure(this.getTime(), this.customer),
      };
  }
}

