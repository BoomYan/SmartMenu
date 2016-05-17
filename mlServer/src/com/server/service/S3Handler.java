package com.server.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

public class S3Handler {
	
	private static String accessKey = "AKIAJEGWABLP254VT75Q";
	private static String secretKey = "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";


	
	public  InputStream download() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3Client s3 = new AmazonS3Client(credentials);
		List<Bucket> buckets = s3.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName());
		}
		S3Object object = s3.getObject(new GetObjectRequest("music-data-test", "predictions_1000002/part-00000"));
		InputStream objectData = object.getObjectContent();
		return objectData;
		//read contents from s3 file
		
	}
	
//	public static void readContents() {
//		InputStream ls = download();
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(ls, "UTF-8"),512);
//			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//				// deal with rating file
//				String res = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
//				String[] predict = res.split(",");
//				
//				JSONObject json = new JSONObject();
//				JSONArray dishArray = new JSONArray();
//				JSONObject dish = new JSONObject();
//				dish.put("dishID", predict[1]);
//				dish.put("rating", predict[2]);
//				dishArray.put(dish);
//				json.put("dishes", dishArray);
//				System.out.println(json.toString());
//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) {
//		readContents();
//	}
	
}
