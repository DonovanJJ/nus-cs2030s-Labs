/**
 * @author Chan Jia Jun (Group 12J)
 */

public class Customer {
  
  private int id;
  private double serviceTime;
  private double arrivalTime;
  private int serviceType;
  private Bank bank;

  // constructor
  public Customer(int customerId, double serviceTime, double arrivalTime, int serviceType) {
    this.id = customerId;
    this.serviceTime = serviceTime;
    this.arrivalTime = arrivalTime;
    this.serviceType = serviceType;
  }

  public String toStringServiceType() {
    String str = "";
    if (this.serviceType == 0) {
      str = String.format("C%d Deposit", this.id);
    } else {
      str = String.format("C%d Withdrawal", this.id);
    }
    return str;
  }

  @Override 
  public String toString() {
    String str = "";
    str = String.format("C%d", this.id);
    return str;
  }

  public double getServiceTime() {
    return this.serviceTime;
  }
}
