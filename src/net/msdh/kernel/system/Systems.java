package net.msdh.kernel.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 26.05.17
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
public class Systems {

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

    public static void run(String command) throws IOException, InterruptedException {
  //   StringBuilder output = new StringBuilder();
	 Process p = Runtime.getRuntime().exec(command);
//	 p.waitFor();
//	 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
//     String line = "";
//	 while ((line = reader.readLine())!= null) {
//       output.append(line).append("\n");
//	 }
     //return output.toString();
   }
}
