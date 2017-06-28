package net.msdh.kernel.system;

import com.sun.glass.ui.Size;
import net.msdh.kernel.ui.Display;
import net.msdh.kernel.utils.Log;

import javax.net.ssl.SSLEngineResult;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 04.07.16
 * Time: 11:06
 * To change this template use File | Settingsdddd | File Templates.
 */
public class Zpool {

    private String name;
    private String size;
    private String alloc;
    private String free;
    private String stat;

    public Zpool() {
    }

    public Zpool(String zpool) {

      if(zpool!=null){
        String zz;
        zz = zpool.replaceAll("[\\s]{2,}"," ");
        Pattern p = Pattern.compile(" ");
        String[] words = p.split(zz);
        name = words[0];
        size = words[1];
        alloc = words[2];
        free = words[3];
        stat = words[8];
      }
      else{
        Log.getInstance().W("Zpools", "Zpoll Source = null");
      }
    }

    public Zpool(String name, String size, String alloc, String free, String stat) {
      this.name = name;
      this.size = size;
      this.alloc = alloc;
      this.free = free;
      this.stat = stat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAlloc() {
        return alloc;
    }

    public void setAlloc(String alloc) {
        this.alloc = alloc;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
