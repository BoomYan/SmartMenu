package utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;


public class ServletUtils {
	
	public static final int BAD_REQUEST = 400;
	public static final int NOT_FOUND = 404;
	
	public static void setHttpResponse(JSONObject obj, HttpServletResponse res) throws IOException {
		if (obj == null || obj.length() == 0) {
			res.setStatus(NOT_FOUND);
			return;
		}
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(obj.toString());
	}

}
