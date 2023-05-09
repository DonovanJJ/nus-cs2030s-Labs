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
    str = String.format(": %s arrived %s", 
        this.customer.toString(), this.bank.showMainQueue()); 
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {
    Counter counter = this.bank.getAvailableCounter();
    if (counter == null) {
      // no avail counter
      Counter availCounterQueue = this.bank.getAvailableCounterQueue();
      if (availCounterQueue == null) {
        // no Counter queue is available
        if (this.bank.isMainQueueFull()) {
          // mainQueue is fuill -> depart
          return new Event[] {
            new Departure(this.getTime(), this.customer, this.bank, null)
          };
        } 
        return new Event[] { new JoinMainQueue(this.getTime(), this.customer, this.bank), };
      } 
      // Counter queue is available -> join the counterQueue
      return new Event[] { 
        new JoinCounterQueue(this.getTime(), this.customer, this.bank, availCounterQueue), };
    } 
    // counter is available
    return new Event[] { new ServiceBegin(this.getTime(), this.customer, counter, this.bank), };
  }
}

