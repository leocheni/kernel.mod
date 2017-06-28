package net.msdh.kernel.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 26.05.17
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class Display {
  private static volatile Display instance;
  private String level;

  public static Display getInstance() {
    Display localInstance = instance;

    if(localInstance == null){
      synchronized (Display.class){
        localInstance = instance;
        if(localInstance == null) {
          instance = localInstance = new Display();
        }
      }
    }
    return localInstance;
  }

  private Display(){
  }

  public void D(String source, String message){
    if(level.equals("DEBUG")){
      System.out.println(source+"::"+message);
    }
  }

  public void I(String source, String message){

    if(level.equals("DEBUG") || level.equals("INFO")){
      System.out.println(source+"::"+message);
    }
  }

  public void W(String source, String message){
    if(level.equals("DEBUG") || level.equals("INFO") || level.equals("WARN")){
      System.out.println(source+"::"+message);
    }
  }

  public void E(String source, String message){
    if(level.equals("DEBUG") || level.equals("INFO") || level.equals("WARN") || level.equals("ERROR")){
      System.out.println(source+"::"+message);
    }
  }

  public void F(String source, String message){
    System.out.println(source+"::"+message);
  }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
