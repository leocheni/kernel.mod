package net.msdh.kernel.timer;

import net.minidev.json.JSONObject;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;
import sun.plugin.dom.html.ns4.NS4DOMObject;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.17
 * Time: 9:35
 * To change this template use File | Settings | File Templates.
 */
public class TimerContext extends Thread{

   public Command Cmd;
   public String CoreAdress;
   public int CorePort;
   public int Count;
   public String StartDate;
   public int Id;
   public boolean Active;


   public TimerContext(){

   }




   public void run(){

     Display.getInstance().D("TIMERContext::Run","CoreAdress: "+CoreAdress);
     Display.getInstance().D("TIMERContext::Run","CorePort: "+CorePort);
     Log.getInstance().D("TIMERCONTEXT","CoreAdress: "+CoreAdress);
     Log.getInstance().D("TIMERCONTEXT","CorePort: "+CorePort);

     Active = true;

       ///todo реализовать циклическое выполнение таймера

       try{
         Thread.sleep(Count);
       }
       catch (InterruptedException e) {
         Log.getInstance().E("TIMERCONTEXT",e.getMessage());
         Display.getInstance().E("TIMERContext::",e.getMessage());
       }

       try{
       NetClient nc = new NetClient();
       nc.Send(CoreAdress,CorePort,Cmd.toJson());
       nc.Close();
     }
     catch (IOException e) {
       Log.getInstance().E("TIMERCONTEXT",e.getMessage());
       Display.getInstance().E("TIMERContext::",e.getMessage());
     }

     Display.getInstance().I("TIMERContext","Sended command to core: " + Cmd.toJson());
     Log.getInstance().I("TIMERCONTEXT","Sended command to core: " + Cmd.toJson());
     Active = false;
     //isRunning = false;
}

public JSONObject toJson(){
   JSONObject tmpTmr = new JSONObject();
   tmpTmr.put("id",Id);
   tmpTmr.put("count",Count);
   tmpTmr.put("activ",Active);
   tmpTmr.put("startdate",StartDate);
   //tmpTmr.push_back(make_pair("command",Cmd.getJsonPtree()));
   tmpTmr.put("command", Cmd);
   return tmpTmr;
}


}
