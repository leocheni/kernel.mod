package net.msdh.kernel.hosts;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.system.Systems;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
public class Hosts {

  private Vector<Host> hosts;

  public Hosts() {
    hosts = new Vector<Host>(20);
  }

  public Host GetHost(String name) throws CoreException {
    Host tHost = null;

    for(Host hst:hosts){
      if(hst.getName().equals(name)){
         tHost = hst;
         break;
      }
    }

    if(tHost==null){
      throw new CoreException(500,"Host.GetHost","Host not found");
    }

    return tHost;
  }

  public void AddHost(Host host){
    if(!hosts.contains(host)){
      hosts.add(host);
    }
  }

//  public void Start(String name){
//    String wolPath = null;
//
//
//    JSONObject objDepen = (JSONObject)Settings.getInstance().Base.get("depend");
//    wolPath = (String)objDepen.get("wol");
//
//    if(wolPath!=null){
//      try {
//        Systems.exec("wol -i " + GetHost(name));
//      }
//      catch (IOException e) {
//        Log.getInstance().E("HOSTS.Start",e.getMessage());
//        Display.getInstance().E("HOSTS.Start",e.getMessage());
//      }
//      catch (InterruptedException e) {
//        Log.getInstance().E("HOSTS.Start",e.getMessage());
//        Display.getInstance().E("HOSTS.Start",e.getMessage());
//      }
//
//    }
//
//  }
//  void Shutdown(String name){
//
//
//  }
//  void Reboot(String name){
//
//
//  }
//
//  public void GetStatus(String name){
//
//
//  }

  public int GetHostCount(){
    return hosts.size();
  }

  public JSONArray GetHostList(){
    JSONArray hostsList = new JSONArray();

    for(Host host:hosts){
      hostsList.add(host.toJson());
    }
    return hostsList;
  }
}
