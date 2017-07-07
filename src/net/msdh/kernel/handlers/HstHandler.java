package net.msdh.kernel.handlers;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.Core;
import net.msdh.kernel.answer.*;
import net.msdh.kernel.answer.Error;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.hosts.Host;
import net.msdh.kernel.system.Disk;
import net.msdh.kernel.system.Interface;
import net.msdh.kernel.system.Slice;
import net.msdh.kernel.system.Zpool;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class HstHandler {
   public String Run(Command cmd, Core core){
     Display.getInstance().D("HstHandler", "Start");
     String item = null,action = null,answer=null;
     int id = 0;
     Map<String,Object> result = new HashMap<String, Object>();
     Map<String,Object> error = new HashMap<String, Object>();
     id = cmd.getId();
     try{
       action = cmd.getParams().get("action").toString();
     }
     catch (Exception e){
       Display.getInstance().E("HstHandler","error get param action");
     }

     try{
       item = cmd.getParams().get("item").toString();
     }
     catch (Exception e){
       Display.getInstance().E("HstHandler","error get param item");
     }

     if(action!=null&&action.equals("show")){

       if(item==null){
         answer = new Result(id,core.Hostss.GetHostList()).toJson();
       }
       else{
         try{
           answer = new Result(id, core.Hostss.GetHost(item).toJson()).toJson();
         }
         catch (CoreException e) {

           error.put("code",e.getCode());
           error.put("source",e.getSource());
           error.put("message",e.getMessage());
           answer = new Error(id,error).toJson();

           Display.getInstance().E("HstHandler","Error: " + e.getMessage());
           Log.getInstance().E("HstHandler","Error: " + e.getMessage());
         }
       }
     }
     else if(action!=null&&action.equals("start")){
       try{
         core.Hostss.GetHost(item).Start();
         result.put("message","Send magic packet");
         answer = new Result(id,result).toJson();
       }
       catch(CoreException e){

         error.put("code",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();

         Display.getInstance().E("HstHandler","Error: " + e.getMessage());
         Log.getInstance().E("HstHandler","Error: " + e.getMessage());
       }
     }
     else if(action!=null&&action.equals("shutdown")){
       try{
         core.Hostss.GetHost(item).Shutdown();
         result.put("message","Host shutdow");
         answer = new Result(id,result).toJson();
       }
       catch(CoreException e){
         error.put("code",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();

         Display.getInstance().E("HstHandler","Error: " + e.getMessage());
         Log.getInstance().E("HstHandler","Error: " + e.getMessage());
       }
     }
     else if(action!=null&&action.equals("reboot")){

       try{
         core.Hostss.GetHost(item).Reboot();
         result.put("message","Host rebooting");
         answer = new Result(id,result).toJson();
       }
       catch(CoreException e){
         error.put("code",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();

         Display.getInstance().E("HstHandler","Error: " + e.getMessage());
         Log.getInstance().E("HstHandler","Error: " + e.getMessage());
       }
     }
     else if(action!=null&&action.equals("started")){
       try{
         JSONObject tParams= (JSONObject) cmd.getParams().get("host");

         try {
           Host host = core.Hostss.GetHost(tParams.get("name").toString());

           if(host.getInterfaces()!=null){
             host.getInterfaces().clear();
           }
           if(host.getDisks()!=null){
             host.getDisks().clear();
           }
           if(host.getSlices()!=null){
             host.getSlices().clear();
           }
           if(host.getZpools()!=null){
             host.getZpools().clear();
           }

           host.setStatus("up");
           try{
             host.setPort(Integer.parseInt(tParams.get("port").toString()));
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }
           try{
             host.setOsType(tParams.get("os").toString());
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }
           try{
              host.setUname(tParams.get("uname").toString());
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }

           try{
             host.setUptime(tParams.get("uptime").toString());
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }

           try{
             JSONObject tCpu = (JSONObject) tParams.get("cpu");
             host.getCpu().cores = Integer.parseInt(tCpu.get("cpuCores").toString());
             host.getCpu().freq = tCpu.get("cpuFreq").toString();
             host.getCpu().model = tCpu.get("cpuModel").toString();
             host.getCpu().temp = tCpu.get("cpuTemp").toString();
             host.getCpu().usage = Integer.parseInt(tCpu.get("cpuUsed").toString());
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }

           try{
             JSONObject tMemory = (JSONObject) tParams.get("memory");
             //host.memory.physical = tMemory.get<double>("memPhysical");
             host.getMemory().total = Double.parseDouble(tMemory.get("memTotal").toString());
             host.getMemory().usage = Double.parseDouble(tMemory.get("memUsed").toString());
             host.getMemory().free = Double.parseDouble(tMemory.get("memFree").toString());
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }

           try{
             JSONObject tPower = (JSONObject) tParams.get("power");
             host.getPower().setCharge(tPower.get("charge").toString());
             host.getPower().setModel(tPower.get("model").toString());
             host.getPower().setInputVoltage(tPower.get("inputVoltage").toString());
             host.getPower().setOutputVoltage(tPower.get("outputVoltage").toString());
             host.getPower().setLoad(tPower.get("load").toString());
             host.getPower().setType(tPower.get("type").toString());
             host.getPower().setStatus(tPower.get("status").toString());
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }

           try{
             JSONArray tInterfaces = (JSONArray) tParams.get("interfaces");
             for(Object i: tInterfaces){
               Interface tInterface = new Interface();
               tInterface.setName(((JSONObject)i).get("name").toString());
               tInterface.setAdress(((JSONObject)i).get("adress").toString());
               tInterface.setMac(((JSONObject)i).get("mac").toString());
               tInterface.setMask(((JSONObject)i).get("mask").toString());
               tInterface.setState(((JSONObject)i).get("state").toString());
               tInterface.setTxBytes(((JSONObject)i).get("tx").toString());
               tInterface.setRxBytes(((JSONObject)i).get("rx").toString());
               host.addInterface(tInterface);
             }
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }
           try{
             JSONArray tDisks = (JSONArray) tParams.get("disks");
             for(Object d: tDisks){
               Disk tDisk = new Disk();
               tDisk.setName(((JSONObject) d).get("name").toString());
               host.addDisk(tDisk);
             }
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }
           try{
             JSONArray tSlices = (JSONArray) tParams.get("slices");
             for(Object s: tSlices){
               Slice tSlice = new Slice();
               tSlice.setMntNameFrom(((JSONObject)s).get("mountFrom").toString());
               tSlice.setMntNameTo(((JSONObject)s).get("mountTo").toString());
               tSlice.setTotalSize(((JSONObject)s).get("totalSize").toString());
               tSlice.setUsedSize(((JSONObject)s).get("usedSize").toString());
               tSlice.setFreeSize(((JSONObject)s).get("freeSize").toString());
               host.addSlice(tSlice);
             }
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }
           try{
             JSONArray tZpools = (JSONArray) tParams.get("zpools");
             for(Object z: tZpools){
               Zpool tZpool = new Zpool(((JSONObject) z).get("name").toString());
               //tZpool.setName(((JSONObject) z).get("name").toString());
               //tZpool.setAlloc();
               //tZpool.setSize();
               //tZpool.getFree();
               host.addZpool(tZpool);
             }
           }
           catch(Exception e){
             Display.getInstance().W("HstHandler.Started",e.getMessage());
	         Log.getInstance().W("HstHandler.Started",e.getMessage());
           }

         }
         catch(CoreException e){

           if(e.getCode()==500){
              core.Hostss.AddHost(new Host(tParams));
           }
           else{
             Display.getInstance().E("HstHandler.Started","Error: " + e.getMessage());
             Log.getInstance().E("HstHandler.Started","Error: " + e.getMessage());
           }
         }
       }
       catch (Exception e){
	     Display.getInstance().E("HstHandler.Started",e.getMessage());
	     Log.getInstance().E("HstHandler.Started",e.getMessage());
       }
     }
     else if(action!=null&&action.equals("stoped")){
       try{
         core.Hostss.GetHost(item).setStatus("down");
       }
       catch(CoreException e){
         Display.getInstance().E("HstHandler.Stoped",e.getMessage());
	     Log.getInstance().E("HstHandler.Stoped",e.getMessage());
       }
     }
     else if(action!=null&&action.equals("refrash")){
       try{
         core.Hostss.GetHost(item).setStatus("up");
       }
       catch(CoreException e){
         Log.getInstance().E("CORE::HstHandler",e.getMessage());
       }
     }
     else if(action!=null&&action.equals("rescan")){   /// todo реализовать опрос всех хостов
       //get info all hosts
     }
     else if(action!=null&&action.equals("exec")){
       String cmdHost;
       cmdHost = cmd.getParams().get("cmd").toString();

       try{
          String ans = core.Hostss.GetHost(item).ExecCommand(cmdHost);
          result.put("message",ans);
          answer = new Result(id,result).toJson();
       }
       catch(CoreException e){
         Log.getInstance().E("CORE::HstHendler",e.getMessage());
       }
     }
     else if(action!=null&&action.equals("get")){
       String param;

       param = cmd.getParams().get("param").toString();

       if(param.equals("status")){
         try{
           result.put("status",core.Hostss.GetHost(item).getStatus());
           answer = new Result(id,result).toJson();
         }
         catch(CoreException e){
           Log.getInstance().E("CORE::HstHendler",e.getMessage());
         }
       }
       else if(param.equals("cpu")){

       }
     }
     return answer;
   }
}
