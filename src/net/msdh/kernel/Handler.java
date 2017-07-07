package net.msdh.kernel;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import net.msdh.kernel.base.Command;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.handlers.*;
import net.msdh.kernel.module.Mod;
import net.msdh.kernel.net.Connection;
import net.msdh.kernel.net.NetClient;
import net.msdh.kernel.net.NetServer;
import net.msdh.kernel.net.SmtpClient;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class Handler implements Runnable {

	private Connection cn;
    private Core core;
    private Command command;
   // private Socket accept;
    private NetServer netServer;
    private NetClient netClient;

    public Handler(Socket accept) {
      netServer = new NetServer(accept);
      netClient = new NetClient();
	}

    synchronized void setCore(Core c){
      this.core = c;
    }

//    synchronized void setConnection(Connection c) {
//		this.cn=c;
//	}

//    synchronized void setConnection(Socket accept) {
//		this.accept=accept;
//	}

    void setCommand(Command cmd){
      this.command = cmd;
    }

	public synchronized void run() {
      try {
        handleClient();
	  }
      catch(Exception e){
        System.out.println("Error: "+e.getMessage());
      }
      System.out.println("Command hendling");
    }

    void handleClient() throws IOException {
      String answer = null;
      System.out.println("ClientHandler start t id: "+ Thread.currentThread().getId() );

        try{
          if(command.getMethod().equals("system")){
            answer = new SysHandler().Run(command, core);
            //netServer.Send(underConstruction(command));
          }
          else if (command.getMethod().equals("mod")){
            answer = new ModHandler().Run(command,core);   ///todo реализовать обработку не отправки ответа модулям
              ///todo реализовать отправку уведомления на коноль, почту, sms
          }
          else if (command.getMethod().equals("dev")){
            answer = new DevHandler().Run(command, core);
          }
          else if(command.getMethod().equals("timer")){
            answer = new TmrHandler().Run(command, core);
          }
          else if(command.getMethod().equals("host")){
            answer = new HstHandler().Run(command, core);
          }
          else if(command.getMethod().equals("Rule")){
            answer = new RulHandler().Run(command);
          }
          else if(command.getMethod().equals("config")){
            answer = new ConHandler().Run(command);
          }
          else if(command.getMethod().equals("log")){
            answer = new LogHandler().Run(command);
          }
          else if (command.getMethod().equals("media")){
            answer = new MediaHandler().Run(command);
          }
          else if (command.getMethod().equals("user")){
            answer = new UsrHandler().Run(command,core);
          }
          else if (command.getMethod().equals("message")){
            answer = underConstruction(command);
          }
          else if (command.getMethod().equals("event")){
            answer = new EventHandler().Run(command);
          }

          if(answer != null){

            if(command.getId()!=1){
              try {
                Mod console = core.Modules.GetMod("console");
                if(console.getStatus().equals("up")){
                  netClient.Send(console.getAdress(), console.getPort(), answer);
                  netClient.Close();
                }
              }
              catch (CoreException e){
                Display.getInstance().E("Core.Handler","Error: " + e.getMessage());
                Log.getInstance().E("Core.Handler","Error: " + e.getMessage());
              }
            }
            else if(command.getId()==1){
              netServer.Send(answer);
            }
            else if(command.getId()==4){

              //netServer.Send(answer);
              SmtpClient smc = new SmtpClient("smtp.yandex.ru",465);
              smc.Connect("smtp.yandex.ru:465");
              smc.SendMessage("testup@yandex.ru",answer);

//              try {
//                Mod mail = core.Modules.GetMod("console");
//                if(mail.getStatus().equals("up")){
//                  netClient.Send(mail.getAdress(), mail.getPort(), answer);
//                  netClient.Close();
//                }
//              }
//              catch (CoreException e){
//                Display.getInstance().E("Core.Handler","Error: " + e.getMessage());
//                Log.getInstance().E("Core.Handler","Error: " + e.getMessage());
//              }
            }
          }
          else{
            netServer.Send(underConstruction(command));
          }
        }
        catch (IOException e) {
          Log.getInstance().E("CORE.Handler","Error: " + e.getMessage());
          Display.getInstance().E("CORE.Handler",e.getMessage());
        }
	    catch(Exception e){
          Log.getInstance().E("CORE.Handler","Error: " + e.getMessage());
          Display.getInstance().E("CORE.Handler",e.getMessage());
        }
        finally {
          netServer.CloseAccept();
        }
    }

    private String underConstruction(Command cmd){
      Map<String,Object> result = new HashMap<String,Object>();
      result.put("message", "command " + cmd.getMethod() + " underconstraction");
      JSONRPC2Response response = new JSONRPC2Response(result, cmd.getId());
      return  response.toJSONString();
    }

}
