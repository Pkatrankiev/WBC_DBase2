package Aplication;

	import java.io.*;
	import java.net.*;
	import java.util.*;


	public class NewPosPHPTest {
	    public static void main(String[] args) throws Exception {
	    	
//	*****************************************    	
//	    	String json = 			Jsoup.connect("https://accuweatherkozlodui.000webhostapp.com/ReadFromDbase_WeatherDataBaseESP22.php").ignoreContentType(true).execute().body();
//			System.out.print(json);
	        
//	  **********************************************  	
	    	
	    	
	    	
//	+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	        URL url = new URL("https://accuweatherkozlodui.000webhostapp.com/ReadFromDbase_WeatherDataBaseESP22.php");
	        Map<String,Object> params = new LinkedHashMap<>();
	        params.put("tableName", "weatherDataBaseESP");
	       	
	        StringBuilder postData = new StringBuilder();
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (postData.length() != 0) postData.append('&');
	            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
	            postData.append('=');
	            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
	        }
	        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//	        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes);

//	       InputStream ee = conn.getInputStream();
//	       ee.
	        
//	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

	    	BufferedReader br = new BufferedReader(
			  new InputStreamReader(conn.getInputStream(), "utf-8")); 
			    StringBuilder response = new StringBuilder();
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
//			    	responseLine = responseLine.replace("[", "{").replace("]", "}");
			        response.append(responseLine.trim());
			    }
			    System.out.println(response.toString());
				       
//	        JSONArray myResponse = new JSONArray (response);
	        
	        
//	        System.out.println(myResponse);
	        
	       
//	    	+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
//	    	 URL url = new URL("https://accuweatherkozlodui.000webhostapp.com/ReadFromDbase_WeatherDataBaseESP22.php");
//	    	 HttpURLConnection con = (HttpURLConnection)url.openConnection();
//	    	 con.setRequestMethod("POST");
//	    	 con.setRequestProperty("Content-Type", "application/json");
//	    	 con.setRequestProperty("Accept", "application/json");
//	    	 con.setDoOutput(true);
//	    	 String jsonInputString = "{tableName: weatherDataBaseESP, tableName: weatherDataBaseESP}";
//	    	 try(OutputStream os = con.getOutputStream()) {
//	    		    byte[] input = jsonInputString.getBytes("utf-8");
//	    		    os.write(input, 0, input.length);			
//	    		}
//	    	 
//	    	 try(BufferedReader br = new BufferedReader(
//	    			  new InputStreamReader(con.getInputStream(), "utf-8"))) {
//	    			    StringBuilder response = new StringBuilder();
//	    			    String responseLine = null;
//	    			    while ((responseLine = br.readLine()) != null) {
//	    			        response.append(responseLine.trim());
//	    			    }
//	    			    System.out.println(response.toString());
//	    			}
	    	 
	    	 
	    	 
	    }
	}