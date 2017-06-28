package net.msdh.kernel.answer;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import net.minidev.json.JSONArray;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 22.05.17
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    private int id;
    private Map<String,Object> result;// = new HashMap<String,Object>();
    private JSONArray resultArray;

    public Result(Map<String,Object> result){
      this.result = result;
    }

    public Result(int id,Map<String,Object> result) {
        this.id = id;
        this.result = result;
    }

    public Result(int id,JSONArray result) {
            this.id = id;
            this.resultArray = result;
        }



    public String toJson(){
      JSONRPC2Response response;
      if(result!=null){
        response = new JSONRPC2Response(result,id);
      }
      else{
        response = new JSONRPC2Response(resultArray,id);
      }

      return  response.toJSONString();
    }
}
