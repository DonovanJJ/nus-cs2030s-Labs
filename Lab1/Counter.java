/**
 * @author Chan Jia Jun (group 12J)
 * */

public class Counter {

  // constructor here
  private int Id;
  private boolean available;
  
  // constructor
  public Counter(int id, boolean available) {
    this.Id = id;
    this.available = available;
  }

  public int getId() {
    return this.Id;
  }

  public void changeStatus(boolean state) {
    this.available = state;
  }

  public boolean getStatus() {
    return this.available;
  }
}
