package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import smartMenu.Restaurant;
import utils.ServletUtils;

public class MenuServlet extends HttpServlet {

	// default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		String restaurantID = (String) req.getParameter("restaurantID");
		String userID = (String) req.getParameter("userID");
		if (restaurantID == null || restaurantID == "") {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		Restaurant restaurant = Restaurant.constructRestaurant(restaurantID, userID);
		if (restaurant == null) {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		req.getSession().setAttribute("restaurant", restaurant);
		JSONObject responseObject = new JSONObject();
		try {
			responseObject.put("dishes", restaurant.getDishes());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ServletUtils.setHttpResponse(responseObject, res);
	}

}