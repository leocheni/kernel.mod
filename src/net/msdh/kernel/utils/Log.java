package net.msdh.kernel.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log {
  private static volatile Log instance;
  private String level;
  private BufferedWriter logFile = null;
  private String fname;
  private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

  public static Log getInstance() {
	Log localInstance = instance;

	if(localInstance == null){
	  synchronized (Log.class){
	    localInstance = instance;

		if(localInstance == null) {
		  instance = localInstance = new Log();
		}
	  }
	}
	return localInstance;
  }

  public boolean Open(String fname) throws IOException {
    logFile = new BufferedWriter(new FileWriter(fname));
    return true;
  }

  public void setLevel(String lv){
    level=lv;
  }

  public void D(String source, String message){
    if(level.equals("DEBUG")){
        try {
            logFile.write(dateFormat.format(new Date())+" DEBUG::"+source+"::"+message+"\r\n");
            logFile.flush();
        }
        catch (IOException e) {
            System.out.println("LOG::Error: " + e.getMessage());
        }
    }
  }

  public void I(String source, String message){

    if(level.equals("DEBUG") || level.equals("INFO")){
        try {
            logFile.write(dateFormat.format(new Date())+" INFO::"+source+"::"+message+"\r\n");
            logFile.flush();
        }
        catch (IOException e) {
            System.out.println("LOG::Error: " + e.getMessage());
        }
    }
  }

  public void W(String source, String message){
    if(level.equals("DEBUG") || level.equals("INFO") || level.equals("WARN")){
        try {
            logFile.write(dateFormat.format(new Date())+" WARN::"+source+"::"+message+"\r\n");
            logFile.flush();
        }
        catch (IOException e) {
            System.out.println("LOG::Error: " + e.getMessage());
        }
    }
  }

  public void E(String source, String message){
    if(level.equals("DEBUG") || level.equals("INFO") || level.equals("WARN") || level.equals("ERROR")){
        try {
            logFile.write(dateFormat.format(new Date())+" ERROR::"+source+"::"+message+"\r\n");
            logFile.flush();
        }
        catch (IOException e) {
            System.out.println("LOG::Error: " + e.getMessage());
        }
    }
  }

  public void F(String source, String message){
      try{
        logFile.write(dateFormat.format(new Date())+" FATAL::"+source+"::"+message+"\r\n");
        logFile.flush();
      }
      catch (IOException e) {
          System.out.println("LOG::Error: " + e.getMessage());
      }
  }

  public void Close() throws IOException {
    if(logFile!=null){
      logFile.flush();
      logFile.close();
    }
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }
}
