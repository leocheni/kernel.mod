package net.msdh.kernel.system;

import net.minidev.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 11:05
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Cpu{

    public int cores;
    public String model;
    public String freq;
    public String temp;
    public int usage;

 //   private long lastSystemTime      = 0;
  //  private long lastProcessCpuTime  = 0;


    public Cpu(){
      this.model = "N/A";
      this.cores = 0;
      this.freq = "N/A";
      this.temp = "N/A";
      this.usage = 0;

    }

    Cpu(int cores, String model, String freq, String temp, int usage){
      this.cores = cores;
      this.model = model;
      this.freq = freq;
      this.temp = temp;
      this.usage = usage;
      //myOsBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    }

//    public synchronized double UpdateUsage(){
//
//       long nanoBefore = System.nanoTime();
//       long cpuBefore = myOsBean.getProcessCpuTime();
//
//       System.out.println("nanoBefor: "+nanoBefore);
//       System.out.println("CpuBefor: "+cpuBefore);
//       try {
//            Thread.sleep(4000);
//       } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//       }
//
//
//       long cpuAfter = myOsBean.getProcessCpuTime();
//       long nanoAfter = System.nanoTime();
//
//       System.out.println("nanoAfter: "+nanoAfter);
//       System.out.println("cpuAfter: "+cpuAfter);
//
//       long percent;
//       //if (nanoAfter > nanoBefore)
//         percent = ((cpuAfter-cpuBefore)*100L)/(nanoAfter-nanoBefore);
//       //else
//       //  percent = 0;
//
//       System.out.println("Cpu usage: "+percent+"%");
//       return  percent;
//
////      if( lastSystemTime == 0){
////        baselineCounters();
////        //return -1;
////
////      }
//
////      lastSystemTime = System.nanoTime();
////      lastProcessCpuTime = myOsBean.getProcessCpuTime();
////
////      long systemTime = System.nanoTime();
////      long processCpuTime = 0;
////
////
////      processCpuTime = myOsBean.getProcessCpuTime();
////      double cpuUsage = (double) ( processCpuTime - lastProcessCpuTime ) / ( systemTime - lastSystemTime );
////      lastSystemTime     = systemTime;
////      lastProcessCpuTime = processCpuTime;
////      return cpuUsage / cores;
//    }

    public JSONObject toJson(){
      JSONObject params = new JSONObject();
      params.put("cpuCores",cores);
      params.put("cpuModel",model);
      params.put("cpuFreq",freq);
      params.put("cpuTemp",temp);
      params.put("cpuUsed",usage);
      return params;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }
}
