package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartMenu.Restaurant;

public class MenuServlet extends HttpServlet {
	
	//default id
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String id = (String) req.getParameter("rID");
		System.out.println(id);
		if (id == null) {
			return;
		}
		Restaurant restaurant = (Restaurant)this.getServletConfig().getServletContext().getAttribute(id);
		if (restaurant == null) {
			restaurant = new Restaurant(id);
			this.getServletConfig().getServletContext().setAttribute(id, restaurant);
		}
		req.getSession().setAttribute("restaurant", restaurant);
		res.setContentType("application/json");
		res.getWriter().write(restaurant.getSortedMenu().toString());
	}

}

