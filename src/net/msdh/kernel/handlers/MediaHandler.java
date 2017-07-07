package net.msdh.kernel.handlers;

import net.msdh.kernel.base.Command;


public class MediaHandler {
   public String Run(Command cmd){
//      ptree result, params;
//
//  try{
//
//    if(Modules.GetMod("kodi").getStatus()=="up"){
//
//      string action;
//      string kodiAdress = Modules.GetMod("kodi").getAdress();
//      int kodiPort = Modules.GetMod("kodi").getPort();
//
//      Command cmd;
//      cmd.Id(7);
//      try{
//        action = c.Params().get<string>("action");
//      }
//      catch(const ptree_bad_path& bpe){
//        Log::getInstance().E("CORE:MediaHandler",bpe.what());
//      }
//
//      if(action=="message"){
//
//        string text;
//        try{
//          text = c.Params().get<string>("text");
//        }
//        catch(const ptree_bad_path& bpe){
//          Log::getInstance().E("CORE:MediaHandler",bpe.what());
//        }
//
//        params.put<string>("action","message");
//        params.put<string>("text",text);
//
//        cmd.Method("set");
//        cmd.Params(params);
//      }
//      else if(action=="sources"){
//
//        params.put<string>("action","sources");
//        cmd.Method("get");
//        cmd.Params(params);
//
//      }
//      else if(action=="list"){
//
//        string directory;
//        try{
//          directory = c.Params().get<string>("item");
//        }
//        catch(const ptree_bad_path& bpe){
//          cout<<"Error: "<<bpe.what()<<endl;
//        }
//        params.put<string>("action","list");
//        params.put<string>("directory",directory);
//        cmd.Method("get");
//        cmd.Params(params);
//      }
//      else if(action=="play"){
//
//        string item;
//        try{
//          item = c.Params().get<string>("item");
//          params.put<string>("action",item);
//        }
//        catch(const ptree_bad_path& bpe){
//          cout<<"CORE Error: "<<bpe.what()<<endl;
//          params.put<string>("action","null");
//        }
//        cmd.Method("play");
//        cmd.Params(params);
//      }
//      else if (action=="pause"){
//        params.put("playerid",1);
//        cmd.Method("pause");
//        cmd.Params(params);
//      }
//      else if (action=="stop"){
//        params.put("playerid",1);
//        cmd.Method("stop");
//        cmd.Params(params);
//      }
//      else if(action=="exit"){
//
//      }
//      Connect.Send(kodiAdress,kodiPort,cmd.getJson());
//      //result.push_back(make_pair("",Json::Read(Connect.ReadClient())));
//
//      string ans = Connect.ReadClient();
//
//      cout<<"ans: "<<ans<<endl;
//      Connect.SendServer(AnswerResult(1,Json::Read(ans)).GetJson());
//      //Connect.SendServer(Ansver(1,errors,Json::Read(Connect.ReadClient())).GetJson());
//      //Connect.SendServer(Ansver(1,errors,result).GetJson());
//      Connect.CloseClient();
//    }
//    else{
//      //ptree errors;
//      //errors.put<int>("code",3000);
//      //errors.put<string>("message","Kodi mod is down status");
//      Connect.SendServer(AnswerError(1,Error(3000,"Kodi mod is down status").get()).GetJson());
//    }
//  }
//  catch(CoreExeption ce){
//    Log::getInstance().E("CORE::MediaHandler",ce.GetMessage());
// //   ptree errors;
// //   errors.put<int>("code",4000);
// //   errors.put<string>("message","Kodi mod is down: "+ce.GetMessage());
//    Connect.SendServer(AnswerError(1,Error(4000,"Kodi mod is down: "+ce.GetMessage()).get()).GetJson());
//  }
//  catch(ConnectionExeption se){
//    Log::getInstance().E("CORE::MediaHandler",se.getMessage());
//   // ptree errors;
//   // errors.put<int>("code",5000);
//   // errors.put<string>("message","Kodi mod is down: "+se.getMessage());
//    Connect.SendServer(AnswerError(1,Error(5000,"Kodi mod is down: "+se.getMessage()).get()).GetJson());
//  }

    return "";
   }
}
