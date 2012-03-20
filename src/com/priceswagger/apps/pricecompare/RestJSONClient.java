package com.priceswagger.apps.pricecompare;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class RestJSONClient {
	public String result_data = "";
	public JSONObject json;
	public JSONArray jsonArray;
	
	
	public void connect(String url)
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		// exec the request
		HttpResponse response;

		JSONObject json = new JSONObject();
		
		try {
				response = httpclient.execute(httpget);
				result_data = response.toString();
				HttpEntity entity = response.getEntity();

				
				if (entity != null) {
					InputStream instream = entity.getContent();
					String result = convertStreamToString(instream);
					result_data = result;
					//json = new JSONObject(result);
					try {
						json = new JSONObject(result_data);
						jsonArray = json.getJSONArray("items");
						//for (int i=0; i<array.length(); i++) {
						//	Log.v("fkkkk", array.getJSONObject(i).getString("prod").toString());
						//}
						
					} catch (JSONException e) {
						json = new JSONObject();
						jsonArray = new JSONArray();
						//Log.v("jsonerr", "Error jsonexception");
						//e.printStackTrace();
					}
					
					instream.close();
				}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result_data = "error clientprotocolexception";
		} catch (IOException e) {
			e.printStackTrace();
			result_data = "error_ioexception"; 
		}
		//return this.result_data;

	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


}

