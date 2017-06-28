package net.msdh.kernel.system;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 11:07
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Interface {

  private String name;
  private String adress;
  private String mask;
  private String mac;
  private String state;
  private String txBytes;
  private String rxBytes;

  public Interface() {
  }

  public Interface(String name, String adress, String mask, String mac, String state, String txBytes, String rxBytes) {
    this.name = name;
    this.adress = adress;
    this.mask = mask;
    this.mac = mac;
    this.state = state;
    this.txBytes = txBytes;
    this.rxBytes = rxBytes;
  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTxBytes() {
        return txBytes;
    }

    public void setTxBytes(String txBytes) {
        this.txBytes = txBytes;
    }

    public String getRxBytes() {
        return rxBytes;
    }

    public void setRxBytes(String rxBytes) {
        this.rxBytes = rxBytes;
    }
}
