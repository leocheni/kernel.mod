package net.msdh.kernel.timer;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.answer.Result;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.base.Queue;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.net.NetServer;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;
import java.util.Date;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.17
 * Time: 9:21
 * To change this template use File | Settings | File Templates.
 */
public class Timer {
   private int countTimer;
   private Vector<TimerContext> timerContexts;
   private String coreAdress;
   private int corePort;

   public Timer() {
     countTimer = 0;
      //timerContexts.capacity(200);
     corePort = Integer.parseInt(Settings.getInstance().Base.get("corePort").toString());
     this.timerContexts = new Vector<TimerContext>();
     coreAdress = "127.0.0.1";
   }

   public int Load(){
     JSONObject params = new JSONObject();
     params.put("item","timer");
     params.put("action","start");
     params.put("port","60011");

     try {
       NetClient nc = new NetClient();
       nc.Send(coreAdress, corePort, new Command("mod", params,1).toJson());
       nc.Close();
     }
     catch(IOException e){
       Log.getInstance().E("Timer.load", e.getMessage());
     }
     Log.getInstance().D("TIMER"," Timer load");
     return 0;
   }

   public int Manager(){
     Log.getInstance().D("TIMER","manager start");
     try{

      //Log::getInstance().D("TIMER","Listen port: " + Settings::getInstance().GetParam("port"));
      NetServer ns = new NetServer();
      ns.Listen(Integer.parseInt(Settings.getInstance().Base.get("port").toString()));

      while(true){  // main accept() loop
        ns.Accept();
        try{

          Queue q = new Queue();
          q.JParser(ns.Read());

          Log.getInstance().D("TIMER", "Recv command: " + q.getCommand().toJson());

          if(q.getCommand().getMethod().equals("add")){
            Log.getInstance().D("TIMER","Recv add command");
            countTimer++;
            int _count=0;
            Command cmd = null;

            try{
              _count = Integer.parseInt(q.getCommand().getParams().get("count").toString());
            }
            catch(Exception e){
              Display.getInstance().E("TIMER","Error: "+ e.getMessage());
              Log.getInstance().E("TIMER",e.getMessage());
            }

            try{
              cmd = new Command(q.getCommand().getParams().get("command").toString());
            }
            catch(Exception e){
              Display.getInstance().E("TIMER","Error: " + e.getMessage());
              Log.getInstance().E("TIMER",e.getMessage());
            }

            if(poolBuse()){

              Display.getInstance().D("TIMER","Add timer context");
              Log.getInstance().D("TIMER","Add TimerContext");

              TimerContext tc = new TimerContext();
              tc.CoreAdress = coreAdress;
              tc.CorePort = corePort;
              tc.Count=_count;
              tc.Cmd = cmd;
              tc.Id = countTimer;
              tc.StartDate = new Date().toString();
              tc.start();
              timerContexts.add(tc);
            }
            else{
              Display.getInstance().D("TIMER","Update timer contecst");
              Log.getInstance().D("TIMER","Update TimerContext");

              TimerContext tce = getTimerContext();
              if(tce!=null){

                tce.Count = _count;
                tce.Cmd = cmd;
                tce.Id = countTimer;
                tce.StartDate = new Date().toString();
                tce.start();
              }
            }
          }
          else if(q.getCommand().getMethod().equals("del")){
            Display.getInstance().D("TIMER","Recv del method");
            Log.getInstance().D("TIMER","Recv del command");
            int item = 0;

            try{
              item = Integer.parseInt(q.getCommand().getParams().get("item").toString());
            }
            catch(Exception e){
              Display.getInstance().E("TIMER","Error: " + e.getMessage());
              Log.getInstance().E("TIMER",e.getMessage());
            }

            for(TimerContext tc : timerContexts){
              if(tc.Id == item){
                tc.interrupt();
                Display.getInstance().D("Timer","timer[" + tc.Id + "] deleted");
                break;
              }
            }
          }
          else if(q.getCommand().getMethod().equals("show")){
            Log.getInstance().D("TIMER","Recv show command");
            Display.getInstance().D("TIMER","Count timers: " +timerContexts.size());
            ns.Send(new Result(1,getTimersList()).toJson());
          }
          else if(q.getCommand().getMethod().equals("exit")){
            Log.getInstance().D("TIMER","Recv exit command");
            break;
          }
        }
        catch(IOException e){
          Display.getInstance().E("TIMER.Manager","Error: "+e.getMessage());
          Log.getInstance().E("TIMER.Manager","Error: " + e.getMessage());
        }
        catch (JSONRPC2ParseException e) {
          Display.getInstance().D("TIMER.Manager","Error: " + e.getMessage());
          Log.getInstance().E("TIMER.Manager", "Error: " + e.getMessage());
        }
      }//accept loop
    }
    catch(IOException e){
      Display.getInstance().D("TIMER.Manager", "Error: " + e.getMessage());
      Log.getInstance().E("TIMER.Manager","Error: " + e.getMessage());
    }

    return 0;
   }

   public int Unload(){
     try{
       Display.getInstance().D("TIMER","Unload entre");
       JSONObject params = new JSONObject();
       params.put("item","timer");
       params.put("action","stop");
       NetClient nc = new NetClient();
       nc.Send(coreAdress, corePort, (new Command("mod", params, 1)).toJson());
       nc.Close();
       Display.getInstance().D("TIMER","Unload exit");
       Log.getInstance().D("TIMER::Unload"," Timer exit");
     }
     catch(IOException e){
       Display.getInstance().E("TIMER","Unload core is down");
       Log.getInstance().D("TIMER::Unload"," core is down: ");
     }
     return 0;
   }

   public void startThread(TimerContext tc){


   }

   public JSONArray getTimersList(){
     JSONArray timers = new JSONArray();

     if(timerContexts.size()>0){
       for(TimerContext tc : timerContexts){
          timers.add(tc.toJson());
       }
     }
     //else{
       //timers.add(Integer.parseInt("timers"), "0");
     //}
     return timers;
   }

   private boolean poolBuse(){
     if(timerContexts.size()==0) return true;

     for(TimerContext tc : timerContexts){
       if(!tc.Active){
         return false;
       }
     }
     return true;
   }

   private TimerContext getTimerContext(){
     TimerContext tct = null;
     for(TimerContext tc : timerContexts){
       if(!tc.Active){
         tct = tc;
       }
     }
     return tct;
   }
}
