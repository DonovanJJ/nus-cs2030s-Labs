
/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (Group 12J)
 * 
*/

public class ServiceBegin extends Event {
  
  // declare fields here
  private Customer customer;
  private Counter counter;
  private Bank bank;

  // class constructor
  public ServiceBegin(double time, Customer customer, Counter counter, Bank bank) {
    super(time);
    this.customer = customer;
    this.counter = counter;
    this.bank = bank;
  }
  
  @Override
  public String toString() {
    String str = "";
    str = String.format(": %s begin (by %s)", 
        this.customer.toStringServiceType(), this.counter.toString()); 
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {
    // The current event is a service-begin event.  
    // Mark the counter is unavailable, then schedule
    // a service-end event at the current time + service time.
    this.counter.changeStatus();
    double endTime = this.getTime() + this.customer.getServiceTime();
    return new Event[] { 
      new ServiceEnd(endTime, this.customer, this.counter, this.bank),
    };
  }
}

