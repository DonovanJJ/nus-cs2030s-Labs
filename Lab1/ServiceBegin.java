
/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (Group 12J)
 * 
*/

public class ServiceBegin extends Event {
  
  // declare fields here
  private Customer customer;
  private double serviceTime;
  private Counter[] counters;
  private Counter counter;

  // class constructor
  public ServiceBegin(double time, Customer customer, 
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
    str = String.format(": Customer %d service begin (by Counter %d)", this.customer.getId(), this.counter.getId()); 
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {
      // The current event is a service-begin event.  
      // Mark the counter is unavailable, then schedule
      // a service-end event at the current time + service time.
      this.counters[this.counter.getId()].changeStatus(false);
      double endTime = this.getTime() + this.serviceTime;
      return new Event[] { 
        new ServiceEnd(endTime, this.customer, 
            this.serviceTime, this.counter, this.counters),
        };
  }
}

