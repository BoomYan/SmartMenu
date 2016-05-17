package com.smartmenu.dataAcquisitionServer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

public class PostRestaurantDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String restaurantID = (String) req.getParameter("restaurantID");
		putReviews(restaurantID);
		putRestaurant(restaurantID);
		putDishes(restaurantID);
	}

	public static void putReviews(String restaurantID) {
		JSONArray reviews = FoursquareHandler2.getReviewsByRestaurantID(restaurantID);
		SmartMenuElasticSearchHandler.putReviews(reviews);
	}

	public static void putRestaurant(String restaurantID) {
		JSONObject restaurant = FoursquareHandler2.getRestaurantByID(restaurantID);
		SmartMenuElasticSearchHandler.putRestaurant(restaurant);
	}

	public static void putDishes(String restaurantID) {
		JSONArray dishes = FoursquareHandler2.getDishesByRestaurantID(restaurantID);
		SmartMenuElasticSearchHandler.putDishes(dishes);
	}
	

}
