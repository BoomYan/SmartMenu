package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import smartMenu.FoursquareHandler2;
import utils.ServletUtils;

public class RestaurantServlet extends HttpServlet {

	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String sw = (String) req.getParameter("sw");
		String ne = (String) req.getParameter("ne");
		String ll = (String) req.getParameter("ll");
		if ((sw == null || ne == null) && ll == null) {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		JSONArray restaurants = new JSONArray();
		if (sw != null && ne != null) {
			restaurants = FoursquareHandler2.getRestaurantsByBounds(sw, ne);
		}
		else if (ll != null) {
			restaurants = FoursquareHandler2.getRestaurantsByLocation(ll);	
		}
		ServletUtils.setHttpResponse(restaurants, res);
	}
	
}
