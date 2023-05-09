import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements a bank simulation.
 * @author CHAN JIA JUN (Group 12J)
 *
 * @author Wei Tsang
 * @version CS2030S AY21/22 Semester 2
 */ 
public class BankSimulation extends Simulation {
  /** 
   * The availability of counters in the bank. 
   */
  private Bank bank;
  private Event[] initEvents;

  /** 
   * Constructor for a bank simulation. 
   *
   * @param sc A scanner to read the parameters from.  The first
   *           integer scanned is the number of customers; followed
   *           by the number of service counters.  Next is a 
   *           sequence of (arrival time, service time) pair, each
   *           pair represents a customer.
   */
  public BankSimulation(Scanner sc) {
    initEvents = new Event[sc.nextInt()];
    int numOfCounters = sc.nextInt();
    int counterQueueSize = sc.nextInt();
    int mainQueueSize = sc.nextInt();

    // create queue
    this.bank = new Bank(numOfCounters, mainQueueSize);

    for (int i = 0; i < numOfCounters; i++) {
      this.bank.addCounter(new Counter(i, true, counterQueueSize));
    }
    
    // Record Customers
    int customerNo = 0;
    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();
      int serviceType = sc.nextInt();

      // create customer object
      Customer customer = new Customer(customerNo, serviceTime, arrivalTime, serviceType);

      // handle Queue
      initEvents[customerNo] = new Arrival(arrivalTime, customer, this.bank);
      customerNo++;
    }
  }

  /**
   * Retrieve an array of events to populate the 
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }
}
