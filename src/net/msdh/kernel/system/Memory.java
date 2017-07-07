package net.msdh.kernel.system;

import net.minidev.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 11:05
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Memory {
  public double physical;
  public double total;
  public double usage;
  public double free;

  public Memory(){
    this.physical = 0;
    this.total = 0;
    this.free = 0;
    this.usage = 0;
  }

  public Memory(double physical, double total, double usage, double free) {
    this.physical = physical;
    this.total = total;
    this.usage = usage;
    this.free = free;
  }

  public JSONObject toJson(){

    JSONObject tMemory = new JSONObject();
    tMemory.put("memPhysical",physical);
    tMemory.put("memTotal",total);
    tMemory.put("memFree",free);
    tMemory.put("memUsed",usage);

    return tMemory;
  }

//  public void Update(){
//      physical = myOsBean.getTotalPhysicalMemorySize();
//      total = myOsBean.getTotalPhysicalMemorySize();
//      free =  myOsBean.getFreePhysicalMemorySize();
//      usage = total - free;
//      //System.out.println("total memory: " + Format.Humanize_number(myOsBean.getTotalPhysicalMemorySize(), 2));
//      //System.out.println("free memory: " + Format.Humanize_number(myOsBean.getFreePhysicalMemorySize(),2));
//  }

  public double getPhysical() {
        return physical;
  }

  public void setPhysical(double physical) {
        this.physical = physical;
  }

  public double getTotal() {
        return total;
  }

  public void setTotal(double total) {
        this.total = total;
  }

  public double getUsage() {
        return usage;
  }

  public void setUsage(double usage) {
        this.usage = usage;
  }

  public double getFree() {
        return free;
  }

  public void setFree(double free) {
        this.free = free;
  }
}
