package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartMenu.Restaurant;

public class ReviewServlet extends HttpServlet {

	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String dishName = (String) req.getParameter("dishName");
		System.out.println(dishName);
		if (dishName == null) {
			return;
		}
		Restaurant restaurant = (Restaurant)req.getAttribute("restaurant");
		if (restaurant == null) {
			//TODO send back info to tell user step back
			return;
		}
		res.setContentType("application/json");
		res.getWriter().write(restaurant.getReviewsByDishName(dishName).toString());
	}
	
}
