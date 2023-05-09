
/** this class implements the Arrival event type
 *
 * @author Chan Jia Jun (group 12J)
 * */

public class Departure extends Event {
  
  // declare fields here
  private Customer customer;
  private Bank bank;
  private Counter counter;

  public Departure(double time, Customer customer, Bank bank, Counter counter) {
    super(time);
    this.customer = customer;
    this.bank = bank;
    this.counter = counter;
  }

  @Override 
  public String toString() {
    String str = "";
    str = String.format(": %s departed", this.customer.toString());
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {
    // for those that reach max cap
    if (this.counter == null) {
      return new Event[] {};
    }
    if (this.counter.counterQueueIsEmpty()) {

      //  counter queue and main queue empty
      if (this.bank.isMainQueueEmpty()) {
        return new Event [] {};
      }
      // main queue not empty -> should push him to counter
      Customer mainCustomer = this.bank.removeMainQueue();
      return new Event[] { 
        new ServiceBegin(this.getTime(), mainCustomer, this.counter, this.bank) };
    } 

    // Customer at counterqueue and main queue -> push both forward
    Customer nextCustomer = this.counter.deqCounterQueue();
    if (!this.bank.isMainQueueEmpty()) {
      Customer mainCustomer = this.bank.removeMainQueue();
      return new Event[] { 
        new JoinCounterQueue(this.getTime(), mainCustomer, this.bank, this.counter), 
        new ServiceBegin(this.getTime(), nextCustomer, this.counter, this.bank), };
    }
    // only Mainqueue customer -> push to counter
    return new Event[] { new ServiceBegin(this.getTime(), nextCustomer, this.counter, this.bank), };
  }
}
