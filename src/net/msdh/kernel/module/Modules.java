package net.msdh.kernel.module;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.msdh.kernel.errors.CoreException;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class Modules {

    public Vector<Mod> mods;

    public Modules(){
      mods = new Vector<Mod>(20);
    }

public void Load() throws CoreException{
  for(Mod mod:mods){
    if(mod.getStatus().equals("down")){
      mod.load();
    }
  }
}

public void Load(String name)throws CoreException{
  int modIndex = getModIndex(name);
  if(modIndex!=-1){
    mods.get(modIndex).load();
  }
  else{
    throw new CoreException(202,"Module::Load"," - Module isn't found");
  }
}

public void Unload(boolean force)throws CoreException{

  try{
    for(Mod mod:mods){
      if(mod.getStatus().equals("up")){
        mod.unload(force);
      }
    }
  }
  catch(CoreException e){
    throw new CoreException(201,e.getSource(), e.getMessage());
  }
}

public void Unload(String name, boolean force)throws CoreException{
  int modIndex = getModIndex(name);
  if(modIndex!=-1){
    mods.get(modIndex).unload(false);
  }
  else{
    throw new CoreException(203,"Module::Unload"," - Module isn't found");
  }
}

public void Start(String  name, int port) throws CoreException {
  if(getModIndex(name)==-1){
    Mod mod = new Mod();
    mod.setName(name);
    mod.setStatus("up");
    mod.setPort(port);
    mods.add(mod);
  }
  else{
    setStatus(name,"up");
    setPort(name,port);
  }
}

public void Stop(String name) throws CoreException {
    setStatus(name,"down");
}

public Mod GetMod(String name)throws CoreException{

  int modIndex = getModIndex(name);
  if(modIndex!=-1){
   //md=mods[modIndex];
    return mods.get(modIndex);
  }
  else{
    throw new CoreException(204,"Module::GetModule"," - Module isn't found");
  }

}

public int GetModsCount(){
  return mods.size();
}

public void AddModule(Mod md)throws CoreException {
  if(getModIndex(md.getName())==-1){
    mods.add(md);
  }
  else{
    throw new CoreException(205,"Module::addModule"," - Module already exists");
  }
}

public void remModule(String name)throws CoreException{
  int modIndex = getModIndex(name);
  if(modIndex != -1){
    mods.removeElementAt(modIndex);
  }
  else{
    throw new CoreException(206,"Module::remModule"," - Module isn't found");
  }
}

public void setStatus(String name, String status) throws CoreException {

  int modIndex = getModIndex(name);
  if(modIndex!=-1){
    mods.get(modIndex).setStatus(status);
  }
  else{
    throw new CoreException(207,"Module::setStatus"," - Module isn't found");
  }
}

public void setPort(String name, int port) throws CoreException {

  int modIndex = getModIndex(name);
  if(modIndex!=-1){
    mods.get(modIndex).setPort(port);
  }
  else{
    throw new CoreException(208,"Module::setStatus"," - Module isn't found");
  }
}

  public int getModIndex(String name){
    int i=0;
    int j=-1;
    for(Mod m:mods){
      if(m.getName().equals(name)){
        j=i;
        break;
      }
      i++;
    }
    return j;
  }

public JSONArray getModList(){

  JSONArray modList = new JSONArray();

    for (Mod mod : mods) {
        JSONObject oMod = new JSONObject();
        oMod.put("name", mod.getName());
        oMod.put("status", mod.getStatus());
        oMod.put("adress", mod.getAdress());
        oMod.put("port", mod.getPort());
        oMod.put("run", mod.getRun());
        oMod.put("bin", mod.getBin());
        oMod.put("log", mod.getLog());
        oMod.put("priority", mod.getPriority());
        oMod.put("corePort", mod.getCorePort());
        modList.add(oMod);
    }

  return modList;
}



/*void Module::updModule(std::string nameMod, std::string status)throw(CoreExeption) {
  int position = getModIndex(nameMod);
  if(position!=0){
    mods[position].setStatus(status);
  }
}*/



}
