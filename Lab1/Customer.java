/**
 * @author Chan Jia Jun (Group 12J)
 */

public class Customer {
  
  static private int tot_customer = 0;
  private int Id;

  // constructor
  public Customer() {
    this.Id = tot_customer;
    // tracks number of customers arrived
    Customer.tot_customer += 1;
  }

  public int getId() {
    return this.Id;
  }

  static public int getTotal() {
    return tot_customer;
  }
}
