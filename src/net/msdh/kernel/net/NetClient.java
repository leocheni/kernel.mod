package net.msdh.kernel.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 23.05.17
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class NetClient {
    private Socket sidClient;

    public NetClient() {

    }

     public void Open(String ip,int port) throws IOException {
      sidClient = new Socket(ip,port);
    }

    public void Send(String ip,int port,String message) throws IOException {
      Open(ip,port);
      Send(message);
    }

    public void Send(String message) throws IOException {
      sidClient.getOutputStream().write((message+"#").getBytes());
    }

    public String Read() throws IOException {

      byte[] buf = new byte[1];
      String cmd = "";
      while(true){
        int r = sidClient.getInputStream().read(buf);
        if(r==0){
          break;
        }
        if(r==-1){
          throw (new IOException("Recive empty line"));

        }
        if(buf[0]=='#'){
          break;
        }
        cmd += new String(buf,0,1);

      }
      return cmd;

//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        while(true) {
//          String s = br.readLine();
//          if(s == null || s.trim().length() == 0) {
//            break;
//          }
//        }

    }


    public void Close() throws IOException {
      sidClient.close();
    }

}
