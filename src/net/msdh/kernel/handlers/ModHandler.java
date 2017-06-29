package net.msdh.kernel.handlers;

import net.msdh.kernel.Core;
import net.msdh.kernel.answer.Error;
import net.msdh.kernel.answer.Result;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.module.Modules;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class ModHandler {
    public String Run(Command cmd, Core core){
      System.out.println("ModHandler");
      String name = null,action = null,port,answer=null;
       int id = 0;
      Map<String,Object> result = new HashMap<String, Object>();
      Map<String,Object> error = new HashMap<String, Object>();

      try{
        action = cmd.getParams().get("action").toString();
        name = cmd.getParams().get("item").toString();
        id = cmd.getId();
      }

      catch (Exception e){
        Display.getInstance().E("MODHandler","error get param");
      }

      if(action!=null&&action.equals("load")){


        if(name==null){
          error.put("message","Mod name is null");
          answer = new Error(id,error).toJson();
        }
        else{
          try{
            core.Modules.Load(name);
            result.put("message","Module load");
            answer = new Result(id,result).toJson();
          }
          catch (CoreException e){
            error.put("code",e.getCode());
            error.put("source",e.getSource());
            error.put("message",e.getMessage());
            answer = new Error(id,error).toJson();
          }
        }
      }
      else if(action!=null&&action.equals("unload")){//ruchnaya vigruzka modulya
        try{
          core.Modules.Unload(name,false);
          result.put("message","Module unload");
          answer = new Result(id,result).toJson();
        }
        catch (CoreException e){
          error.put("code",e.getCode());
          error.put("source",e.getSource());
          error.put("message",e.getMessage());
          answer = new Error(id,error).toJson();
        }
      }
      else if(action!=null&&action.equals("start")){//modul startoval

        port = cmd.getParams().get("port").toString();
        try{
          core.Modules.Start(name,Integer.parseInt(port));
          result.put("message","Module "+name+" start");
          answer = new Result(id,result).toJson();
        }
        catch(CoreException e){

          error.put("code",e.getCode());
          error.put("source",e.getSource());
          error.put("message",e.getMessage());
          answer = new Error(id,error).toJson();

          Display.getInstance().E("CORE::ModHandler","Error: "+e.getMessage());
          Log.getInstance().E("CORE::ModHandler",e.getMessage());
        }
      }
      else if(action!=null&&action.equals("stop")){ //modul ostanovilsya
        try{
          core.Modules.Stop(name);
          result.put("message","Module "+name+" stop");
          answer = new Result(id,result).toJson();


//          if(name=="console"){
//            Connect.SendServer(AnswerResult(1,Message("Module "+name+" stop OK").get()).GetJson());
//          }
//          else{
//            if(Modules.GetMod("console").getStatus()=="up"){
//              Connect.Send("127.0.0.1",Modules.GetMod("console").getPort(),Command(1,"notification",Message("Module "+name+" stop OK").get()).getJson());
//              Connect.CloseClient();
//            }
//          }
      }
      catch(CoreException e){
        error.put("code",e.getCode());
        error.put("source",e.getSource());
        error.put("message",e.getMessage());
        answer = new Error(id,error).toJson();

        Display.getInstance().E("CORE::ModHandler","Error: "+e.getMessage());
        Log.getInstance().E("CORE::ModHandler",e.getMessage());
      }
    }
    else if(action!=null&&action.equals("comands")){
//      try{
//        Connect.SendServer(underConstruction(c));
//      }
//      catch(ConnectionExeption &se){
//        Display::getInstance().SetConsoleLine("Error: "+se.getMessage(),'e');
//        Log::getInstance().E("CORE::ModHandler",se.getMessage());
//      }
    }
    else if(action!=null&&action.equals("get")){ //polushenie informacii modulya

      String param = null;

      try{
        param = cmd.getParams().get("status").toString();
      }
      catch (Exception e){
        Display.getInstance().E("MODHandler","error get param");
      }

      if(param!=null&&param.equals("status")){
        try{
          answer = core.Modules.GetMod(name).getStatus();
        }
        catch(CoreException e){
          Display.getInstance().E("CORE::ModHandler","Error: "+e.getMessage());
          Log.getInstance().E("CORE::ModHandler",e.getMessage());
        }
      }
      else if(param!=null&&param.equals("")){


      }
    }
    else if(action!=null&&action.equals("set")){ //ustanovka informacii ot modulya v structuru yadra
      String param = null;
      String value = null;
      try{
        param = cmd.getParams().get("param").toString();
        value = cmd.getParams().get("value").toString();
      }
      catch (Exception e){
        Display.getInstance().E("MODHandler","error get param");
      }

      if(param!=null&&param.equals("status")){
        try{
          core.Modules.GetMod(name).setStatus(value);
        }
        catch(CoreException e){
          Display.getInstance().E("CORE::ModHandler","Error: "+e.getMessage());
          Log.getInstance().E("CORE::ModHandler",e.getMessage());
        }
      }
      else if(param!=null&&param.equals("")){


      }
    }
    else if(action!=null&&action.equals("show")){
     // try{
        answer = new Result(id,core.Modules.getModList()).toJson();

    //  }
    //  catch(CoreException e){
    //    Display.getInstance().E("CORE::ModHandler","Error: "+e.getMessage());
    //    Log.getInstance().E("CORE::ModHandler",e.getMessage());
    //  }
    }
    return answer;
   }
}
