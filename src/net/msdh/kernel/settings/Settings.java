package net.msdh.kernel.settings;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 29.06.16
 * Time: 11:24
 * To change this template use File | Settings | File Templates.
 */
public class Settings {

   private static volatile Settings instance;
   private String source;
   public JSONObject Base;

   private String coreAdress;
   private int corePort;

   private boolean inicialized;


   public static Settings getInstance() {
	Settings localInstance = instance;

	if(localInstance == null){
	  synchronized (Settings.class){
	    localInstance = instance;
		if(localInstance == null) {
		  instance = localInstance = new Settings();
		}
	  }
	}
	return localInstance;
  }

  private Settings(){
    inicialized = false;
  }

  public  void Load(String source) throws IOException, ParseException {

    FileInputStream inFile = new FileInputStream(source);
    byte[] str = new byte[inFile.available()];
    inFile.read(str);
    String config = new String(str);
    inFile.close();
    System.out.println("Config: " + config);

    Base = (JSONObject)JSONValue.parse(config);
    inicialized = true;
  }

  public void save() throws IOException {

      FileOutputStream outFile = new FileOutputStream(source);
      byte[] config = new byte[Base.size()];
      outFile.write(config);
      outFile.flush();
      outFile.close();
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
     this.source = source;
  }

  public boolean isInicialized() {
    return inicialized;
  }

    public String getCoreAdress() {
        return coreAdress;
    }

    public void setCoreAdress(String coreAdress) {
        this.coreAdress = coreAdress;
    }

    public int getCorePort() {
        return corePort;
    }

    public void setCorePort(int corePort) {
        this.corePort = corePort;
    }
}
