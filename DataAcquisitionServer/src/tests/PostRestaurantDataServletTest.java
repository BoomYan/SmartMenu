package tests;

import com.smartmenu.dataAcquisitionServer.PostRestaurantDataServlet;

public class PostRestaurantDataServletTest {

	static final String restaurantID = "40a55d80f964a52020f31ee3";
	
	static final String salamID = "4b9ef1f0f964a5200e0c37e3";
	
	static final String waverlyRestaurantID = "43bfd385f964a520232d1fe3";
	
	static final String goodID = "45b9c8c1f964a520d7411fe3";
	
	static final String[] restaurants = {salamID, waverlyRestaurantID, goodID};
	
	public PostRestaurantDataServletTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		putAllInCloudSearch(salamID);
	}
	
	private static void putAllInCloudSearch(String id) {
//		PostRestaurantDataServlet.putReviews(id);
//		PostRestaurantDataServlet.putRestaurant(id);
		PostRestaurantDataServlet.putDishes(id);		
	}

}
