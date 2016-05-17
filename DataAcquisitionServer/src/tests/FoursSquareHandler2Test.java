package tests;

import com.smartmenu.dataAcquisitionServer.FoursquareHandler2;

public class FoursSquareHandler2Test {
	
	private static final String restaurantID = "40a55d80f964a52020f31ee3";

	public FoursSquareHandler2Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(FoursquareHandler2.getRestaurantByID(PostRestaurantDataServletTest.goodID).toString());
		
	}

}
