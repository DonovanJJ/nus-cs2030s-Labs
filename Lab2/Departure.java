
/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (group 12J)
 * */

public class Departure extends Event {
  
  // declare fields here
  private Customer customer;
  private Bank bank;

  public Departure(double time, Customer customer, Bank bank) {
    super(time);
    this.customer = customer;
    this.bank = bank;
  }

  @Override 
  public String toString() {
    String str = "";
    str = String.format(": %s departed", this.customer.toString());
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {
    if (this.bank.getAvailableCounter() == null || this.bank.isQueueEmpty()) {
      return new Event[] {};
    }
    Customer nextCustomer = this.bank.removeQueue();
    return new Event[] {
      new ServiceBegin(this.getTime(), nextCustomer, this.bank.getAvailableCounter(), this.bank),
    };
  }
}
