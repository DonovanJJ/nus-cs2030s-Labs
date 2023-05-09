/**
 * @author Chan Jia Jun (Group 12J)
*/

public class JoinMainQueue extends Event {
  
  private Customer customer;
  private Bank bank;

  public JoinMainQueue(double time, Customer customer, Bank bank) {
    super(time);
    this.customer = customer;
    this.bank = bank;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": %s joined bank queue %s",  
          this.customer.toString(), this.bank.showMainQueue());
    return super.toString() + str;
  }

  @Override 
  public Event[] simulate() {
    this.bank.joinMainQueue(this.customer);
    return new Event[] {};
  }
}
