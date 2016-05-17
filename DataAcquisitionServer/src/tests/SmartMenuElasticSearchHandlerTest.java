package tests;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.amazonaws.util.json.JSONException;
import com.smartmenu.dataAcquisitionServer.SmartMenuElasticSearchHandler;

public class SmartMenuElasticSearchHandlerTest {
	
//	private static final String restaurantID = "40a55d80f964a52020f31ee3";
	
	private static final String waverlyRestaurantID = "43bfd385f964a520232d1fe3";
	
	private static final String dishName = "Soups";
	
	private static final String lat = "40.72107924768216";
	
	private static final String lng = "-73.98394256830215";			

	public static void main(String[] args) throws JSONException, FileNotFoundException, UnsupportedEncodingException {
//		System.out.println(SmartMenuElasticSearchHandler.getDishesByRestaurantID(PostRestaurantDataServletTest.goodID).toString());
//		System.out.println(SmartMenuElasticSearchHandler.getRestaurantByRestaurantID(waverlyRestaurantID).toString());
//		System.out.println(SmartMenuElasticSearchHandler.searchRestaurantsByLocation(lat, lng).toString());
//		System.out.println(SmartMenuElasticSearchHandler.searchReviewsByDishNameAndRestaurant(dishName, restaurantID).toString());
		for (Integer userID = 1; userID <= 5; userID++){
			for (int rest = 0; rest < PostRestaurantDataServletTest.restaurants.length; rest++) {
				printToPredict("0000" + userID.toString(), PostRestaurantDataServletTest.restaurants[rest]);
			}
		}
	}
	
	public static void printToPredict(String userID, String restaurantID) throws JSONException, FileNotFoundException, UnsupportedEncodingException {
		String[] dishIDs = SmartMenuElasticSearchHandler.getRestaurantByRestaurantID(restaurantID).getString("dishIDs").split(",");
		String filename = "ToPredicts_" + userID + "_" + restaurantID;
		PrintWriter writer = new PrintWriter(filename + ".txt", "UTF-8");
		for (int i = 0; i < dishIDs.length; i++) {
			writer.println(userID + "," + restaurantID + "," + dishIDs[i]);
		}
		writer.close();
	}

}
