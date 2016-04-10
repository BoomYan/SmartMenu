package test;

import smartMenu.Restaurant;

public class RestaurantTest {
	private static String rID = "40a55d80f964a52020f31ee3";

	private static void testGetSortedMenu(Restaurant r) {
		System.out.println(r.getSortedMenu());
	}
	
	public static void testGetReviewsByDishName(Restaurant r) {
		System.out.println(r.getReviewsByDishName("Pancakes"));
	}
	
	public static void main(String[] args) {
		Restaurant r = Restaurant.constructRestaurant(rID);
		testGetSortedMenu(r);
		//testGetReviewsByDishName(r);
	}

}
