package test;

import smartMenu.FoursquareHandler2;

public class FoursquareHandler2Test {

	private static String rID = "40a55d80f964a52020f31ee3";
	private static String lat = "40.6410459224843";
	private static String lon = "-74.01994849379909";
	
	public FoursquareHandler2Test() {
		// TODO Auto-generated constructor stub
	}

	
	public static void testGetReviewsByRestaurantID() {
		System.out.println(FoursquareHandler2.getReviewsByRestaurantID(rID).size());
	}
	
	public static void testGetMenuByRestaurantID() {
		System.out.println(FoursquareHandler2.getMenuByRestaurantID(rID));
	}
	
	public static void testGetRestaurantsByLocation() {
		System.out.println(FoursquareHandler2.getRestaurantsByLocation(lat, lon));
	}
	
	public static void main(String[] args) {
		//testGetReviewsByRestaurantID();
//		testGetMenuByRestaurantID();
		testGetRestaurantsByLocation();
	}
	
}
