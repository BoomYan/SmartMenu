package utils;


import java.util.Iterator;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;

public class Utils {

	// link several strings to one string
	public static String buildURL(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String str : strings) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static JSONObject getJSONObject(JSONObject jsonObject, String key) throws JSONException{
		String objStr = jsonObject.getString(key);
		return new JSONObject(objStr);
	}

	public static JSONObject getJSONObject(JSONArray jsonArray, int index) throws JSONException {
		return jsonArray.getJSONObject(index);
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String key) throws JSONException {
		return jsonObject.getJSONArray(key);
	}
	
	//put b into a
	public static void putAll(JSONObject aObj, JSONObject bObj) throws JSONException {
		@SuppressWarnings("rawtypes")
		Iterator keys = bObj.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			aObj.put(key, bObj.get(key));
		}
		
	}
	
	// take get http url and return response object as JSONObject
	public static JSONObject sendGetRequest(String url) throws JSONException{
		HttpRequest res = HttpRequest.get(url);
		return handleResponse(res, url);
	}
	
	public static JSONObject sendGetRequest(String url, CharSequence data) throws JSONException{
		HttpRequest res = HttpRequest.get(url).send(data);
		return handleResponse(res, url);
	}
	
	public static JSONObject sendPutRequest(String url, CharSequence data) throws JSONException{
		HttpRequest res = HttpRequest.put(url).send(data);
		return handleResponse(res, url);
	}
	
	private static JSONObject handleResponse(HttpRequest res, String url) throws JSONException{
		if (res.badRequest()) {
			throw new IllegalArgumentException(res.body());
		}
		String response = res.body();
		return new JSONObject(response);
	}
}
