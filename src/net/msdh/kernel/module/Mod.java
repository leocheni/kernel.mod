package net.msdh.kernel.module;

import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.system.Systems;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Mod {
  private String name;
  private String status;
  private int priority;
  private int port;
  private int corePort;
  private String adress;
  private String run;
  private String bin;
  private String log;

  public Mod() {

  }

  public void load() throws CoreException {

  Log.getInstance().D("MODULE::MOD", "run: " + run + "/" + name);
  Log.getInstance().D("MODULE::MOD", "bin: " + bin + "/" + name);

  //String binFile = bin+"/"+name+".jar";
  String binFile = "F:/dev/projects/lab/dh/kernel.mod/out/artifacts/kernel/modules"+"/"+name+".jar";

  try{

    File file = new File(binFile);
    if(file.exists() && file.isFile()){
      //System.out.println("exec: " + Systems.exec("java -jar " + binFile)); ///todo сделать обработку ошибки запуска jar файла через создание lock файла
      //status = "up";
      Systems.eExec("java -jar " + binFile);
      Display.getInstance().E("Mod.Load", name + " mod start ok");
    }
    else {
      throw new CoreException(800,"Mod.Load","Mod bin file not found");
    }
  }
  catch(IOException e){
    Log.getInstance().E("Mod.Load",e.getMessage());
    Display.getInstance().E("Mod.Load",e.getMessage());
    throw new CoreException(800,"Mod.Load",e.getMessage());
  }
  catch (InterruptedException e) {
    Log.getInstance().E("Mod.Load",e.getMessage());
    Display.getInstance().E("Mod.Load",e.getMessage());
    throw new CoreException(800,"Mod.Load",e.getMessage());
  }
      ///todo реализовать загрузку jar файла в другом процессе JVM.
}

public void unload(boolean force) throws CoreException {
  int pidFile;

  if(status.equals("down")){
    Log.getInstance().E("MODULE::MOD","module down state");
    throw new CoreException(800,"MODULE::MOD"," module down state");
  }

  if(force){ ///todo принудительное завершение процесса??

//      pidFile = open(run.c_str(),O_CREAT|O_TRUNC|O_RDWR,0644);
//
//      if(pidFile<0){
//        status="down";
//        Log::getInstance().E("MODULE::MOD","can't open pid file");
//        throw(CoreExeption(800,"MODULE::MOD can't open pid file"));
//      }
//      if(read(pidFile,(char *)&pid,sizeof(pid_t))<0){
//        Log::getInstance().E("MODULE::MOD","can't read pid file");
//        throw(CoreExeption(800,"MODULE::MOD can't read pid file"));
//      }
//      if(!(pidFile<0)){
//        close(pidFile);
//      }
//
//      if(kill(pid,SIGINT)<0){
//        Log::getInstance().E("MODULE::MOD","can't kill process");
//        throw(CoreExeption(800,"MODULE::MOD can't kill process"));
//      };
//
//      if(unlink(run.c_str())<0){
//        Log::getInstance().E("MODULE::MOD","can't delete pid file");
//        throw(CoreExeption(800,"MODULE::MOD can't delete pid file"));
//      }

    }
    else{
      if(status.equals("up") && (!name.equals("core")) && (!name.equals("console"))){
        Map<String, Object> params = new HashMap<String, Object>();
        NetClient nc = new NetClient();
        Command c = new Command();
        c.setMethod("exit");
        params.put("delay",0);
        c.setParams(params);

        try {
          nc.Send(adress, port, c.toJson());
          nc.Close();
          Display.getInstance().D("MODULE::MOD","Send exit to mod");
        }
        catch (IOException e) {
          throw new CoreException(800,"MODULE::MOD", "Error send exit command for process"+e.getMessage());
        }
      }
    } //else force
}


public String getName(){
  return this.name;
}

public void setName(String name){
  this.name=name;
}

public int getPriority(){
   return priority;
}

public void setPriority(int Priority){
   this.priority= Priority;
}

public String getStatus(){
  return status;
}

public void setStatus(String status){
  this.status = status;
}

public String getBin(){
  return bin;
}

public void setBin(String bin){
  this.bin = bin;
}

public String getRun(){
  return run;
}

public void setRun(String run){
  this.run = run;
}

public String getLog(){
  return log;
}

public void setLog(String logDir){
  this.log = logDir;
}


public String getAdress(){
  return adress;
}

public void setAdress(String adress){
  this.adress = adress;
}

public int getPort(){
  return port;
}
public void setPort(int port){
  this.port=port;
}

public int getCorePort(){
  return corePort;
}

public void setCorePort(int corePort){
  this.corePort=corePort;
}
}
