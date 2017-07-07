package net.msdh.kernel;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.answer.*;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.base.Queue;
import net.msdh.kernel.device.Dev;
import net.msdh.kernel.device.Devices;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.hosts.Host;
import net.msdh.kernel.hosts.Hosts;
import net.msdh.kernel.module.Mod;
import net.msdh.kernel.module.Modules;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.net.NetServer;
import net.msdh.kernel.rule.Rule;
import net.msdh.kernel.security.Session;
import net.msdh.kernel.security.User;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Core {

  private final static String TAG = "CORE";
  public Session Session;
  public Modules Modules;
  public Devices Devices;
  public Rule Rules;
  public Hosts Hostss;

  private String coreAdress;
  private int corePort;

  public Core(){

  }

  public int Load() throws CoreException {

    Session = new Session();
    Modules = new Modules();
    Devices = new Devices();
    Rules = new Rule();
    Hostss = new Hosts();

    Display.getInstance().I(TAG+".Load","Loading core...");
    if(Settings.getInstance().isInicialized()){
      try{
        JSONArray aUsers = (JSONArray)Settings.getInstance().Base.get("users");
        JSONArray aHosts = (JSONArray) Settings.getInstance().Base.get("hosts");
        JSONArray aModules = (JSONArray) Settings.getInstance().Base.get("modules");
        JSONArray aDevices = (JSONArray) Settings.getInstance().Base.get("diveces");
        JSONObject aBase = (JSONObject)Settings.getInstance().Base.get("base");
        corePort = Integer.parseInt(aBase.get("port").toString());

        if(aUsers!=null){
          Display.getInstance().I(TAG+".load","=============================");
          for(Object oUser: aUsers){
            JSONObject nUser = (JSONObject)oUser;
            User tUser = new User();
            tUser.setLogin(nUser.get("login")==null?"":nUser.get("login").toString());
            tUser.setPassword(nUser.get("password")==null?"":nUser.get("password").toString());
            tUser.setName(nUser.get("name")==null?"":nUser.get("name").toString());

            Session.AddUser(tUser);
            Display.getInstance().I(TAG+".load","User add " + tUser.getLogin());
          }
        }
        if(aHosts!=null){
          Display.getInstance().I(TAG+".load","=============================");
          for(Object oHost: aHosts){
            JSONObject nHost = (JSONObject)oHost;
            Host tHost = new Host();
            tHost.setName(nHost.get("name")==null?"":nHost.get("name").toString());
            tHost.setAdress(nHost.get("adress")==null?"":nHost.get("adress").toString());
            tHost.setMac(nHost.get("mac")==null?"":nHost.get("mac").toString());
            tHost.setStatus(nHost.get("status")==null?"":nHost.get("status").toString());
            tHost.setUptime("n/a");
            tHost.setDmesg("n/a");
            tHost.setOsType("n/a");
            tHost.setUname("n/a");
            Hostss.AddHost(tHost);
            Display.getInstance().I(TAG+".load","Host add " + tHost.getName());
          }
        }
        if(aModules!=null){
           Display.getInstance().I(TAG+".load","=============================");
           for(Object oMod: aModules){
            JSONObject nMod = (JSONObject)oMod;
            Mod tMod = new Mod();
            tMod.setName(nMod.get("name")==null?"":nMod.get("name").toString());
            tMod.setAdress(nMod.get("adress")==null?"":nMod.get("adress").toString());
            tMod.setBin(nMod.get("mac")==null?"":nMod.get("mac").toString());
            tMod.setCorePort(corePort);
            tMod.setPort((nMod.get("port")==null?0:(Integer.parseInt(nMod.get("port").toString()))));
            tMod.setLog(nMod.get("log")==null?"":nMod.get("log").toString());
            tMod.setRun(nMod.get("run")==null?"":nMod.get("run").toString());
            tMod.setPriority(nMod.get("priority")==null?0:Integer.parseInt(nMod.get("priority").toString()));
            tMod.setStatus(nMod.get("status")==null?"":nMod.get("status").toString());
            Modules.AddModule(tMod);
            Display.getInstance().I(TAG+".load","Mod add " + tMod.getName());
          }
        }
        if(aDevices!=null){
             Display.getInstance().I(TAG+".load","=============================");
           for(Object oDev: aDevices){
            JSONObject nDev = (JSONObject)oDev;
            Dev tDev = new Dev();
            tDev.setName(nDev.get("name")==null?"":nDev.get("name").toString());
            tDev.setStat(nDev.get("status")==null?"":nDev.get("status").toString());
            tDev.setEnable(Boolean.parseBoolean(nDev.get("enable")==null?"":nDev.get("enable").toString()));
            Devices.AddDev(tDev);
            Display.getInstance().I(TAG+".load","Dev add " + tDev.getName());
          }
        }

        Modules.setStatus("core","up");
      }
      catch (Exception e){
        throw new CoreException(100,TAG+".Load",e.getMessage());
      }
    }
    else {
      throw new CoreException(100,TAG+".Load","Settings not inicialize");
    }
    Display.getInstance().I(TAG+".Load","End");
    return 0;
  }

  public int Unload(){
    try{
      Modules.Stop("core");
      Modules.Unload(false);
    }
    catch(CoreException e){
      Display.getInstance().E(TAG+".unload",e.getMessage());
      Log.getInstance().E(TAG+".unload",e.getMessage());
    }

    Display.getInstance().I(TAG+".Unload","Core unloaded");
    return 0;
  }

  public int Manager(){

   Display.getInstance().I(TAG+".Manager","Start");
   try{

     NetServer ns = new NetServer();
     NetClient nc = new NetClient();

     ns.Listen(corePort);

     while(true){
       Socket accept = null;
       try{
	     accept = ns.Accept();
         String line = ns.Read();
         line = line.replace("\r","").replace("\n",""); ///todo переделать в spore отправку ответа без переводов строк и это убрать


         Display.getInstance().D(TAG+".Manager", (new Date()) + ": Get raw command: " + line);
         Log.getInstance().D(TAG+".Manager", "Get raw command: " + line);

         try{

           JSONObject aBase = (JSONObject)Settings.getInstance().Base.get("base");
           String logLevel = aBase.get("logLevel").toString();
           if(logLevel!=null&&logLevel.equals("DEBUG")){
             Mod console = Modules.GetMod("console");
             if(console.getStatus().equals("up")){
               nc.Send(console.getAdress(),console.getPort(),line);
             }
           }
         }
         catch (CoreException e) {
           Log.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
           Display.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
         }
         catch(Exception e){
           Log.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
           Display.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
         }

         Queue Q = new Queue();
         Q.JParser(line);

         if((Q.getCommand().getMethod().equals("system"))&&(Q.getCommand().getParams().get("action").equals("down"))){
           if(Q.getCommand().getParams().containsKey("item")){
             int dilay = Integer.parseInt(Q.getCommand().getParams().get("item").toString());
             ///todo установить таймер на выключение, разработать модуль таймера
             Map<String,Object> error = new HashMap<String, Object>();

             try {
               Mod timer = Modules.GetMod("timer");
               if(timer.getStatus().equals("up")){

                 Map<String,Object> params = new HashMap<String, Object>();
                 params.put("action","down");
                 Command shutdownCmd = new Command("system",params,9);

                 Map<String,Object> paramsTmr = new HashMap<String, Object>();
                 paramsTmr.put("count",dilay);
                 paramsTmr.put("command",shutdownCmd.toJson());
                 try{
                   nc.Send(timer.getAdress(),timer.getPort(),new Command("add",paramsTmr,0).toJson());
                   nc.Close();
                 }
                 catch(Exception e){
                   error.put("code",200);
                   error.put("source","core");
                   error.put("message",e.getMessage());
                   ns.Send( new net.msdh.kernel.answer.Error(0,error).toJson());
                 }
               }
               else{

                  error.put("code",200);
                  error.put("source","core");
                  error.put("message","Error: timer mod is down state");

                  ns.Send( new net.msdh.kernel.answer.Error(0,error).toJson());

                 Log.getInstance().E(TAG+".Manager","Error: timer mod is down state");
                 Display.getInstance().E(TAG+".Manager","Error: timer mod is down state");
               }
             }
             catch (CoreException e) {
               error.put("code",e.getCode());
               error.put("source",e.getSource());
               error.put("message",e.getMessage());

               ns.Send( new net.msdh.kernel.answer.Error(0,error).toJson());
               Log.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
               Display.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
             }

           }
           else{

             Map<String,Object> result = new HashMap<String, Object>();
             result.put("message","Core is down");
             ns.Send(new Result(Q.getCommand().getId(),result).toJson());
             break;
           }
         }
         else{
           Handler hd = new Handler(accept);
           hd.setCore(this);
           hd.setCommand(Q.getCommand());
           Thread t = new Thread(hd,"Client handler");
           t.start();
         }
       }
       catch (JSONRPC2ParseException e) {
         if(accept!=null){
           ns.CloseAccept();
         }
         Log.getInstance().E(TAG+".Manager","Command parsing error: " + e.getMessage());
         Display.getInstance().E(TAG+".Manager","Command parsing error: " + e.getMessage());
       }
       catch(Exception e){
         Log.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
         Display.getInstance().E(TAG+".Manager","Error: " + e.getMessage());
       }
       //finally {  }
	 }
     ns.Close();
   }
   catch (IOException e) {
     Log.getInstance().E(TAG+".Manager","Global error: " + e.getMessage());
     Display.getInstance().E(TAG+".Manager","Global error: " + e.getMessage());
   }
   return 0;
  }

}
