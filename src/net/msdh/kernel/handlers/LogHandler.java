package net.msdh.kernel.handlers;

import net.msdh.kernel.base.Command;

public class LogHandler {
    public String Run(Command cmd){
      String name,action,item,value;
      ///todo реализовать обработчик лог файлов
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
