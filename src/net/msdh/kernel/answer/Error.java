package net.msdh.kernel.answer;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 22.05.17
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class Error {
  private int id;
  private Map<String,Object> params;// = new HashMap<String,Object>();


    public Error(Map<String, Object> params) {
      this.params = params;
    }

    public Error(int id, Map<String, Object> params) {
      this.id = id;
      this.params = params;
    }

    public String toJson(){
      //JSONRPC2Error error = new JSONRPC2Error(id,"message",params);
      JSONRPC2Response response = new JSONRPC2Response(params,id);
      return  response.toJSONString();
    }

}
