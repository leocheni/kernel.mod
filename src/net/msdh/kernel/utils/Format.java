package net.msdh.kernel.utils;

public class Format {

  public static String Humanize_number(double num,int scale){

    String sizze = " Gb";
    //stringstream hnStr;

    if(num<1000000){
      num=num/1024;
      sizze = " Kb";
    }
    else if(num<1000000000){
      num = (num/1024)/1024;
      sizze = " Mb";
    }
    else{
      num = ((num/1024)/1024)/1024;
    }
    // System.out.println(num);
    //hnStr<<(floor((num)*100 +0.5)/100)<<sizze;



    return Math.round(num * Math.pow(10, scale)) / Math.pow(10, scale) + " " + sizze;
  }
}
