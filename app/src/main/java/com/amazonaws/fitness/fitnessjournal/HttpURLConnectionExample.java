package com.amazonaws.fitness.fitnessjournal;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";


	// HTTP GET request
	public String sendGet() throws Exception {

		String url = "https://7mbivmda6c.execute-api.us-west-2.amazonaws.com/prod/bodypartresource?partname=Chest";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;

		inputLine = in.readLine();

		in.close();
		
		//print result
		return inputLine;
	}
}