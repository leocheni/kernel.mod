package net.msdh.kernel.mail;

import net.minidev.json.parser.ParseException;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 05.06.17
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */
public class Main {
  public static void main(String[] args) {

    try {
      Log.getInstance().Open("F:\\dev\\projects\\lab\\dh\\kernel.mod\\out\\artifacts\\kernel\\log\\mail.log");
      //Log.getInstance().Open("C:\\projects\\dh\\kernel.mod\\out\\artifacts\\kernel\\log\\core.log");
      //Log.getInstance().Open("log/mail.log");

      Log.getInstance().setLevel("DEBUG");
      Log.getInstance().I("Mail.Main","====================mod mail:Start=====================");

        Display.getInstance().setLevel("DEBUG");
      //Log.getInstance().D("Mail.Main", args[1]);
     // System.out.println("args lenght" + args.length);
     // System.out.println("args" + args[1]);

   //   if (args.length == 2){
        //Settings.getInstance().Load(args[1]);
        Settings.getInstance().Load("F:\\dev\\projects\\lab\\dh\\kernel.mod\\out\\artifacts\\kernel\\conf\\mail.conf");
      //Settings.getInstance().Load("C:\\projects\\dh\\kernel.mod\\out\\artifacts\\kernel\\conf\\msdh.conf");
       // Settings.getInstance().Load("conf/mail.conf");


        Mail mMail = new Mail();
        mMail.Load();
        mMail.Manager();
        mMail.Unload();
//      }
//      else{
//        Log.getInstance().E("Mail.Main","Too few arguments");
//      }

      Log.getInstance().I("Mail.Main","=====================mod mail:Exit=====================");
      Log.getInstance().Close();

    }
    catch (ParseException e) {
      System.out.println("Mail.Main::Global Error parsing config: "+e.getMessage());
    }
    catch (IOException e) {
      System.out.println("Mail.Main::Global Error:"+e.getMessage());
    }

  }
}
