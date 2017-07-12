package net.msdh.kernel;

import net.minidev.json.parser.ParseException;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;

public class Kernel {

  public static void main(String[] args){

    try{
      //System.out.println(Log.getInstance().getClass().getResource("/"));
      Log.getInstance().Open("F:\\dev\\projects\\lab\\dh\\kernel.mod\\out\\artifacts\\kernel\\log\\core.log");
      //Log.getInstance().Open("C:\\projects\\dh\\kernel.mod\\out\\artifacts\\kernel\\log\\core.log");
      //Log.getInstance().Open("../log/core.log");
     }
    catch (IOException e){
      System.out.println("Error loading log file: " + e.getMessage());
      return;
    }

    Log.getInstance().setLevel("DEBUG");
    Log.getInstance().I("MAIN","=============MSDH START============");

    try{
      Settings.getInstance().Load("F:\\dev\\projects\\lab\\dh\\kernel.mod\\out\\artifacts\\kernel\\conf\\msdh.conf");
      //Settings.getInstance().Load("C:\\projects\\dh\\kernel.mod\\out\\artifacts\\kernel\\conf\\msdh.conf");
      //Settings.getInstance().Load("../conf/msdh.conf");
    }
    catch (IOException e){
      System.out.println("Error load config: " + e.getMessage());
    }
    catch (ParseException e){
      System.out.println("Error load config: " + e.getMessage());
    }

    Display.getInstance().setLevel("DEBUG");

    Core c = new Core();
    try{
      c.Load();
      c.Manager();
      c.Unload();
    }
    catch (CoreException e) {
      Display.getInstance().F("MAIN",e.getSource() + " " + e.getMessage());
    }

    try{
      Log.getInstance().I("MAIN","============MSDH STOP===========");
      Log.getInstance().Close();
    }
    catch (IOException e) {
      System.out.println("Error close log: " + e.getMessage());
    }
  }
}
