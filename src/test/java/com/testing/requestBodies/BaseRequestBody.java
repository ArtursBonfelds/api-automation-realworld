package com.testing.requestBodies;

import org.json.JSONObject;

import java.util.*;

import static com.tools.support.SupportVariables.SPLIT_PATTERN;

public class BaseRequestBody {
  // Variables
  private JSONObject body;
  // Constructors
  public BaseRequestBody(){
    body =  new JSONObject();
  }
  // Methods
  public void addKey(String key, Object value) {
    JSONObject convertedJson = convertComplexKeyToJson(key.replaceAll("\\s+",""), value);
    String firstKey = convertedJson.keySet().iterator().next().replaceAll("\\s+","");

    if(body.has(firstKey)){
      JSONObject tempBody = (JSONObject) body.get(firstKey);
      JSONObject temp = (JSONObject) convertedJson.get(firstKey);
      String currentBodyKey;
      String currentTempKey;
     while (true){
       currentBodyKey = tempBody.keySet().iterator().next();
       currentTempKey = temp.keySet().iterator().next();
       if (currentBodyKey.equals(currentTempKey)){
         tempBody = (JSONObject) tempBody.get(currentBodyKey);
         temp = (JSONObject) temp.get(currentTempKey);
       } else {
         tempBody.put(currentTempKey, temp.get(currentTempKey));
         return;
       }
     }
    } else {
      body.put(firstKey, convertedJson.get(firstKey));
    }
  }

  private JSONObject convertComplexKeyToJson(String key, Object value){
    Iterator<String> it = Arrays.asList(key.split(SPLIT_PATTERN)).iterator();
    JSONObject json = new JSONObject();
    JSONObject last = json;

    while(it.hasNext()){
      String current = it.next();
      JSONObject temp = new JSONObject();
      if(it.hasNext()){
        last.put(current, temp);
        last = temp;
      } else {
        last.put(current, value);
      }
    }
    return json;
  }

  public String getBody() {
    return body.toString();
  }
}
