package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartMenu.Restaurant;
import utils.ServletUtils;

public class MenuServlet extends HttpServlet {
	
	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String id = (String) req.getParameter("rID");
		if (id == null || id == "") {
			res.setStatus(ServletUtils.BAD_REQUEST);
			return;
		}
		Restaurant restaurant = (Restaurant)this.getServletConfig().getServletContext().getAttribute(id);
		if (restaurant == null) {
			restaurant = Restaurant.constructRestaurant(id);
			if (restaurant == null) {
				res.setStatus(ServletUtils.BAD_REQUEST);
				return;
			}
			this.getServletConfig().getServletContext().setAttribute(id, restaurant);
		}
		req.getSession().setAttribute("restaurant", restaurant);
		ServletUtils.setHttpResponse(restaurant.getSortedMenu(), res);
	}

}