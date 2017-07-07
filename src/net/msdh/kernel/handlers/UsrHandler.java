package net.msdh.kernel.handlers;

import net.msdh.kernel.Core;
import net.msdh.kernel.answer.*;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.security.User;
import net.msdh.kernel.ui.Display;

import java.util.HashMap;
import java.util.Map;

public class UsrHandler {
   public String Run(Command cmd, Core core){
     String name = null,action = null,password = null,login = null, answer = null;

     Map<String,Object> result = new HashMap<String, Object>();
     Map<String,Object> error = new HashMap<String, Object>();

     try{
       action = cmd.getParams().get("action").toString();
     }
     catch (Exception e){
       Display.getInstance().E("UsrHandler","Error get param action");
     }
     try{
       name = cmd.getParams().get("name").toString();
     }
     catch (Exception e){
       Display.getInstance().E("UsrHandler","Error get param name");
     }
     try{
       login = cmd.getParams().get("item").toString();
     }
     catch (Exception e){
       Display.getInstance().E("UsrHandler","Error get param item");
     }
     try{
       password = cmd.getParams().get("add").toString();
     }
     catch (Exception e){
       Display.getInstance().E("UsrHandler","Error get param add");
     }
     int id = cmd.getId();

     Display.getInstance().D("UsrHandler","action: "+action+"\r\n"+"name: "+name+"\r\n"+"password: "+password+"\r\n"+"fullname: "+login);

     if(action.equals("add")){
       User usr = new User(name,password,login);
       try{
         core.Session.AddUser(usr);
         result.put("message","User was added");
         answer = new Result(id,result).toJson();
       }
       catch (CoreException e) {
         error.put("cod",e.getCode());
         error.put("source",e.getSource());
         error.put("message",e.getMessage());
         answer = new net.msdh.kernel.answer.Error(id,error).toJson();
       }
     }
     else if(action.equals("del")){

     }
     else if(action.equals("show")){
       answer= new Result(id,core.Session.GetUserList()).toJson();
     }
     else if (action.equals("login")){
       if(core.Session.GetUser(login).Auth(password)){
         result.put("auth",1);
       }
       else{
         result.put("auth",0);
       }
       answer = new Result(id,result).toJson();
     }
     else if(action.equals("logout")){
       core.Session.GetUser(login).Exit();
       result.put("message","User exit");
       answer = new Result(id,result).toJson();
     }
     return answer;
   }
}
