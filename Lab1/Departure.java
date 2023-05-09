
/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (group 12J)
 * */

public class Departure extends Event {
  
  // declare fields here
  private Customer customer;

  public Departure(double time, Customer customer) {
    super(time);
    this.customer = customer;
  }

 @Override 
  public String toString() {
    String str = "";
    str = String.format(": Customer %d departed", this.customer.getId());
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {
    // do nothing
    return new Event[] {};
  }
}
