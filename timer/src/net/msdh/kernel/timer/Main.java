package net.msdh.kernel.timer;

import net.minidev.json.parser.ParseException;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.17
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */

public class Main {

    public static void main(String[] args) {
      try {
        Log.getInstance().Open("F:\\dev\\projects\\lab\\dh\\kernel.mod\\out\\artifacts\\kernel\\log\\timer.log");
        //Log.getInstance().Open("log/timer.log");
        Log.getInstance().setLevel("DEBUG");
        Log.getInstance().I("MAIN","====================HD:Start=====================");
        Display.getInstance().setLevel("DEBUG");
        //if(argc == 2){

          //Settings.getInstance().Load("conf/timer.conf");
          Settings.getInstance().Load("F:\\dev\\projects\\lab\\dh\\kernel.mod\\out\\artifacts\\kernel\\conf\\timer.conf");
          Timer timer = new Timer();
          timer.Load();
          timer.Manager();
          timer.Unload();
          Log.getInstance().I("MAIN","Timer exit");
       // }
       // else{
       //   Log.getInstance().F("MAIN","Too few argument");
       // }
        Log.getInstance().I("MAIN","=====================DH:Exit=====================");
        Log.getInstance().Close();
      }
      catch (ParseException e){
        System.out.println("Timer.Main::Global Error parsing config: " + e.getMessage());
      }
      catch (IOException e){
        System.out.println("Timer.Main::Global Error:" + e.getMessage());
      }
    }
}
