package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import smartMenu.SmartMenuElasticSearchHandler;
import utils.ServletUtils;

public class RestaurantServlet extends HttpServlet {

	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String location = (String) req.getParameter("location");
		if (location == null) {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		JSONArray restaurants = new JSONArray();
		if (location != null) {
			String[] latlng = location.split(",");
			restaurants = SmartMenuElasticSearchHandler.searchRestaurantsByLocation(latlng[0], latlng[1]);
		}
		JSONObject responseObject = new JSONObject();
		try {
			ServletUtils.setHttpResponse(responseObject.put("restaurants", restaurants), res);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}
