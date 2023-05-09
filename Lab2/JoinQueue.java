/**
 * @author Chan Jia Jun (Group 12J)
*/

public class JoinQueue extends Event {
  
  private Customer customer;
  private Bank bank;

  public JoinQueue(double time, Customer customer, Bank bank) {
    super(time);
    this.customer = customer;
    this.bank = bank;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": %s joined queue %s",  
          this.customer.toString(), this.bank.showQueue());
    return super.toString() + str;
  }

  @Override 
  public Event[] simulate() {
    this.bank.joinQueue(this.customer);
    return new Event[] {};
  }
}
