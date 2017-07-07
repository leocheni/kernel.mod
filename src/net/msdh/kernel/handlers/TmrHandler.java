package net.msdh.kernel.handlers;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import net.minidev.json.JSONObject;
import net.msdh.kernel.Core;
import net.msdh.kernel.answer.*;
import net.msdh.kernel.answer.Error;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.module.Mod;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TmrHandler {
   public String Run(Command cmd, Core core){
     String action = null, answer = null;
     int id = 0;

     Map<String,Object> result = new HashMap<String, Object>();
     Map<String,Object> error = new HashMap<String, Object>();

     try{
       action = cmd.getParams().get("action").toString();
       id = cmd.getId();
     }
     catch(Exception e){
       Display.getInstance().E("TmrHandler","Error get param: " +e.getMessage());
       Log.getInstance().E("TmrHandler","Error get param: " +e.getMessage());
     }

     if(action!=null&&action.equals("add")){

       try {
         if(core.Modules.GetMod("timer").getStatus().equals("up")){
           JSONObject command = null;
           int timerCount=0;
           try{
             command = (JSONObject)cmd.getParams().get("command");
           }
           catch(Exception e){
             Display.getInstance().E("TmrHandler","Error: "+e.getMessage());
             Log.getInstance().E("TmrHandler","Error: "+e.getMessage());
           }

           try{
             timerCount = Integer.parseInt(cmd.getParams().get("count").toString());
           }
           catch(Exception e){
             Display.getInstance().E("TmrHandler","Error: "+e.getMessage());
             Log.getInstance().E("TmrHandler","Error: "+e.getMessage());
           }

           Mod m = core.Modules.GetMod("timer");

           JSONObject params = new JSONObject();
           params.put("count",timerCount);
           params.put("command", command);

           NetClient nc = new NetClient();
           nc.Send(m.getAdress(), m.getPort(), new Command("add", params, 1).toJson());
           nc.Close();

           result.put("message","Timer add");
           answer = new Result(id,result).toJson();

         }
         else{
           error.put("code","2000");
           error.put("source","TnrHandler");
           error.put("message","Timer mod down state");
           answer = new net.msdh.kernel.answer.Error(id,error).toJson();
           Display.getInstance().E("TmrHandler","Error: Timer mod down state");
           Log.getInstance().E("TmrHandler","Error: Timer mod down state");
         }
       }
       catch (CoreException e) {
         Display.getInstance().E("TmrHandler","Error: "+e.getMessage());
         Log.getInstance().E("TmrHandler","Error: "+e.getMessage());
         error.put("code",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
       catch(IOException e){
         Display.getInstance().E("TmrHandler","Error: "+e.getMessage());
         Log.getInstance().E("TmrHandler","Error: "+e.getMessage());
         error.put("code","2001");
         error.put("source","TmrHandler");
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null && action.equals("del")){
        ///todo реализовать отправку команды удаления таймера

        //Connect.SendServer(underConstruction(Q.command));
     }
     else if (action!=null && action.equals("show")){
       try{
         Mod m = core.Modules.GetMod("timer");
         JSONObject params = new JSONObject();//, errors;
         NetClient nc = new NetClient();
         nc.Send(m.getAdress(), m.getPort(), new Command("show", params, 1).toJson());
         String ans = nc.Read();
         Display.getInstance().D("CORE::TimerHandler","Show answer: " + ans);

         JSONRPC2Request request = null;
		 try {
		   request = JSONRPC2Request.parse(ans);
           result = request.getNamedParams();
           //result.put(params);
           answer = new Result(id,result).toJson();
		 }
         catch (JSONRPC2ParseException e) {
		   Display.getInstance().E("CORE::TimerHandler","Error: "+e.getMessage());
		 }
       }
       catch(CoreException e){
         Display.getInstance().E("CORE::TimerHandler","Error: "+e.getMessage());
       }
       catch(IOException e){
         Display.getInstance().E("CORE::TimerHandler","Error: "+e.getMessage());
       }
     }
     else if (action!=null && action.equals("done")){
       //Connect.SendServer(underConstruction(Q.command));
     }
    return answer;
   }
}
