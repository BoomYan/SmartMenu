package test;

import smartMenu.Restaurant;

public class RestaurantTest {
	private static String rID = "40a55d80f964a52020f31ee3";
	static final String goodID = "45b9c8c1f964a520d7411fe3";
	static final String userID = "001";

	private static void testGetSortedMenu(Restaurant r) {
		System.out.println(r.getDishes());
	}
	
	public static void testGetReviewsByDishName(Restaurant r) {
		System.out.println(r.getReviewsByDishName("Pancakes"));
	}
	
	public static void main(String[] args) {
		Restaurant r = Restaurant.constructRestaurant(goodID, userID);
		System.out.println(r.getDishes().toString());
//		testGetSortedMenu(r);
		//testGetReviewsByDishName(r);
	}

}
