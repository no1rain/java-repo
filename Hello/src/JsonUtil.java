import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 
/**
 *    일반문자열 유틸.
 *
 * @author someone
 * @version 1.0.0
 */
public class JsonUtil {
 
    /**
     * Map을 json으로 변환한다.
     *
     * @param map Map<String, Object>.
     * @return JSONObject.
     */
    @SuppressWarnings("unchecked")
    public static JSONObject getJsonStringFromMap( Map<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject;
    }

    /**
     * @param Map<String, Object>
     * @appiNote Map<String, Object>를 JSONObject로 변환처리.
     * @return JSONObject
     */
    @SuppressWarnings("unchecked")
    public JSONObject convertMapToJson(Map<String, Object> map) {

        JSONObject json = new JSONObject();
        String key = "";
        Object value = null;
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            json.put(key, value);
        }
        return json;
    }
    
    /**
     * List<Map>을 jsonArray로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return JSONArray.
     */
    @SuppressWarnings("unchecked")
    public static JSONArray getJsonArrayFromList( List<Map<String, Object>> list )
    {
        JSONArray jsonArray = new JSONArray();
        for( Map<String, Object> map : list ) {
            jsonArray.add( getJsonStringFromMap( map ) );
        }
        
        return jsonArray;
    }
    
    /**
     * List<Map>을 jsonString으로 변환한다.
     *
     * @param list List<Map<String, Object>>.
     * @return String.
     */
    public static String getJsonStringFromList( List<Map<String, Object>> list )
    {
        JSONArray jsonArray = getJsonArrayFromList( list );
        return jsonArray.toJSONString();
    }
 
    /**
     * JsonObject를 Map<String, String>으로 변환한다.
     *
     * @param jsonObj JSONObject.
     * @appiNote JSONObject를 Map<String, Object> 형식으로 변환처리.
     * @return Map<String, Object>.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj )
    {
        Map<String, Object> map = null;
        
        try {
            
            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class) ;
            
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return map;
    }
 
    /**
     * JsonArray를 List<Map<String, String>>으로 변환한다.
     *
     * @param jsonArray JSONArray.
     * @return List<Map<String, Object>>.
     */
    public static List<Map<String, Object>> getListMapFromJsonArray( JSONArray jsonArray )
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        if( jsonArray != null )
        {
            int jsonSize = jsonArray.size();
            for( int i = 0; i < jsonSize; i++ )
            {
                Map<String, Object> map = JsonUtil.getMapFromJsonObject( ( JSONObject ) jsonArray.get(i) );
                list.add( map );
            }
        }
        
        return list;
    }

    // public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
    //     Map<String, Object> resultMap = new HashMap<String, Object>();

    //     if(json != JSONObject.NULL) {
    //         resultMap = toMap(json);
    //     }
    //     return resultMap;
    // }

    // public static Map<String, Object> toMap(JSONObject object) throws JSONException {
    //     Map<String, Object> map = new HashMap<String, Object>();

    //     Iterator<String> keysItr = object.keys()
    //     while(keysItr.hasNext()) {
    //         String key = keysItr.next();
    //         Object value = object.get(key);

    //         if(value instanceof JSONArray) {
    //             value = toList((JSONArray) value);
    //         }
    //         else if(value instanceof JSONObject) {
    //             value = toMap((JSONObject) value);
    //         }
    //         map.put(key, value);
    //     }
    //     return map;
    // }

    // public static List<Object> toList(JSONArray array) throws JSONException {
    //     List<Object> list = new ArrayList<Object>();
    //     for(int i = 0; i < array.length(); i++) {
    //         Object value = array.get(i);
    //         if(value instanceof JSONArray) {
    //             value = toList((JSONArray) value);
    //         }
    //         else if(value instanceof JSONObject) {
    //             value = toMap((JSONObject) value);
    //         }
    //         list.add(value);
    //     }
    //     return list;
    // }
}