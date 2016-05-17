package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import smartMenu.Restaurant;
import smartMenu.SmartMenuElasticSearchHandler;
import utils.ServletUtils;

public class ReviewServlet extends HttpServlet {

	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String dishID = req.getParameter("dishID");
		String userID = req.getParameter("userID");
		if (dishID == null) {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		Restaurant restaurant = (Restaurant)req.getSession().getAttribute("restaurant");
		if (restaurant == null) {
			try {
				//trying to recover the restaurant
				JSONObject dish = SmartMenuElasticSearchHandler.getDishByDishID(dishID);
				String restaurantID = dish.getString("restaurantID");
				restaurant = Restaurant.constructRestaurant(restaurantID, userID);
				req.getSession().setAttribute("restaurant", restaurant);
			} catch (JSONException e) {
				e.printStackTrace();
				res.sendError(ServletUtils.BAD_REQUEST, "Need Restaurant ID");
				return;
			}

		}
		JSONObject responseObject = new JSONObject();
		try {
			ServletUtils.setHttpResponse(responseObject.put("reviews", restaurant.getReviewsByDishName(dishID)), res);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
