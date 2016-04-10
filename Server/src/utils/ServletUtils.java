package utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

public class ServletUtils {
	
	public static final int BAD_REQUEST = 400;
	public static final int NOT_FOUND = 404;
	
	public static void setHttpResponse(JSONArray array, HttpServletResponse res) throws IOException {
		if (array == null || array.size() == 0) {
			res.setStatus(NOT_FOUND);
			return;
		}
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(array.toString());
	}

}
