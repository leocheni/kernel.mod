package net.msdh.kernel.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 23.05.17
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class NetServer {

    private ServerSocket sidServer;
    private Socket sidAccept;

    public NetServer() {

    }

    public NetServer(Socket accept) {
      this.sidAccept = accept;
    }

    public void Listen(int port) throws IOException {
      //sidServer = new ServerSocket(port, 0, InetAddress.getByName("localhost"));
      sidServer = new ServerSocket(port);
    }

    public Socket Accept() throws IOException {
      sidAccept = sidServer.accept();
      return sidAccept;
    }

    public String Read() throws IOException {
      InputStream is = sidAccept.getInputStream();
      byte[] buf = new byte[1];
      String cmd = "";
      while(true){
        int r = is.read(buf);
        if(r==0){
          break;
        }
        if(buf[0]=='#'){
          break;
        }
        cmd += new String(buf,0,1);
      }
      //return new String(buf, 0, r);
      return cmd;

//      BufferedReader br = new BufferedReader(new InputStreamReader(is));
//      while(true) {
//        String s = br.readLine();
//        if(s == null || s.trim().length() == 0) {
//          break;
//        }
//      }

    }

//    public String Read(Socket sidAccept) throws IOException {
//      InputStream is = sidAccept.getInputStream();
//      byte[] buf = new byte[1];
//      String cmd = "";
//      while(true){
//        int r = is.read(buf);
//        if(r==0){
//          break;
//        }
//        if(buf[0]=='#'){
//          break;
//        }
//        cmd += new String(buf,0,1);
//      }
//      //return new String(buf, 0, r);
//      return cmd;
//
////      BufferedReader br = new BufferedReader(new InputStreamReader(is));
////      while(true) {
////        String s = br.readLine();
////        if(s == null || s.trim().length() == 0) {
////          break;
////        }
////      }
//
//    }

    public void Send(String message) throws IOException {
      OutputStream os = sidAccept.getOutputStream();
      os.write((message+"#").getBytes());
    }

//    public void Send(Socket sidAccept, String message) throws IOException {
//      OutputStream os = sidAccept.getOutputStream();
//      os.write((message+"#").getBytes());
//    }

    public void Close() throws IOException {
      sidServer.close();
    }

    public void CloseAccept() throws IOException {
      sidAccept.close();
    }

//    public void CloseAccept(Socket sidAccept) throws IOException {
//      sidAccept.close();
//    }
}
