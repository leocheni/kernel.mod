package net.msdh.kernel.handlers;

import com.sun.java.util.jar.pack.*;
import net.msdh.kernel.Core;
import net.msdh.kernel.answer.Result;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;

import java.util.HashMap;
import java.util.Map;

public class SysHandler {
   public String Run(Command cmd, Core core){
     System.out.println("SysHandler");

     Map<String,Object> result = new HashMap<String, Object>();

     int exitCode=0;
     String sAction = null, answer = null;
     int id = 0;
     try{
       sAction = cmd.getParams().get("action").toString();
       id = cmd.getId();
     }
     catch (Exception e){
       Display.getInstance().E("MODHandler","error get param");
     }

     if(sAction!=null&&sAction.equals("get")){
       String item = cmd.getParams().get("key").toString();

       if(item.equals("status")){

         StringBuilder status = new StringBuilder("Mod count ").
                                    append(core.Modules.GetModsCount()).
                                    append("|host count ").append(core.Hostss.GetHostCount()).
                                    append("|Status OK");

         result.put("message",status);
         answer = new Result(id,result).toJson();

//      if(c.Id()==4){
//        SmtpClient* sc = new SmtpClient();
//        sc->Connect("smtp.yandex.ru:465");
//        sc->SendMessage("testup@yandex.ru",status);
//        delete(sc);
//
//      }
//      else if(c.Id()==1){
//        Connect.SendServer(AnswerResult(1,Message(status).get()).GetJson());
//      }


       }
       else if(item.equals("config")){
         //cout<<"get config"<<endl;
         answer = new Result(id,Settings.getInstance().Base).toJson();
       }
       else if(item.equals("log")){

       }

     }
     else if(sAction!=null&&sAction.equals("set")){
       String key,value;
       key = cmd.getParams().get("key").toString();
       value = cmd.getParams().get("value").toString();

       //Settings.getInstance().Base.(key,value);

       //hangup();

       //Connect.SendServer(AnswerResult(1,Message("Value is change").get()).GetJson());

     }
     else if(sAction!=null&&sAction.equals("hendup")){
        //hangup();
        //Connect.SendServer(AnswerResult(1,Message("Configuration updated").get()).GetJson());

     }
     return answer;

   }
}
