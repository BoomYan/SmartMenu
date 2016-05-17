package com.smartmenu.dataAcquisitionServer;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class SmartMenuElasticSearchHandler extends ElasticSearchHandler {

	
	/*
	 * puts
	 */
	
	
	public static void putReviews(JSONArray reviews) {
		for (int i = 0; i < reviews.length(); i++) {
			try {
				JSONObject review = reviews.getJSONObject(i);
				putAnObject(review, "reviews", "review", reviewObjectToID(review));
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	private static String reviewObjectToID(JSONObject review) throws JSONException {
		String toHashString = review.getString("text") + review.getString("restaurantID");
		return Integer.toString(toHashString.hashCode());
	}
	
	public static void putRestaurant(JSONObject restaurant) {
		try {
			putAnObject(restaurant, "restaurants", "restaurant", restaurant.getString("restaurantID"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	public static void putDishes(JSONArray dishes) {
		for (int i = 0; i < dishes.length(); i++) {
			try {
				JSONObject dish = dishes.getJSONObject(i);
				putAnObject(dish, "dishes", "dish", dish.getString("dishID"));
			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	/*
	 * searches
	 */
	
	public static JSONArray searchRestaurantsByLocation(String lat, String lng) {
		try {
			JSONArray restaurants = ElasticSearchHandler.searchByLocation(lat, lng, "restaurants", "restaurant");
			return restaurants;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONArray searchReviewsByDishNameAndRestaurant(String dishName, String restaurantID) {
		try {
			JSONObject queryMatchObject = ElasticSearchHandler.generateQueryMatchObject("text", dishName, "restaurantID", restaurantID);
			 return ElasticSearchHandler.searchByQueryMatchObject(queryMatchObject, "reviews", "review");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new JSONArray();
	}

	
	/*
	 * gets
	 */

	public static JSONObject getRestaurantByRestaurantID(String restaurantID) {
		try {
			return ElasticSearchHandler.getObjectByID("restaurants", "restaurant", restaurantID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static JSONArray getDishesByRestaurantID(String restaurantID) throws JSONException {
		JSONArray dishes = new JSONArray();
		JSONObject restaurant = getRestaurantByRestaurantID(restaurantID);
		String[] dishIDs = restaurant.getString("dishIDs").split(",");
		for (int i = 0; i < dishIDs.length; i++) {
			String dishID = dishIDs[i];
			JSONObject dish = getDishByDishID(dishID);
			dishes.put(dish);
		}
		return dishes;
	}

	public static JSONObject getDishByDishID(String dishID) {
		try {
			return ElasticSearchHandler.getObjectByID("dishes", "dish", dishID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}



}
