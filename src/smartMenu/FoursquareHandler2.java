package smartMenu;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.Utils;

public class FoursquareHandler2 {

	private static final String CLIENT_ID = "LDTUI1GZBPNE2ZSXNMOZDUIBBQNUMYJ5VPRY1AW2XIFDLNAH";
	private static final String CLIENT_SECRET = "F3CTNTRHQWR3NOFLM0CJUZXQB0LUGZHSAPQ2JDONXWT0I0XL";
	private static final String ID_AND_SECRET = "client_id=" + CLIENT_ID + "&" + "client_secret=" + CLIENT_SECRET;

	// + venueid + /tips
	private static final String VENUES_LINK = "https://api.foursquare.com/v2/venues/";
	private static final String VENUES_SEARCH_LINK = VENUES_LINK + "search?";
	private static final String TIPS = "tips?";
	private static final String MENU = "menu?";
	private static final String VERSION = "v=20160405";
	private static final String AND = "&";
	private static final String SLASH = "/";
	// for tips max return 500 tips
	private static final Integer SEARCH_LIMIT_INTEGER = 2;
	private static final String SEARCH_LIMIT = "limit=" + SEARCH_LIMIT_INTEGER.toString();
	private static final String OFFSET = "offset=";
	private static final String LOCATION = "ll=";
	private static final String QUERY_RESTAURANT = "query=restaurant";



	/**
	 * return example
	 * [{"text":""}...]
	 * 
	 * @param restaurantID
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static JSONArray getReviewsByRestaurantID(String restaurantID) {
		JSONArray items = new JSONArray();
		JSONArray reviews = new JSONArray();
		Integer offset = 0;
		try {
			do {
				String url = Utils.buildURL(VENUES_LINK, restaurantID, SLASH, TIPS, AND, OFFSET, offset.toString(), AND,
						SEARCH_LIMIT, AND, ID_AND_SECRET, AND, VERSION);
				JSONObject responseObject = Utils.sendGetRequest(url);
				JSONObject tips = (JSONObject) ((JSONObject) responseObject.get("response")).get("tips");
				items = (JSONArray) tips.get("items");
				for (int i = 0; i < items.size(); i++) {
					String text = (String) ((JSONObject) items.get(i)).get("text");
					JSONObject textJSON = new JSONObject();
					textJSON.put("text", text);
					reviews.add(textJSON);
				}
				offset = reviews.size();
			} while (items.size() == SEARCH_LIMIT_INTEGER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviews;
	}

	/**
	 * 
	 * return example [{"location":{"lat":"","lng":""},"hasMenu","name"}...] 
	 * 
	 * @param lat
	 * @param lng
	 * @return
	 */
	public static JSONArray getRestaurantsByLocation(String lat, String lng) {
		String url = Utils.buildURL(VENUES_SEARCH_LINK, LOCATION, lat, ",", lng, AND, QUERY_RESTAURANT, AND, SEARCH_LIMIT,
				AND, ID_AND_SECRET, AND, VERSION);
		try {
			JSONObject responseObject = Utils.sendGetRequest(url);
			JSONArray venues = Utils.getJSONArray(Utils.getJSONObject(responseObject, "response"), "venues");
			for (int i = 0; i < venues.size(); i++) {
				JSONObject obj = Utils.getJSONObject(venues, i);
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
			return venues;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}

	/**
	 * return example[{name:"",type:"",price:"",description:""},{}]
	 * 
	 * @param restaurantID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray getMenuByRestaurantID(String restaurantID) {
		JSONArray menu = new JSONArray();
		try {
			String url = Utils.buildURL(VENUES_LINK, restaurantID, SLASH, MENU, AND, ID_AND_SECRET, AND, VERSION);
			JSONObject responseObject = Utils.sendGetRequest(url);
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
	
	public static JSONArray getRestaurantsByBounds(String swLatitude, String swLongitude, String neLatitude,
			String neLongitude) {
		// TODO
		return null;
	}


}
