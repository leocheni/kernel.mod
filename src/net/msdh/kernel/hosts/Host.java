package net.msdh.kernel.hosts;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.system.*;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 10:59
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Host {

//    private Iterator<Interface> intIter;
//    private Iterator<Disk> dskIter;
//    private Iterator<Slice> slcIter;
//    private Iterator<Zpool> zpIter;

    private Map<String,String> KernelVariables;

    private String dmesg;
    private String uptime;
    private String uname;
    private String adress;
    private String mac;
    private String name;
    private String status;
    private int port;
    private String osType;

    private Cpu cpu;
    private Memory memory;
    private Power power;

    private Vector<Disk> disks;
    private Vector<Slice> slices;
    private Vector<Zpool> zpools;
    private Vector<Interface> interfaces;

    public Host(){
      cpu = new Cpu();
      memory = new Memory();
      power = new Power();
      disks = new Vector<Disk>();
      slices = new Vector<Slice>();
      interfaces = new Vector<Interface>();
      zpools = new Vector<Zpool>();
    }


    public Host(JSONObject params){

      cpu = new Cpu();
      memory = new Memory();
      power = new Power();
      disks = new Vector<Disk>();
      slices = new Vector<Slice>();
      interfaces = new Vector<Interface>();
      zpools = new Vector<Zpool>();

      this.setStatus("up");
      try{
        this.port = Integer.parseInt(params.get("port").toString());
      }
      catch(Exception e){
        this.port = 0;
      }

      try{
        this.osType = params.get("os").toString();
      }
      catch(Exception e){
        this.osType = "N/A";
      }
      try{
        this.uname = params.get("uname").toString();
      }
      catch(Exception e){
        this.uname = "N/A";
      }

      try{
        this.uptime = params.get("uptime").toString();
      }
      catch(Exception e){
        this.uptime = "N/A";
      }

      try{
        JSONObject tCpu = (JSONObject) params.get("cpu");
        this.cpu.cores = Integer.parseInt(tCpu.get("cpuCores").toString());
        this.cpu.freq = tCpu.get("cpuFreq").toString();
        this.cpu.model = tCpu.get("cpuModel").toString();
        this.cpu.temp = tCpu.get("cpuTemp").toString();
        this.cpu.usage = Integer.parseInt(tCpu.get("cpuUsed").toString());
      }
      catch(Exception ignored){ }

      try{
        JSONObject tMemory = (JSONObject) params.get("memory");
                    //host.memory.physical = tMemory.get<double>("memPhysical");
        this.memory.total = Double.parseDouble(tMemory.get("memTotal").toString());
        this.memory.usage = Double.parseDouble(tMemory.get("memUsed").toString());
        this.memory.free = Double.parseDouble(tMemory.get("memFree").toString());
      }
      catch(Exception ignored){ }

      try{
        JSONObject tPower = (JSONObject) params.get("power");
        this.power.setCharge(tPower.get("charge").toString());
        this.power.setModel(tPower.get("model").toString());
        this.power.setInputVoltage(tPower.get("inputVoltage").toString());
        this.power.setOutputVoltage(tPower.get("outputVoltage").toString());
        this.power.setLoad(tPower.get("load").toString());
        this.power.setType(tPower.get("type").toString());
        this.power.setStatus(tPower.get("status").toString());
      }
      catch(Exception ignored){ }

      try{
        JSONArray tInterfaces = (JSONArray) params.get("interfaces");
        for(Object i: tInterfaces){
          Interface tInterface = new Interface();
          tInterface.setName(((JSONObject)i).get("name").toString());
          tInterface.setAdress(((JSONObject)i).get("adress").toString());
          tInterface.setMac(((JSONObject)i).get("mac").toString());
          tInterface.setMask(((JSONObject)i).get("mask").toString());
          tInterface.setState(((JSONObject)i).get("state").toString());
          tInterface.setTxBytes(((JSONObject)i).get("tx").toString());
          tInterface.setRxBytes(((JSONObject)i).get("rx").toString());
          this.interfaces.add(tInterface);
        }
      }
      catch(Exception ignored){ }

      try{
        JSONArray tDisks = (JSONArray) params.get("disks");
        for(Object d: tDisks){
          Disk tDisk = new Disk();
          tDisk.setName(((JSONObject) d).get("name").toString());
          this.disks.add(tDisk);
        }
      }
      catch(Exception ignored){  }

      try{
        JSONArray tSlices = (JSONArray) params.get("slices");
        for(Object s: tSlices){
          Slice tSlice = new Slice();
          tSlice.setMntNameFrom(((JSONObject)s).get("mountFrom").toString());
          tSlice.setMntNameTo(((JSONObject)s).get("mountTo").toString());
          tSlice.setTotalSize(((JSONObject)s).get("totalSize").toString());
          tSlice.setUsedSize(((JSONObject)s).get("usedSize").toString());
          tSlice.setFreeSize(((JSONObject)s).get("freeSize").toString());
          this.slices.add(tSlice);
        }
      }
      catch(Exception ignored){ }

      try{
        JSONArray tZpools = (JSONArray) params.get("zpools");
        for(Object z: tZpools){
          Zpool tZpool = new Zpool(((JSONObject) z).get("name").toString());
          //tZpool.setName(((JSONObject) z).get("name").toString());
          //tZpool.setAlloc();
          //tZpool.setSize();
          //tZpool.getFree();
          this.zpools.add(tZpool);
        }
      }
      catch(Exception ignored){ }


    }

    public Host(String name, String adress, String mac, String status) {
        this.name = name;
        this.mac = mac;
        this.adress = adress;
        this.status = status;

        cpu = new Cpu();
        memory = new Memory();
        power = new Power();
        disks = new Vector<Disk>();
        slices = new Vector<Slice>();
        interfaces = new Vector<Interface>();
        zpools = new Vector<Zpool>();

    }

//    public void Inicialize(){
//      osType = System.getProperty("os.name");
//
//      cpu = new Cpu();
//      memory = new Memory();
//      power = new Power();
//      //System.out.println("CPU usage: " + cpu.UpdateUsage());
//
//      File[] roots = File.listRoots();
//
//      for (File root : roots) {
//        System.out.println("File system root: " + root.getAbsolutePath());
//        System.out.println("Total space (bytes): " + Format.Humanize_number(root.getTotalSpace(), 2));
//        System.out.println("Free space (bytes): " + Format.Humanize_number(root.getFreeSpace(),2));
//
//      }
//
//      try {
//        uname = Systems.exec("uname -nprs");
//      }
//      catch (IOException e) {
//        Log.getInstance().E("CORE.Power",e.getMessage());
//        Display.getInstance().E("CORE.Power",e.getMessage());
//      }
//      catch (InterruptedException e) {
//        Log.getInstance().E("CORE.Power",e.getMessage());
//        Display.getInstance().E("CORE.Power",e.getMessage());
//      }
//    }

    public JSONObject toJson(){
      JSONObject tHost = new JSONObject();
        tHost.put("uptime",uptime);
        tHost.put("uname",uname);
        tHost.put("adress",adress);
        tHost.put("mac",mac);
        tHost.put("name",name);
        tHost.put("status",status);
        tHost.put("port",port);
        tHost.put("os",osType);

        tHost.put("cpu",cpu.toJson());
        tHost.put("memory",memory.toJson());

        if(power.isInicialized()){
          tHost.put("power",power.toJson());
        }

        if(disks!=null&&disks.size()>0){
          JSONArray tDisks = new JSONArray();
          for(Disk dd : disks){
            JSONObject td = new JSONObject();
            td.put("name",dd.getName());
            td.put("size",dd.getSize());
            td.put("smart",dd.getSmartRaw());
            tDisks.add(td);
          }
          tHost.put("disks",tDisks);
        }

        if(interfaces!=null&&interfaces.size()>0){
          JSONArray tInt = new JSONArray();
          for(Interface intr : interfaces){
            JSONObject ii = new JSONObject();
            ii.put("name",intr.getName());
            ii.put("adress",intr.getAdress());
            ii.put("mac",intr.getMac());
            ii.put("mask",intr.getMask());
            ii.put("state",intr.getState());
            ii.put("rx",intr.getRxBytes());
            ii.put("tx",intr.getTxBytes());
            tInt.add(ii);
          }
          tHost.put("interfaces",tInt);
        }

        if(slices!=null&&slices.size()>0){
          JSONArray tSl = new JSONArray();
          for(Slice s : slices){
            JSONObject ss = new JSONObject();
            ss.put("mountFrom",s.getMntNameFrom());
            ss.put("mountTo",s.getMntNameTo());
            ss.put("fsType",s.getFsType());
            ss.put("totalSize",s.getTotalSize());
            ss.put("usedSize",s.getUsedSize());
            ss.put("freeSize",s.getFreeSize());
            tSl.add(ss);
          }
          tHost.put("slices",tSl);
        }

        if(zpools!=null&&zpools.size()>0){
          JSONArray tZp = new JSONArray();
          for(Zpool z : zpools){
            JSONObject zz = new JSONObject();
            zz.put("name",z.getName());
            zz.put("alloc",z.getAlloc());
            zz.put("size",z.getSize());
            zz.put("free",z.getFree());
            zz.put("stat",z.getStat());
            tZp.add(zz);
          }
          tHost.put("zpools",tZp);
        }

      return  tHost;
    }


     public void Start() throws CoreException {

       JSONObject objDepen = (JSONObject) Settings.getInstance().Base.get("depend");
       String wolPath = (String)objDepen.get("wol");

       File file = new File(wolPath);
       if(file.exists() && file.isFile()){
         try{
           String raw = null;
           raw = Systems.exec(wolPath + " -i 192.168.0.255 -p 9 " + mac);
           Display.getInstance().I("HOST.Start","wol: " + raw);
           Log.getInstance().I("HOST::Start","wol raw: " + raw);
         }
         catch(IOException e) {
           throw new CoreException(205,"Host.Start",e.getMessage());
         }
         catch(InterruptedException e) {
           throw new CoreException(205,"Host.Start",e.getMessage());
         }

       }
       else{
         Display.getInstance().W("HOST.Start","Wol not found");
         Log.getInstance().W("HOST.Start"," wol not found");
         throw new CoreException(205,"Host.Start","Wol not found");
       }

    }

  public void Shutdown() throws CoreException {
    Log.getInstance().I("HOST::Shutdown","Start");
      //Display::getInstance().SetConsoleLine("HOST::Shutdown",'i');

    if(status.equals("up")){
      Map<String,Object> params = new HashMap<String, Object>();
      params.put("name",name);
      params.put("delay",0);

      try {
        NetClient nc = new NetClient();
        nc.Send(adress, port, new Command("shutdown", params, 1).toJson());
        nc.Close();
        Display.getInstance().D("HOST.Shutdown","Command send");
      }
      catch (IOException e) {
        throw new CoreException(201,"HOST.Shutdown","Connection error: "+e.getMessage());
      }
    }
    else{
      throw new CoreException(202,"HOST.Shutdown","Host "+name+" is already down");
    }
  }

  public void Reboot() throws CoreException {
    Log.getInstance().I("HOST::Shutdown","Start");
    //Display::getInstance().SetConsoleLine("HOST::Shutdown",'i');

    if(status.equals("up")){
      Map<String,Object> params = new HashMap<String, Object>();
      params.put("name",name);
      params.put("delay",0);

      try{
        NetClient nc = new NetClient();
        nc.Send(adress, port, new Command("reboot", params, 1).toJson());
        nc.Close();
        Display.getInstance().D("HOST.Reboot","Command send");
      }
      catch (IOException e) {
        throw new CoreException(203,"HOST.Reboot","Connection error: "+e.getMessage());
      }
    }
    else{
      throw new CoreException(204,"HOST.Reboot","Host "+name+" is already down");
    }
  }

    public String ExecCommand(String cmdHost) throws CoreException {
      Log.getInstance().D("HOST.ExecCommand","Start");
      String out = null;
      if(adress.equals("127.0.0.1")){
        try{
          out = Systems.exec(cmdHost);
        }
        catch (IOException e) {
          throw new CoreException(206,"Host.ExecCommand",e.getMessage());
        }
        catch (InterruptedException e) {
          throw new CoreException(206,"Host.ExecCommand",e.getMessage());
        }

      }
      else{
        Display.getInstance().D("Host.ExecCommand","send exec command ");
        if(status.equals("up")){
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("cmd",cmdHost);

          try{
            NetClient nc = new NetClient();
            nc.Send(adress, port, new Command("exec", params, 1).toJson());
            nc.Close();
            Display.getInstance().D("Host.ExecCommand","Command exec send to host: " + name);
            out = "Command exec send to host: " + name;
          }
          catch (IOException e) {
            status="down";
            throw new CoreException(206,"Host.Exec","Connection fail error: " + e.getMessage());
          }
        }
        else{
          throw new CoreException(206,"Host::Exec","Host "+name+" is already down");
        }
      }
      return out;
    }


    public Map<String, String> getKernelVariables() {
        return KernelVariables;
    }

    public void setKernelVariables(Map<String, String> kernelVariables) {
        KernelVariables = kernelVariables;
    }

    public String getDmesg() {
        return dmesg;
    }

    public void setDmesg(String dmesg) {
        this.dmesg = dmesg;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public Vector<Disk> getDisks() {
        return disks;
    }

    public void setDisks(Vector<Disk> disks) {
        this.disks = disks;
    }

    public Vector<Slice> getSlices() {
        return slices;
    }

    public void setSlices(Vector<Slice> slices) {
        this.slices = slices;
    }

    public Vector<Zpool> getZpools() {
        return zpools;
    }

    public void setZpools(Vector<Zpool> zpools) {
        this.zpools = zpools;
    }

    public Vector<Interface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Vector<Interface> interfaces) {
        this.interfaces = interfaces;
    }

    public void addInterface(Interface intface){
       if(!interfaces.contains(intface)){
         interfaces.add(intface);
       }
    }

    public void addDisk(Disk disk){
       if(!disks.contains(disk)){
         disks.add(disk);
       }
    }

    public void addSlice(Slice slice){
       if(!slices.contains(slice)){
         slices.add(slice);
       }
    }

    public void addZpool(Zpool zpool){
         if(!zpools.contains(zpool)){
           zpools.add(zpool);
         }
      }


}