package layout.communication;

public class ControllerCommunicator {
  private static final ControllerCommunicator instance = new ControllerCommunicator();
  private String msg;

  private ControllerCommunicator() {}

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public static ControllerCommunicator getInstance() {
    return instance;
  }
}
