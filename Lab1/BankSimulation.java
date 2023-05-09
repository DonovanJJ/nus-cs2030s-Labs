import java.util.Scanner;
import java.util.Arrays;

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
  // private boolean[] available;
  private Counter[] counters;
  /** 
   * The list of customer arrival events to populate
   * the simulation with.
   */
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

    counters = new Counter[numOfCounters];
    for (int i = 0; i < numOfCounters; i++) {
      this.counters[i] = new Counter(i, true);
    }

    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();
      initEvents[Customer.getTotal()] = new Arrival(arrivalTime, new Customer(), serviceTime, this.counters);
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
