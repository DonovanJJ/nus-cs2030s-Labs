/**
@author Chan Jia Jun (Group 12J)
*/
public class Arrival extends Event {
  
  // declare fields here
  private Customer customer;
  private Bank bank;

  // class constructor here
  public Arrival(double time, Customer customer, Bank bank) {
    super(time);
    this.customer = customer;
    this.bank = bank;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": %s arrived %s", this.customer.toString(), this.bank.showQueue()); 
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {

    // check if theres available counters
    Counter counter = this.bank.getAvailableCounter();
    if (counter == null) {
      // no avail counter -> join queue if not full
      if (this.bank.isQueueFull()) {
        return new Event[] {
          new Departure(this.getTime(), this.customer, this.bank)
        };
      } else {
        return new Event[] { new JoinQueue(this.getTime(), this.customer, this.bank), };
      }
    }
    // Else, the customer should go the the first 
    // available counter and get served.
    return new Event[] { 
      new ServiceBegin(this.getTime(), this.customer, counter, this.bank),
    };
  }
}

