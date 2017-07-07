package net.msdh.kernel.mail;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import net.minidev.json.JSONObject;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.base.Queue;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.net.ImapClient;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.net.NetServer;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 05.06.17
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class Mail extends Thread{
  private String coreAdress;
  private int corePort;
  private int port;
  private static volatile boolean shutdownFlag;
  private Thread t;


public Mail(){
  coreAdress = Settings.getInstance().Base.get("coreIP").toString();
  corePort = Integer.parseInt(Settings.getInstance().Base.get("corePort").toString());
  port = Integer.parseInt(Settings.getInstance().Base.get("port").toString());
  shutdownFlag = true;
}

public int Load(){

  //Settings.Inicialize();
 // Listener(); //start run as thread
  shutdownFlag = false;
  t = new Thread(this,"Mail run handler");
  t.start();

  Map<String,Object> params = new HashMap<String, Object>();
  params.put("item","mail");
  params.put("action","start");
  params.put("port",port);

  try{
    NetClient nc = new NetClient();
    nc.Send(coreAdress,corePort,new Command("mod",params,1).toJson());
    nc.Close();
  }
  catch(IOException e){
    Log.getInstance().E("Mail::Load",e.getMessage());
    Display.getInstance().E("Mail::Load ",e.getMessage());
  }
  //File::createPidFile((Settings::getInstance().GetParam("run")).c_str());
  return 0;
}

public int Unload(){

  Map<String,Object> params = new HashMap<String, Object>();
  params.put("item","mail");
  params.put("action","stop");

  try{
    NetClient nc = new NetClient();
    nc.Send(coreAdress,corePort, new Command("mod", params, 1).toJson());
  }
  catch (IOException e) {
    Log.getInstance().E("Mail.Unload",e.getMessage());
  }
  return 0;
}

public int Manager(){
  Log.getInstance().D("Mail.manager","Start");
  Display.getInstance().D("Mail.manager","Start");

  String status = "working main loop";

  try{
    NetServer ns = new NetServer();

    ns.Listen(port);

    while(true) {  // main accept() loop

      ns.Accept();
      try{
        Queue q = new Queue();
        q.JParser(ns.Read());
        Display.getInstance().D("Mail.Manager",q.getCommand().toJson());
        Log.getInstance().D("Mail.Manager","Comand: "+q.getCommand().toJson());

        if(q.getCommand().getMethod().equals("get")){
              String action = null;
              try{
                action = q.getCommand().getParams().get("action").toString();
              }
              catch(Exception e){
                Display.getInstance().E("MAIL::Manager"," error: "+e.getMessage());
              }


              if(action.equals("status")){
                Display.getInstance().D("Mail.Manager","Get command "+ q.getCommand().toJson());
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("status",status);
                params.put("name","mail");
                try {
                  NetClient nc = new NetClient();
                  nc.Send(coreAdress, corePort, (new Command("mod", params, 1)).toJson());
                  nc.Close();
                }
                catch (IOException e) {
                  Display.getInstance().E("MAIL::Manager", " error: " + e.getMessage());
                }

              }
            }

            else if(q.getCommand().getMethod().equals("set")) {
                Display.getInstance().D("Mail.Manager","Get command "+ q.getCommand().toJson());
            }
            else if(q.getCommand().getMethod().equals("send")){
              String message;
              String to;
              try{
                message = q.getCommand().getParams().get("message").toString();
                to = q.getCommand().getParams().get("to").toString();

            //    MailConnector* ms = new MailConnector();
            //    ms->Connect("smtp.yandex.ru:465");
            //    ms->SendMessage(to,message);
            //    ms->Close();
            //    delete(ms);
              }
              catch(Exception e){
                Display.getInstance().E("MAIL::Manager"," Error: "+e.getMessage());
                Log.getInstance().E("MAIL.Manager","Error: " + e.getMessage());
              }
            }
            else if(q.getCommand().getMethod().equals("exit")) {
              Display.getInstance().D("Mail.Manager","Get command "+ q.getCommand().toJson());
              Log.getInstance().D("Mail.Manager","Get command "+ q.getCommand().toJson());
              shutdownFlag = true;
              Log.getInstance().D("Mail.Manager","Waiting mail thread");
              try{
                t.interrupt();
                t.join();
              }
              catch(InterruptedException e){
                Log.getInstance().D("Mail.Manager","Error join: "+ e.getMessage());
              }
              Log.getInstance().D("Mail.Manager","Waiting mail thread close");
              return 0;
            }
        }
        catch(IOException e){
          Display.getInstance().E("MAIL.Manager"," error: "+e.getMessage());
          Log.getInstance().E("MAIL.Manager","Error: " + e.getMessage());
        }
        catch (JSONRPC2ParseException e) {
          Display.getInstance().E("MAIL.Manager"," error: "+e.getMessage());
          Log.getInstance().E("MAIL.Manager","Error: " + e.getMessage());
        }
    }//accept loop
  }
  catch(IOException e){
    Display.getInstance().E("MAIL.Manager"," error: "+e.getMessage());
    Log.getInstance().E("MAIL.Manager","Error: " + e.getMessage());
  }
  return 0;
}


public void run(){
  Log.getInstance().D("Mail.run","Start");
  Display.getInstance().D("Mail.run","Start");

  while(!shutdownFlag){

    Log.getInstance().D("MAIL::run","next step");
    try {
      ImapClient mc = new ImapClient("imap.yandex.ru","993");
      mc.Connect("base.msdh@yandex.ru","mamontezmamontyan");
      Log.getInstance().D("MAIL::run","login");

      mc.SelectMailBox("INBOX|commands");

      JSONObject message = mc.GetMessage("1");
      if(message!=null){
        Display.getInstance().D("Mail.run",message.toJSONString());
      }

      ///todo реализовать разбор команды и отправку ее в ядро

      int[] i = new int[1];
      i[0] = 1;
      mc.DeleteMessage(i);
      mc.CloseBox();
      mc.Logout();
    }
    catch (CoreException e) {
       Log.getInstance().E("MAIL::run",e.getMessage());
       Display.getInstance().D("MAIL::run",e.getMessage());
    }

    Display.getInstance().D("MAIL::run","sleeping");

    try{
      Thread.sleep(300000);
    }
    catch (InterruptedException e) {
      Log.getInstance().I("MAIL::run","Interapt exception");
    }

    Log.getInstance().I("MAIL::run","============================");
  }
}
}
