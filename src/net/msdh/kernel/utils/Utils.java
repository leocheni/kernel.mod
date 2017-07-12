package net.msdh.kernel.utils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utils {

  public static ImageIcon createImageIcon(String path) {

    ImageIcon icon = null;
    try{
      Image img = ImageIO.read(new FileImageInputStream(new File(path)));
      icon = new ImageIcon(img);
    }
    catch (IOException ex) {
      System.out.println("Error: " + ex.getMessage());
    }
    return icon;
  }
}
