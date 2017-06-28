package net.msdh.kernel.device;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.errors.CoreException;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public class Devices {
  private Vector<Dev> devs;

  public Devices() {
  }

  public Dev GetDev(String name)throws CoreException{

  int devIndex = getDevIndex(name);
  if(devIndex!=-1){
    return devs.get(devIndex);
  }
  else{
    throw new CoreException(204,"Device.GetDev"," - Dev isn't found");
  }
}

public int GetDevsCount(){
  return devs.size();
}

public int getDevIndex(String name){
  int src = -1;
  int i=0;
  for(Dev d:devs){
    if(d.getName().equals(name)){
      src = i;
      break;
    }
    i++;
  }
  return src;
}

public void AddDev(Dev dv)throws CoreException{

  if(devs.contains(dv)){
    devs.add(dv);
  }
  else{
    throw new CoreException(205,"Device.addDev"," - Device already exists");
  }

}

public void RemDev(String name)throws CoreException{
  int devIndex = getDevIndex(name);
  if(devIndex != -1){
    devs.remove(devIndex);
  }
  else{
    throw new CoreException(206,"Device.remDev"," - Device isn't found");
  }
}

public JSONArray GetDevList(){
  JSONArray devList = new JSONArray();
  for(Dev dev:devs){
    JSONObject tDev = new JSONObject();
    tDev.put("name",dev.getName());
    tDev.put("stat",dev.getStat());
    tDev.put("enable",dev.getEnable());
    devList.add(tDev);
  }
  return devList;
}

}
