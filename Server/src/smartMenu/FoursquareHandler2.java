package smartMenu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import utils.Utils;

public class FoursquareHandler2 {

	private static final String AND = "&";
	private static final String SLASH = "/";

	private static final String CLIENT_ID = "client_id=LDTUI1GZBPNE2ZSXNMOZDUIBBQNUMYJ5VPRY1AW2XIFDLNAH";
	private static final String CLIENT_SECRET = "client_secret=F3CTNTRHQWR3NOFLM0CJUZXQB0LUGZHSAPQ2JDONXWT0I0XL";
	private static final String VERSION = "v=20160405";
	private static final String ID_AND_SECRET_AND_VERSION = CLIENT_ID + AND + CLIENT_SECRET + AND + VERSION;

	// + venueid + /tips
	private static final String VENUES_LINK = "https://api.foursquare.com/v2/venues/";
	private static final String VENUES_SEARCH_LINK = VENUES_LINK + "search?";
	private static final String TIPS = "tips?";
	private static final String MENU = "menu?";

	// for tips max return 500 tips
	private static final Integer SEARCH_LIMIT_INTEGER = 500;
	private static final String SEARCH_LIMIT = "limit=" + SEARCH_LIMIT_INTEGER.toString();
	private static final String OFFSET = "offset=";
	private static final String LOCATION = "ll=";
	private static final String SW = "sw=";
	private static final String NE = "ne=";
	private static final String INTENT_BROWSE = "intent=browse";
	private static final String QUERY_RESTAURANT2 = "categoryId=4d4b7105d754a06374d81259";
	private static final String QUERY_RESTAURANT = "query=restaurant";

	private static final String SOURCE_FOURSQUARE = "Foursquare";
	
	/**
	 * return example [{"text":""}...]
	 * 
	 * @param restaurantID
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static JSONArray getReviewsByRestaurantID(String restaurantID) {
		//TODO
		JSONArray items = new JSONArray();
		JSONArray reviews = new JSONArray();
		Integer offset = 0;
		try {
			do {
				String url = Utils.buildURL(VENUES_LINK, restaurantID, SLASH, TIPS, AND, OFFSET, offset.toString(), AND,
						SEARCH_LIMIT, AND, ID_AND_SECRET_AND_VERSION);
				JSONObject responseObject = Utils.sendGetRequest(url);
				JSONObject tips = (JSONObject) ((JSONObject) responseObject.get("response")).get("tips");
				items = (JSONArray) tips.get("items");
				for (int i = 0; i < items.size(); i++) {
					String text = (String) ((JSONObject) items.get(i)).get("text");
					JSONObject textJSON = new JSONObject();
					textJSON.put("text", text);
					textJSON.put("source", SOURCE_FOURSQUARE);
					reviews.add(textJSON);
				}
				offset = reviews.size();
			} while (items.size() == SEARCH_LIMIT_INTEGER);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(restaurantID + " is not a valid id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}

	/**
	 * 
	 * return example [{"location":{"lat":"","lng":""},"hasMenu","name"}...]
	 * 
	 * @param ll
	 *            example 44.5,33.5
	 * @return
	 */
	public static JSONArray getRestaurantsByLocation(String ll) {
		String url = Utils.buildURL(VENUES_SEARCH_LINK, LOCATION, ll, AND, QUERY_RESTAURANT, AND, SEARCH_LIMIT, AND,
				ID_AND_SECRET_AND_VERSION);
		return getRestaurantsByURL(url);
	}

	public static JSONArray getRestaurantsByBounds(String sw, String ne) {
		String url = Utils.buildURL(VENUES_SEARCH_LINK, SW, sw, AND, NE, ne, AND, INTENT_BROWSE, AND, QUERY_RESTAURANT,
				AND, SEARCH_LIMIT, AND, ID_AND_SECRET_AND_VERSION);
		return getRestaurantsByURL(url);
	}

	/*
	 * Only Return max 50 restaurants
	 */
	private static JSONArray getRestaurantsByURL(String url) {
		try {
			JSONObject responseObject = Utils.sendGetRequest(url);
			JSONArray venues = Utils.getJSONArray(Utils.getJSONObject(responseObject, "response"), "venues");
			for (int i = 0; i < venues.size(); i++) {
				JSONObject obj = Utils.getJSONObject(venues, i);
				cleanRestaurantObject(obj);
			}
			return venues;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}

	private static void cleanRestaurantObject(JSONObject obj) {
		obj.remove("contact");
		obj.remove("categories");
		obj.remove("verified");
		obj.remove("stats");
		obj.remove("delivery");
		obj.remove("allowMenuUrlEdit");
		obj.remove("specials");
		obj.remove("venuePage");
		obj.remove("menu");
		obj.remove("hereNow");
		obj.remove("referralId");
		obj.remove("venueChains");
	}

	/**
	 * return example[{name:"",type:"",price:"",description:""},{}]
	 * 
	 * 
	 * 
	 * @param restaurantID
	 * @return invalid id => throw Illegal ArgumentException() not found menu =>
	 *         return empty JSONArray();
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray getMenuByRestaurantID(String restaurantID) {
		JSONArray menu = new JSONArray();
		String url = Utils.buildURL(VENUES_LINK, restaurantID, SLASH, MENU, AND, ID_AND_SECRET_AND_VERSION);
		JSONObject responseObject;
		try {
			responseObject = Utils.sendGetRequest(url);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(restaurantID + " is not a valid id");
		} catch (ParseException e) {
			return menu;
		}
		try {
			JSONObject menus = (JSONObject) ((JSONObject) ((JSONObject) responseObject.get("response")).get("menu"))
					.get("menus");
			// sets of menu e.g. main menu, lunch special menu...
			JSONArray items0 = (JSONArray) menus.get("items");
			for (int i = 0; i < items0.size(); i++) {
				JSONObject mainMenu = (JSONObject) (items0).get(i);
				// e.g. starters, soups...
				JSONArray items1 = Utils.getJSONArray(Utils.getJSONObject(mainMenu, "entries"), "items");
				for (int j = 0; j < items1.size(); j++) {
					JSONObject entry = Utils.getJSONObject(items1, j);
					String courseType = (String) entry.get("name");
					// e.g. food1, food2...
					JSONArray items2 = Utils.getJSONArray(Utils.getJSONObject(entry, "entries"), "items");
					for (int k = 0; k < items2.size(); k++) {
						JSONObject course = Utils.getJSONObject(items2, k);
						course.remove("entryId");
						course.remove("prices");
						course.remove("options");
						course.remove("additions");
						course.put("type", courseType);
						menu.add(course);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menu;
	}

}
