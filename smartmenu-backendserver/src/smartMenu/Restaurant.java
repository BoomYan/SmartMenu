package smartMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import utils.Utils;

public class Restaurant {
	
	private static final String REVIEWS_COUNT = "reviewsCount";
	
	private final String restaurantID;
	
	private JSONArray dishes;
	private final Map<String, JSONArray> dishIDToReviews;
	
	private final static Integer multiplier = 100;
	
	public static Restaurant constructRestaurant(String restaurantID, String userID) {
		Restaurant restaurant;
		try {
			restaurant = new Restaurant(restaurantID);
			restaurant.updateDishesRecommendation(userID);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return restaurant;
	}
	
	private void updateDishesRecommendation(String userID) throws JSONException {
		dishes = sendDishesToMLServer().getJSONArray("dishes");
	}
	
	private JSONObject sendDishesToMLServer() throws JSONException {
		return Utils.sendGetRequest("smartmenu-mlserver.us-east-1.elasticbeanstalk.com/getRecommendation", dishes.toString());
	}
	
	//update each dishes with reviews count
	private static Map<String, JSONArray> getDishIDToReviews(JSONArray dishes, String restaurantID) throws JSONException {
		Map<String, JSONArray> result = new HashMap<>();
		Integer maxReviewsCount = 0;
		for (int i = 0; i < dishes.length(); i++) {
			JSONObject dish = Utils.getJSONObject(dishes, i);
			String dishName = dish.get("name").toString();
			String dishID = dish.get("dishID").toString();
			JSONArray dishReviews = SmartMenuElasticSearchHandler.searchReviewsByDishNameAndRestaurant(dishName, restaurantID);
			result.put(dishID, dishReviews);
			dish.put(REVIEWS_COUNT, dishReviews.length());
			maxReviewsCount = Math.max(dishReviews.length(), maxReviewsCount);
		}
		for (int i = 0; i < dishes.length(); i++) {
			JSONObject dish = Utils.getJSONObject(dishes, i);
			try {
				dish.put("popularity", Math.round((multiplier * (Double.parseDouble(dish.get(REVIEWS_COUNT).toString()) / maxReviewsCount))));
			}catch(Exception e) {
				dish.put("popularity", "0");
			}
		}
		return result;
	}
	

	private Restaurant(String restaurantID) throws JSONException {
		this.restaurantID = restaurantID;
		dishes = SmartMenuElasticSearchHandler.getDishesByRestaurantID(restaurantID);
		dishIDToReviews = getDishIDToReviews(dishes, restaurantID);
	}

	public JSONArray getReviewsByDishName(String dishName) {
		return getDishToReviews().get(dishName);
	}
	
	public JSONArray getDishes() {
		return dishes;
	}
	
	private Map<String, JSONArray> getDishToReviews() {
		return dishIDToReviews;
	}

	public String getId() {
		return restaurantID;
	}

}
