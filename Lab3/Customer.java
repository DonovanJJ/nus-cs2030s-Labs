/**
 * @author Chan Jia Jun (Group 12J)
 */

public class Customer { 
  private int id;
  private double serviceTime;
  private double arrivalTime;
  private int serviceType;

  // constructor
  public Customer(int customerId, double serviceTime, double arrivalTime, int serviceType) {
    this.id = customerId;
    this.serviceTime = serviceTime;
    this.arrivalTime = arrivalTime;
    this.serviceType = serviceType;
  }

  @Override 
  public String toString() {
    String str = "";
    str = String.format("C%d", this.id);
    return str;
  }

  public String getTask() {
    if (this.serviceType == 0) {
      return "Deposit";
    } else if (this.serviceType == 1) {
      return "Withdrawal";
    } else {
      return "OpenAccount";
    }
  }

  public double getServiceTime() {
    return this.serviceTime;
  }
}
