package net.msdh.kernel.handlers;

import net.msdh.kernel.base.Command;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class LogHandler {
    public String Run(Command cmd){
      String name,action,item,value;

      action = cmd.getParams().get("action").toString();
      name = cmd.getParams().get("item").toString();

      if(action.equals("get")){

      }
      else if(action.equals("set")){

      }
      else if(action.equals("list")){

      }
      return "";
    }
}
