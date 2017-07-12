package net.msdh.kernel.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class
   Systems {

   public static String exec(String command) throws IOException, InterruptedException {
     StringBuilder output = new StringBuilder();
	 Process p;

	 p = Runtime.getRuntime().exec(command);
	 p.waitFor();

	 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
     String line = "";
	 while ((line = reader.readLine())!= null) {
       output.append(line).append("\n");
	 }

     return output.toString();
   }

   public static void eExec(String command) throws IOException, InterruptedException {
     //StringBuilder output = new StringBuilder();
	 Process p = Runtime.getRuntime().exec(command);
     //p.getOutputStream();

     //p.exitValue()

//	 p.waitFor();
//	 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//     String line = "";
//	 while ((line = reader.readLine())!= null) {
//       output.append(line).append("\n");
//	 }

     //return output.toString();
   }

//    public static void run(String command) throws IOException, InterruptedException {
//  //   StringBuilder output = new StringBuilder();
//	 //Process p = Runtime.getRuntime().exec(command);
////	 p.waitFor();
////	 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
////     String line = "";
////	 while ((line = reader.readLine())!= null) {
////       output.append(line).append("\n");
////	 }
//     //return output.toString();
//
//
//     ProcessBuilder builder = new ProcessBuilder(command);
//    //Map<String, String> environ = builder.environment();
//      final Process process;
//
//  //  try {
//      process = builder.start();
//
//      //System.out.println("Process: " + process.);
//
//      InputStream is = process.getInputStream();
//      InputStreamReader isr = new InputStreamReader(is);
//      BufferedReader br = new BufferedReader(isr);
//      String line;
//      while ((line = br.readLine()) != null) {
//        System.out.println("Command answer: " + line);
//      }
//      //return true;
////    }
////    catch (IOException e) {
////
////      Log.getInstance().E("System.run","Error: "+e.getMessage());
////      throw new CoreException(800,"System.run",e.getMessage());
////      //return false;
////    }
//   }
}
