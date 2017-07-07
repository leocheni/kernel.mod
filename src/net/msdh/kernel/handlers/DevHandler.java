package net.msdh.kernel.handlers;

import net.msdh.kernel.Core;
import net.msdh.kernel.answer.*;
import net.msdh.kernel.answer.Error;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.ui.Display;

import java.util.HashMap;
import java.util.Map;

public class DevHandler {

   public String Run(Command cmd,Core core) {
     Display.getInstance().I("Dev Handler"," entry");
     String item = null, action = null, answer = null;
     int id = 0;

     Map<String,Object> result = new HashMap<String, Object>();
     Map<String,Object> error = new HashMap<String, Object>();
     try{
       item = cmd.getParams().get("item").toString();
       action = cmd.getParams().get("action").toString();
       id = cmd.getId();
     }
     catch (Exception e){
       Display.getInstance().E("MODHandler","Error get param");
     }

     if(action!=null&&action.equals("uncnoun")){
       //Dev dv = Devices.GetDev(item);
       //dv.Enable(true);
     }
     else if(action!=null&&action.equals("enable")){
       try{
         core.Devices.GetDev(item).setEnable(true);
         result.put("message","Dev enable set");
         answer=new Result(id,result).toJson();
       }
       catch (CoreException e) {
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null&&action.equals("disable")){
       try {
         core.Devices.GetDev(item).setEnable(false);
         result.put("message","Dev enable unset");
         answer=new Result(id,result).toJson();
       }
       catch(CoreException e){
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null&&action.equals("add")){
   	    //Connect.SendServer(underConstruction(c));
     }
     else if(action!=null&&action.equals("del")){
       try{
         core.Devices.RemDev(item);
         result.put("message","Dev removed");
         answer=new Result(id,result).toJson();

       }
       catch (CoreException e) {
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null&&action.equals("stat")){

     }
     else if(action!=null&&action.equals("on")){
       try{
         core.Devices.GetDev(item).setStat("on");
         result.put("message","Dev set stat on");
         answer=new Result(id,result).toJson();
       }
       catch (CoreException e) {
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null&&action.equals("off")){
       try{
         core.Devices.GetDev(item).setStat("off");
         result.put("message","Dev set stat off");
         answer=new Result(id,result).toJson();
       }
       catch (CoreException e) {
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null&&action.equals("send")){
       try {
         core.Devices.GetDev(item).Send(item);
         result.put("message","Dev command sended");
         answer=new Result(id,result).toJson();
       }
       catch (CoreException e) {
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new Error(id,error).toJson();
       }
     }
     else if(action!=null&&action.equals("show")){

       if(item.equals("")){
         answer=new Result(id,core.Devices.GetDevList()).toJson();
       }
       else{
         try {
           answer=new Result(id,core.Devices.GetDev(item).toJson()).toJson();
         }
         catch (CoreException e) {
           error.put("cod",e.getCode());
           error.put("source",e.getSource());
           error.put("message",e.getMessage());
           answer = new Error(id,error).toJson();
         }
       }
     }
     return answer;
   }
}
