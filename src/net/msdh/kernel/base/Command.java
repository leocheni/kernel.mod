package net.msdh.kernel.base;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import net.msdh.kernel.utils.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 09.06.16
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class Command {
    private int id;
    private String method;
    private Map<String,Object> params;// = new HashMap<String,Object>();

    public Command(){
      params = new HashMap<String,Object>();
    }

    public Command(String jCommand){
      if(jCommand!=null){
		JSONRPC2Request request = null;
		try {
			request = JSONRPC2Request.parse(jCommand);
		}
        catch (JSONRPC2ParseException e) {
		  System.out.println(e);
          return;
		}
		method = request.getMethod();
		params = request.getNamedParams();
        id = Integer.parseInt(request.getID().toString());
      }
      else{
        Log.getInstance().W("Command::Contructor","Empty jCommand");
      }
      //isCorrect = false;
    }

    public Command(String method, Map<String, Object> params, int id) {
      this.method = method;
      this.params = params;
      this.id = id;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getMethod() {
      return method;
    }

    public void setMethod(String method) {
      this.method = method;
    }

    public Map<String, Object> getParams() {
      return params;
    }

    public void setParams(Map<String, Object> params) {
      this.params = params;
    }

    public String toJson(){
      JSONRPC2Request request = new JSONRPC2Request(method, params, id);
      return  request.toJSONString();
    }
}
