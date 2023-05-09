/**
 * @author Chan Jia Jun (group 12J)
 * */

public class Counter {

  // constructor here
  private int id;
  private boolean available;
  
  // constructor
  public Counter(int id, boolean available) {
    this.id = id;
    this.available = available;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format("S%d", this.id);
    return str;
  }

  public void changeStatus() {
    this.available = !this.available;
  }

  public boolean isAvailable() {
    return this.available;
  }
}
