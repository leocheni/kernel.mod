package net.msdh.kernel.net;

import net.minidev.json.JSONObject;
import net.msdh.kernel.errors.CoreException;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 05.06.17
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class ImapClient {

  final private String TAG = "ImapClient";
  private String server;
  private String port;
  final private String protocol = "imap";

  private Folder folder;
  private Store store;

    public ImapClient() {
      this.store = null;
      this.folder = null;
    }


    public ImapClient(String server, String port) {
      this.server = server;
      this.port = port;
      this.store = null;
      this.folder = null;
    }

    public boolean Connect(String email, String password) throws CoreException {
    Properties properties = new Properties();
    properties.put("mail.debug"          , "false"  );
    properties.put("mail.store.protocol" , "imaps"  );
    properties.put("mail.imap.ssl.enable", "true"   );
    properties.put("mail.imap.port"      , port);

    //Authenticator auth = new EmailAuthenticator(IMAP_AUTH_EMAIL, IMAP_AUTH_PWD);
    Session session = Session.getDefaultInstance(properties, null);
    session.setDebug(false);

    try {
      store = session.getStore(protocol);
      store.connect(server, email, password);
    }
    catch (NoSuchProviderException e) {
      Log.getInstance().D(TAG+".Connect","Error: " + e.getMessage());
      throw new CoreException(900,TAG+".Connect",e.getMessage());
    }
    catch (MessagingException e) {
      Log.getInstance().D(TAG+".Connect","Error: " + e.getMessage());
      throw new CoreException(900,TAG+".Connect",e.getMessage());
    }

  return true;
}

public String ListBox(){

  String result="";
//  sendSSL(". LIST \"\" \"*\"\r\n");
//  result=readSSL();
////  cout<<"list ans: "<<result<<endl;
//  Log::getInstance().D("MC:ListBox",result);

  return result;
}

//public int StatusBox(String box){
//  int messageCount = 0;
//  if(isConnected){
//    sendSSL(". STATUS "+box+" (messages)\r\n");
//    string result = readSSL();
//    size_t pos = result.find("MESSAGES");
//    if(pos!= string::npos){
//      messageCount = Format::StringToInt(result.substr(pos+8,pos+9));
////      cout<<"message cont: "<<messageCount<<endl;
////      cout<<"pos: "<<pos<<endl;
//    }
//    Log::getInstance().D("MC:StatusBox",result);
////    cout<<"status ans: "<<result<<endl;
//  }
//  return messageCount;
//}

public boolean SelectMailBox(String box) throws CoreException {
  if(store.isConnected()){
    try{
      folder = store.getFolder(box);
      folder.open(Folder.READ_WRITE);
      Log.getInstance().D("MC:SelectMailBox","Open folder: " + folder.getName());
    }
    catch (MessagingException e) {
      Log.getInstance().E(TAG+".SelectMailBox",e.getMessage());
      throw new CoreException(900,TAG+".SelectMailBox",e.getMessage());
    }
  }
  return true;
}

public JSONObject GetMessage(String id) throws CoreException {
  JSONObject result = null;
  if(folder!=null&&folder.isOpen()){
      try {
        Log.getInstance().D("MC:GetMessage","Количество сообщений : " + String.valueOf(folder.getMessageCount()));

        if (folder.getMessageCount() != 0){
          result = new JSONObject();
            // Последнее сообщение; первое сообщение под номером 1
          Message message = folder.getMessage(folder.getMessageCount());
          Multipart mp = (Multipart) message.getContent();

          result.put("Subject",message.getSubject());
          result.put("Date",message.getSentDate().toString());

            // Вывод содержимого в консоль
          for(int i = 0; i < mp.getCount(); i++){
            BodyPart bp = mp.getBodyPart(i);
            if(bp.getFileName() == null){
              result.put("Text",bp.getContent());
              System.out.println("    " + i + ". сообщение : '" + bp.getContent() + "'");
            }
            else{
              System.out.println("    " + i + ". файл : '" + bp.getFileName() + "'");
            }
          }
        }
      }
      catch (MessagingException e) {
        Log.getInstance().E(TAG+".GetMessage",e.getMessage());
        throw new CoreException(900,TAG+".SelectMailBox",e.getMessage());
      }
      catch (IOException e) {
        Log.getInstance().E(TAG+".GetMessage",e.getMessage());
        throw new CoreException(900,TAG+".SelectMailBox",e.getMessage());
      }
  }
  else{
    Log.getInstance().E("MC:GetMessage", "Folder not open");
    Display.getInstance().E("MC:GetMessage", "Folder not open");
  }
  return result;
}

public boolean DeleteMessage(int[] id) throws CoreException {
  if(folder!=null && folder.isOpen()){
    try{
      folder.setFlags(id,new Flags(Flags.Flag.DELETED),true);
    }
    catch (MessagingException e) {
      Log.getInstance().E(TAG+".DelMessage",e.getMessage());
      throw new CoreException(900,TAG+".DelMessage",e.getMessage());
    }
  }
  else{
    Log.getInstance().E(TAG+".DelMessage","Folder not open");
    throw new CoreException(900,TAG+".DelMessage","Folder not open");
  }
  return true;
}

public boolean CloseBox() throws CoreException {

  if(folder!=null&&folder.isOpen()){
    try{
      folder.close(true);
    }
    catch (MessagingException e) {
      Log.getInstance().E(TAG+".CloseBox",e.getMessage());
      throw new CoreException(900,TAG+".CloseBox",e.getMessage());
    }
  }
  else{
    Log.getInstance().E(TAG+".CloseBox","Folder not open");
    throw new CoreException(900,TAG+".CloseBox","Folder not open");
  }
  return true;
}

public boolean Logout() throws CoreException {
  if(store!=null&&store.isConnected()){
    try{
      store.close();
    }
    catch (MessagingException e) {
      Log.getInstance().E(TAG+".Logout",e.getMessage());
      throw new CoreException(900,TAG+".Logout",e.getMessage());
    }
  }
  else {
    Log.getInstance().E(TAG+".Logout", "store not connected");
    throw new CoreException(900,TAG+".Logout","store not connected");
  }
  return true;
}

/*string ImapClient::getLine(){

  //Log::getInstance().D("MC:GetLine","start");

  string result;
  bool re = false;
  int p;
  char r;
  if(isConnected){

    for(;;){
      p = BIO_read(bio, &r, 1);
      if(p<=0){
        Log::getInstance().D("MC:GetLine","p=0");
        result = "";
        break;
      }
      if(r=='\r'){
        re=true;
        continue;
      }

      if(r=='\n'&&re){
          break;
      }
      else{
        if(r!='\n'){
           result+=r;
        }
      }
    }
  }
  Log::getInstance().D("MC:GetLine",result);
//  Log::getInstance().D("MC:GetLine","stop");
  return result;
}
*/

//public String parser(String source, String start, String end){
//      string result;
//      size_t posS = source.find(start);
//      if(posS!= string::npos){
//        result=source.substr(posS+start.size());
////        cout<<"r1: "<<result<<" s "<<posS<<endl;
//      }
//
//      size_t posF = result.find(end);
//      if(posF!= string::npos){
//        result=result.substr(0,posF);
////        cout<<"r2: "<<result<<" f "<<posF<<endl;
//      }
//  return result;
//}




}
