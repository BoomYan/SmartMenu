package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartMenu.Restaurant;
import utils.ServletUtils;

public class ReviewServlet extends HttpServlet {

	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String dishName = (String) req.getParameter("dishName");
		if (dishName == null) {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		Restaurant restaurant = (Restaurant)req.getSession().getAttribute("restaurant");
		if (restaurant == null) {
			res.sendError(ServletUtils.BAD_REQUEST, "Need Restaurant ID");
			return;
		}
		ServletUtils.setHttpResponse(restaurant.getReviewsByDishName(dishName), res);
	}
	
}
