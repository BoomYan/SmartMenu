package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.kevinsawicki.http.HttpRequest;

public class Utils {
	
	private static final JSONParser JSON_PARSER = new JSONParser();

	// link several strings to one string
	public static String buildURL(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String str : strings) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
		return (JSONObject) jsonObject.get(key);
	}

	public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
		return (JSONObject) jsonArray.get(index);
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
		return (JSONArray) jsonObject.get(key);
	}
	
	// take get http url and return response object as JSONObject
	public static JSONObject sendGetRequest(String url) throws ParseException {
		String response = HttpRequest.get(url).body();
		return (JSONObject) JSON_PARSER.parse(response);
	}
}
