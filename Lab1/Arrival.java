
/**
@author Chan Jia Jun (Group 12J)
*/
public class Arrival extends Event {
  
  // declare fields here
  private Customer customer;
  private double serviceTime;
  private Counter[] counters;

  // class constructor here
  public Arrival(double time, Customer customer, double serviceTime, Counter[] counters) {
    super(time);
    this.customer = customer;
    this.serviceTime = serviceTime;
    this.counters = counters;
  }
  
  @Override
  public String toString() {
    String str = "";
    str = String.format(": Customer %d arrives", this.customer.getId());
    return super.toString() + str;
  }
  
  @Override
  public Event[] simulate() {

    // check if theres available counters
      Counter counter = null;
      for (int i = 0; i < this.counters.length; i += 1) {
        if (this.counters[i].getStatus()) {
          counter = this.counters[i];;
          break;
        }
      }
      if (counter == null) {
        // If no such counter can be found, the customer
        // should depart.
        return new Event[] { 
          new Departure(this.getTime(), this.customer)
        };
      } else {
        // Else, the customer should go the the first 
        // available counter and get served.
        return new Event[] { 
          new ServiceBegin(this.getTime(), this.customer, this.serviceTime, counter, this.counters)
        };
      }

  }

}

