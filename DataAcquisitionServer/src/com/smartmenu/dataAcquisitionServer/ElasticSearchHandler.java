package com.smartmenu.dataAcquisitionServer;


import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import utils.Utils;

public class ElasticSearchHandler {

	private static final String SERVER_ENDPOINT = "http://search-smart-menu-hrhqs3qwyl3c6dmd47eq6f7eoy.us-east-1.es.amazonaws.com";
	private static final String INDEX = "/tweets";
	private static final String TWEET = "/tweet";
	private static final String SEARCH = "/_search";
	private static final String SLASH = "/";
	private static final String DISTANCE = "1000km";
	private static final Integer MAX_SIZE_RETURN = 2000; 
	
	//get an object by its id
	public static JSONObject getObjectByID(String index, String term, String id) throws JSONException {
		String url = Utils.buildURL(SERVER_ENDPOINT, SLASH, index, SLASH, term, SLASH, id);
		JSONObject res = Utils.sendGetRequest(url);
		return res.getJSONObject("_source");
	}
	
	//put an object to it's id
	public static JSONObject putAnObject(JSONObject obj, String index, String term, String id) throws JSONException {
		String url = Utils.buildURL(SERVER_ENDPOINT, SLASH, index, SLASH, term, SLASH, id);
		JSONObject res = Utils.sendPutRequest(url, obj.toString());
		return res;
	}
	
	protected static JSONArray searchByQueryMatchObject(JSONObject queryMatchObject, String index, String term) throws JSONException {
		JSONObject queryObject = generateQueryJSONByMatchJSON(queryMatchObject);
		return searchObjectsByQueryObject(queryObject, index, term);	
	}
	
	public static JSONArray searchByText(String queryString, String index, String term) throws JSONException{
		JSONObject queryMatchObject = generateQueryMatchObject("text", queryString);
		return searchByQueryMatchObject(queryMatchObject, index, term);
	}
	
	public static JSONArray searchByLocation(String lat, String lng, String index, String term) throws JSONException {
		JSONObject queryObject = addMaxSize(generateLocationQueryJSONObject(lat, lng));
		return searchObjectsByQueryObject(queryObject, index, term);		
	}
	
	//example input "text","lalala","restaurantID","daljdlfakjsf" 
	//multi match
	public static JSONObject generateQueryMatchObject(String...termsAndTexts) throws JSONException {

		JSONArray should = new JSONArray();
		for (int i = 0; i < termsAndTexts.length; i = i + 2) {
			try {
				JSONObject queryMatchObject = new JSONObject().put("match", 
						new JSONObject().put(termsAndTexts[i], termsAndTexts[i+1]));
				should.put(queryMatchObject);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		JSONObject result = new JSONObject();
		result.put("bool", new JSONObject().put("should", should));
		return result;
	}
	
	private static JSONObject addMaxSize(JSONObject obj) throws JSONException {
		obj.put("size", MAX_SIZE_RETURN);
		return obj;
	}

	private static JSONArray searchObjectsByQueryObject(JSONObject queryObject, String index, String term) throws JSONException{
		String url = Utils.buildURL(SERVER_ENDPOINT, SLASH, index, SLASH, term, SEARCH);
		JSONObject res = Utils.sendGetRequest(url, queryObject.toString());
		JSONArray hits = Utils.getJSONArray(Utils.getJSONObject(res, "hits"), "hits");
		JSONArray result = new JSONArray();
		for (int i = 0; i < hits.length(); i++) {
			JSONObject tweet = Utils.getJSONObject(Utils.getJSONObject(hits, i), "_source");
			result.put(tweet);
		}
		return result;
	}

	// '{"query": {"match": {"text": "testText"}}}'
	private static JSONObject generateQueryJSONByMatchJSON(JSONObject queryMatchObject) throws JSONException {
		JSONObject query = new JSONObject();
		JSONObject queryContents = queryMatchObject;
		query.put("query", queryContents);
		return query;
	}

//	{
//		  "query": {
//		    "filtered" : {
//		        "query" : {
//		            "match" : {}
//		        },
//		        "filter" : {
//		            "geo_distance" : {
//		                "distance" : "20km",
//		                "location" : {
//		                    "lat" : 37.9174,
//		                    "lon" : -122.3050
//		                }
//		            }
//		        }
//		    }
//		  }
//		}'
	
	private static JSONObject generateLocationQueryJSONObject(String lat, String lng) throws JSONException {
		JSONObject location = new JSONObject();
		location.put("lat", lat);
		location.put("lon", lng);
		JSONObject geoDistance = new JSONObject();
		geoDistance.put("distance", DISTANCE);
		geoDistance.put("location", location);
		JSONObject filter = new JSONObject();
		filter.put("geo_distance", geoDistance);
		JSONObject filtered = new JSONObject();
		filtered.put("filter", filter);
		JSONObject query = new JSONObject();
		query.put("filtered", filtered);
		JSONObject result = new JSONObject();
		result.put("query", query);
		return result;
	}
	
//	//with query string
//	private static JSONObject generateLocationQueryJSONObjectWithQueryString(String queryString, String lat, String lng) throws JSONException {
//		JSONObject innerQuery = generateTextQueryJSONObject(queryString);
//		JSONObject location = new JSONObject();
//		location.put("lat", lat);
//		location.put("lon", lng);
//		JSONObject geoDistance = new JSONObject();
//		geoDistance.put("distance", DISTANCE);
//		geoDistance.put("location", location);
//		JSONObject filter = new JSONObject();
//		filter.put("geo_distance", geoDistance);
//		JSONObject filtered = new JSONObject();
//		Utils.putAll(filtered, innerQuery);
//		filtered.put("filter", filter);
//		JSONObject query = new JSONObject();
//		query.put("filtered", filtered);
//		JSONObject result = new JSONObject();
//		result.put("query", query);
//		return result;
//	}

}
