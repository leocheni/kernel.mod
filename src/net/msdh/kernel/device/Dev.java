package net.msdh.kernel.device;

import net.minidev.json.JSONObject;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.ui.Display;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 26.05.17
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class Dev {
   private  String name;
   private  String stat;
   private  Boolean enable;

   public Dev() {
   }

   public Dev(String name, String stat, Boolean enable) {
     this.name = name;
     this.stat = stat;
     this.enable = enable;
   }

   public JSONObject toJson(){
     JSONObject tDev = new JSONObject();
     tDev.put("name",name);
     tDev.put("stat",stat);
     return tDev;
   }

   public void Send(String comm) throws CoreException {

       NetClient nc = new NetClient();

       Map<String,Object> params = new HashMap<String,Object>();
       params.put("name", name);
       params.put("stat", stat);
       Command cmd = new Command();
       cmd.setMethod("set");
       cmd.setParams(params);

       try {

         ///todo отправка сообщения в модуль раюоты с физическим устройством
         nc.Send("", 0, cmd.toJson());
         Display.getInstance().D("DEV","send command to dev");
       }
       catch (IOException e) {
         throw new CoreException(500,"DEV",e.getMessage());
       }

   }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
