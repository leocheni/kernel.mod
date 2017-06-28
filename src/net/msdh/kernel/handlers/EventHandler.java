package net.msdh.kernel.handlers;

import net.msdh.kernel.base.Command;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class EventHandler {
   public String Run(Command cmd){
//         try{
//     cout<<"CORE::RECV::Event: "<<c.getJson()<<endl;
//
//     ptree cmd;
//     string method;
//
//     try{
//       cmd = c.Params().get_child("command");
//       method = cmd.get<string>("method");
//     }
//     catch(const ptree_bad_path& bpe){
//       cout<<"CORE::EventHandler::Error: "<<bpe.what()<<endl;
//     }
//
//
//     if(Modules.GetMod("console").getStatus()=="up"){
//       ptree params,errors;
//       params.put<string>("message",method);
//       Connect.Send("127.0.0.1",Modules.GetMod("console").getPort(),Command(1,"notification",params).getJson());
//       Connect.CloseClient();
//     }
//   }
//   catch(CoreExeption& ce){
//     Log::getInstance().E("CORE::EventHandler",ce.GetMessage());
//   }
//   catch(ConnectionExeption& se){
//     Log::getInstance().E("CORE::EventHandler",se.getMessage());
//   }
     return "";
   }
}
