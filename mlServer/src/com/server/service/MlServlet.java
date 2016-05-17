package com.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.IOUtils;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class MlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static S3Handler handler = new S3Handler();
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String reqString = IOUtils.toString(req.getInputStream());
//		try {
////			JSONObject json = new JSONObject(reqString);
////			String userID = json.getString("userID");
////			JSONArray dishes = json.getJSONArray("dishes");
//			// 1. write userID and dishID into s3.
//			System.out.println(req);
//			// 2. 
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String reqString = IOUtils.toString(req.getInputStream());
		try {
			JSONObject json = new JSONObject(reqString);
			int userID = json.getInt("userID");
			System.out.println(userID);
			JSONArray dishes = json.getJSONArray("dishes");
			for (int i = 0; i < dishes.length(); i++) {
				int menuID = dishes.getJSONObject(i).getInt("menuID");
			}
			
			JSONObject result = readContents();
			resp.setContentType("application/json");
			resp.getWriter().write(result.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JSONObject readContents() {
		InputStream ls = handler.download();
		JSONObject json = new JSONObject();
		JSONArray dishArray = new JSONArray();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ls, "UTF-8"),512);
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				// deal with rating file
				String res = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				String[] predict = res.split(",");
				
				
				JSONObject dish = new JSONObject();
				dish.put("dishID", predict[1]);
				dish.put("rating", predict[2]);
				dishArray.put(dish);
			}
			json.put("dishes", dishArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return json;
		}
		return json;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
