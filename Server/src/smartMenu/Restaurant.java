package smartMenu;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.Utils;

public class Restaurant {
	
	private static final String REVIEWS_COUNT = "reviewsCount";
	
	private final String id;
	
	private final JSONArray menu;
	private final JSONArray reviews;
	private final Map<String, JSONArray> dishToReviews;
	
	@SuppressWarnings("unchecked")
	private static Map<String, JSONArray> getDishToReviews(JSONArray menu, JSONArray reviews) {
		Map<String, JSONArray> result = new HashMap<>();
		for (int i = 0; i < menu.size(); i++) {
			JSONObject dish = Utils.getJSONObject(menu, i);
			String dishName = (String)dish.get("name");
			JSONArray dishReviews = new JSONArray();
			for (int j = 0; j < reviews.size(); j++) {
				String reviewText = (String)Utils.getJSONObject(reviews, j).get("text");
				if (reviewText.contains(dishName)) {
					JSONObject reviewObj = new JSONObject();
					reviewObj.put("text", reviewText);
					dishReviews.add(reviewObj);
				}
			}
			result.put(dishName, dishReviews);
			dish.put(REVIEWS_COUNT, dishReviews.size());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static void sortMenu(JSONArray menu) {
		Collections.sort(menu, new Comparator<Object>(){
			@Override
			public int compare(Object o1, Object o2) {
				JSONObject j1 = (JSONObject) o1;
				JSONObject j2 = (JSONObject) o2;
				return (Integer)j2.get(REVIEWS_COUNT) - (Integer)j1.get(REVIEWS_COUNT);
			}
			
		});
	}
	

	public Restaurant(String id) {
		this.id = id;
		menu = FoursquareHandler2.getMenuByRestaurantID(id);
		reviews = FoursquareHandler2.getReviewsByRestaurantID(id);
		dishToReviews = getDishToReviews(menu, reviews);
		sortMenu(menu);
	}

	public JSONArray getReviewsByDishName(String dishName) {
		return getDishToReviews().get(dishName);
	}
	
	public JSONArray getSortedMenu() {
		return menu;
	}
	
	public String getId() {
		return id;
	}
	
	private Map<String, JSONArray> getDishToReviews() {
		return dishToReviews;
	}

}
