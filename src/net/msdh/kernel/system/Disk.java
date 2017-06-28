package net.msdh.kernel.system;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 11:05
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Disk {

  private String name;
  private String smartRaw;
  private String size;


  public Disk() {
  }

  public Disk(String name, String smartRaw, String size) {
    this.name = name;
    this.smartRaw = smartRaw;
    this.size = size;
  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmartRaw() {
        return smartRaw;
    }

    public void setSmartRaw(String smartRaw) {
        this.smartRaw = smartRaw;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
