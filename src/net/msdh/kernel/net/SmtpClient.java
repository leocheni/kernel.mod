package net.msdh.kernel.net;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 05.06.17
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public class SmtpClient {

 private String mailhost;
 private int port;
 private Session session;
 private String from = "base.msdh@yandex.ru";

public SmtpClient(){
  this.session = null;

}

public SmtpClient(String mailhost, int port) {
        this.mailhost = mailhost;
        this.port = port;
        this.session = null;
}

public boolean Connect(String connectString){

      Properties properties = new Properties();
      properties.put("mail.smtp.host", mailhost);
      properties.put("mail.smtp.port", port);
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

      Session session = Session.getInstance(properties, null);
      session.setDebug(false);

       return true;
}


public boolean SendMessage(String to, String message){
  try{
	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(from));
	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	msg.setSubject("MSDH message");

      //	    if (file != null) {
//		// Attach the specified file.
//		// We need a multipart message to hold the attachment.
//		MimeBodyPart mbp1 = new MimeBodyPart();
//		mbp1.setText(text);
//		MimeBodyPart mbp2 = new MimeBodyPart();
//		mbp2.attachFile(file);
//		MimeMultipart mp = new MimeMultipart();
//		mp.addBodyPart(mbp1);
//		mp.addBodyPart(mbp2);
//		msg.setContent(mp);
//	    } else {
//		// If the desired charset is known, you can use
		// setText(text, charset)
	msg.setText(message);
	//    }
    msg.setHeader("X-Mailer", "msgsend");
    msg.setSentDate(new Date());

	Transport.send(msg);
    System.out.println("\nMail was sent successfully.");
  }
  catch(MessagingException e){
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
  }


//  sendSSL("EHLO smtp.mail.ru\r\n");
//  cout<<"ans helo: "<<getLine()<<endl;
//  cout<<"ans helo: "<<getLine()<<endl;
//  cout<<"ans helo: "<<getLine()<<endl;
//  cout<<"ans helo: "<<getLine()<<endl;
//  cout<<"ans helo: "<<getLine()<<endl;
//  cout<<"ans helo: "<<getLine()<<endl;
//  cout<<"ans helo: "<<getLine()<<endl;
//
//  sendSSL("AUTH LOGIN\r\n");
//  cout<<"ans auth: "<<getLine()<<endl;
//  string login = "base.msdh@yandex.ru";
//  string pass = "mamontezmamontyan";
//  sendSSL(Format::base64_encode(reinterpret_cast<const unsigned char*>(login.c_str()), login.length())+"\r\n");
//  cout<<"ans login: "<<getLine()<<endl;
//  sendSSL(Format::base64_encode(reinterpret_cast<const unsigned char*>(pass.c_str()), pass.length())+"\r\n");
//  cout<<"ans pass: "<<getLine()<<endl;
//
//sendSSL("MAIL FROM: base.msdh@yandex.ru\r\n");
//  cout<<"ans mail from: "<<getLine()<<endl;
//
//sendSSL("RCPT TO: testup@yandex.ru\r\n");
//
//  cout<<"ans RCPT TO: "<<getLine()<<endl;
//sendSSL("DATA\r\n");
//  cout<<"ans DATA: "<<getLine()<<endl;
//
//sendSSL("FROM: base.msdh@yandex.ru\r\n");
//sendSSL("TO: "+to+"\r\n");
//sendSSL("SUBJECT: MSDH_test\r\n");
//
//sendSSL("\r\n" + message + "\r\n");
//sendSSL("\r\n.\r\n");
// cout<<"mail message: "<<getLine()<<endl;
//
//sendSSL("QUIT\r\n");
// cout<<"ans quit: "<<getLine()<<endl;

  return true;
}

public void close (){
  //session.

}

/*string SmtpClient::getLine(){

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


}
