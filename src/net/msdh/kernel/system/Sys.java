package net.msdh.kernel.system;

import net.msdh.kernel.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public class Sys{


  public static void Shutdown() throws RuntimeException, IOException {
    String shutdownCommand;
    String operatingSystem = System.getProperty("os.name");
    System.out.println("os name: " + operatingSystem);
    if("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)){
      shutdownCommand = "shutdown -h now";
    }
    else if ("Windows".equals(operatingSystem)){
      shutdownCommand = "shutdown.exe -s -t 0";
    }
    else{
      throw new RuntimeException("Unsupported operating system.");
    }

    Runtime.getRuntime().exec(shutdownCommand);
    System.exit(0);
  }

  public static String getHostname(){
    String hostname = "Unknown";
    try{
      InetAddress addr;
      addr = InetAddress.getLocalHost();
      hostname = addr.getHostName();
    }
    catch (UnknownHostException ex){
       System.out.println("Hostname can not be resolved");
    }
    return hostname;
  }

//  public static boolean shutdown(int time) throws IOException {
//    String shutdownCommand = null, t = time == 0 ? "now" : String.valueOf(time);
//
//    if(SystemUtils.IS_OS_AIX)
//        shutdownCommand = "shutdown -Fh " + t;
//    else if(SystemUtils.IS_OS_FREE_BSD || SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC|| SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_NET_BSD || SystemUtils.IS_OS_OPEN_BSD || SystemUtils.IS_OS_UNIX)
//        shutdownCommand = "shutdown -h " + t;
//    else if(SystemUtils.IS_OS_HP_UX)
//        shutdownCommand = "shutdown -hy " + t;
//    else if(SystemUtils.IS_OS_IRIX)
//        shutdownCommand = "shutdown -y -g " + t;
//    else if(SystemUtils.IS_OS_SOLARIS || SystemUtils.IS_OS_SUN_OS)
//        shutdownCommand = "shutdown -y -i5 -g" + t;
//    else if(SystemUtils.IS_OS_WINDOWS_XP || SystemUtils.IS_OS_WINDOWS_VISTA || SystemUtils.IS_OS_WINDOWS_7)
//        shutdownCommand = "shutdown.exe -s -t " + t;
//    else
//        return false;
//
//    Runtime.getRuntime().exec(shutdownCommand);
//    return true;
//  }

  public static boolean exec(String cmd){

    ProcessBuilder builder = new ProcessBuilder(cmd);
    //Map<String, String> environ = builder.environment();
    final Process process;

    try {
      process = builder.start();

      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
      return true;
    }
    catch (IOException e) {
      Log.getInstance().E("SPORE","Error: "+e.getMessage());
      return false;
    }
  }
}
