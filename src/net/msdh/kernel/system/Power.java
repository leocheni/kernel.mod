package net.msdh.kernel.system;

import net.minidev.json.JSONObject;
import net.msdh.kernel.settings.Settings;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Power {

  private boolean inicialized;

  private String charge;
  private String voltage;
  private String voltageNominal;
  private String model;
  private String inputVoltage;
  private String outputVoltage;
  private String load;
  private String status;
  private String type;


  public Power(){
    model="N/A";
    charge = "N/A";
    voltage = "N/A";
    voltageNominal = "N/A";
    inputVoltage = "N/A";
    outputVoltage = "N/A";
    load = "N/A";
    status = "N/A";
    inicialized = false;
  }

public void Inicialize(){

    JSONObject objDepen = (JSONObject) Settings.getInstance().Base.get("depend");
    String nutPath = (String)objDepen.get("nut");
    File file = new File(nutPath);
    if(file.exists() && file.isFile()){
      String raw = null;
      try {
        raw = Systems.exec(nutPath + " cyber@localhost");
        Log.getInstance().D("CORE.Power:Raw upsc: ",raw);
      }
      catch (IOException e) {
        Log.getInstance().E("CORE.Power",e.getMessage());
        Display.getInstance().E("CORE.Power",e.getMessage());
      }
      catch (InterruptedException e) {
        Log.getInstance().E("CORE.Power",e.getMessage());
        Display.getInstance().E("CORE.Power",e.getMessage());
      }

      if(raw!=null){
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(raw.getBytes())));
        String line;
        Map<String,String> params = new HashMap<String, String>();

        try {
          while ((line = reader.readLine()) != null) {
            Pattern p = Pattern.compile(":");
            String[] words = p.split(line);
            params.put(words[0],words[1]);
            System.out.println(line);
          }
          reader.close();
        }
        catch (IOException e) {
          Log.getInstance().E("CORE.Power",e.getMessage());
          Display.getInstance().E("CORE.Power",e.getMessage());
        }

        model=params.get("device.model");
        charge = params.get("battery.charge");
        voltage = params.get("battery.voltage");
        voltageNominal = params.get("battery.voltage.nominal");
        inputVoltage = params.get("input.voltage");
        outputVoltage = params.get("output.voltage");
        load = params.get("ups.load");
        status = params.get("ups.status");
        inicialized = true;
      }
      else{
        Log.getInstance().W("CORE.Power", "Answer from nut = null");
      }
    }
    else{
      Log.getInstance().W("CORE.Power", "NUT is not installed");
    }
  }

public JSONObject toJson(){

  JSONObject tPower = new JSONObject();
  tPower.put("model",model);
  tPower.put("charge",charge);
  tPower.put("voltage",voltage);
  tPower.put("voltageNominal",voltageNominal);
  tPower.put("inputVoltage",inputVoltage);
  tPower.put("outputVoltage",outputVoltage);
  tPower.put("load",load);
  tPower.put("status",status);

  return tPower;
}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public boolean isInicialized() {
        return inicialized;
    }

    public void setInicialized(boolean inicialized) {
        this.inicialized = inicialized;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getVoltageNominal() {
        return voltageNominal;
    }

    public void setVoltageNominal(String voltageNominal) {
        this.voltageNominal = voltageNominal;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(String inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public String getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(String outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
