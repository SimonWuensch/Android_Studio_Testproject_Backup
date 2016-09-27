package ssi.ssn.com.ssi_service.model.handler;
import com.owlike.genson.Genson;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {

    public static String toJson(Object object){
        Genson genson = new Genson();
        return genson.serialize(object);
    }

    public static Object fromJsonGeneric(Class value, String jsonString){
        Genson genson = new Genson();
        return genson.deserialize(jsonString, value);
    }

    public static Map<String, Object> fromJsonToMap(String jsonString){
        return fromJsonToMap(new LinkedHashMap<String, Object>(), jsonString);
    }

    private static Map<String, Object> fromJsonToMap(Map<String, Object> rootMap, String jsonString) {
        Map<String, Object> map = (LinkedHashMap<String, Object>) JsonHandler.fromJsonGeneric(LinkedHashMap.class, jsonString);
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value.toString().startsWith("{") && value.toString().endsWith("}")) {
                Map<String, Object> newMap = new LinkedHashMap<>();
                rootMap.put(key, newMap);
                fromJsonToMap(newMap, JsonHandler.toJson(value));
            }else if (value.toString().startsWith("[") && value.toString().endsWith("]")) {
                if(!value.toString().contains(",")){
                    rootMap.put(key, value + "");
                    continue;
                }
                value = JsonHandler.toJson(value);
                List list = (List) JsonHandler.fromJsonGeneric(List.class, value.toString());
                for(int i = 0; i < list.size(); i++){
                    Map<String, Object> newMap = new LinkedHashMap<>();
                    rootMap.put(i + 1 + ". " + key, newMap);
                    try{
                        fromJsonToMap(newMap, JsonHandler.toJson(list.get(i)));
                    }catch(Throwable t){
                        t.printStackTrace();
                    }
                }
            }else{
                rootMap.put(key, value + "");
            }
        }
        return rootMap;
    }
}