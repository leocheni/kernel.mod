package net.msdh.kernel.errors;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */
public class CoreException extends Throwable{
  private String message;
  private String source;
  private int code;

  public CoreException(int code, String source, String message){
    this.message = message;
    this.source = source;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

    public String getSource() {
        return source;
    }

    public int getCode() {
        return code;
    }
}
