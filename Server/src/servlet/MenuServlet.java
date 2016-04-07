package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import smartMenu.Restaurant;

public class MenuServlet extends HttpServlet {

	public MenuServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		
		String id = (String) req.getParameter("rID");
		System.out.println(id);
		Restaurant restaurant = new Restaurant(id);
		req.setAttribute("restaurant", restaurant);
		res.setContentType("application/json");
		res.getWriter().write(restaurant.getSortedMenu().toJSONString());
	}

}

//filter vege..
