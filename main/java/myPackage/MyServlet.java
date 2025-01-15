package myPackage;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String inputData = request.getParameter("userInput");
//		System.out.println(inputData);
//		doGet(request, response);
		
		//API setup
		String apiKey = "5c019651709a5294c70279742e01773c";
		
		//get the city name from user input
		String city = request.getParameter("city").toUpperCase();
		String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
		//create the URL for the OpenWeatherMap API request
		String apiURL = "https://api.openweathermap.org/data/2.5/weather?q="+ encodedCity +"&appid="+ apiKey;
	
		try {
			//API integration
			URL url = new URL(apiURL);
			
			//to establish a connection of type Http
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//get request 
			connection.setRequestMethod("GET");
			
			//to read the data (stream by stream)
			InputStream inputStream = connection.getInputStream();
			//read the data stream
			InputStreamReader reader = new InputStreamReader(inputStream);
			
			//to store the data - using StringBuilder because it is mutable
			StringBuilder responseContent = new StringBuilder();
			
			//input from the reader, will create the scanner object
			//scanning the data
			Scanner scanner = new Scanner(reader);
			
			//runs till the end of line
			while(scanner.hasNext()) {
				responseContent.append(scanner.nextLine());
			}
			
			scanner.close();
			//System.out.println(responseContent);
			
			//type casting - parsing the data into JSON
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
//			System.out.println(jsonObject);
			
			//Date & Time
	        long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
	        String date = new Date(dateTimestamp).toString();
	        
	        //Temperature
	        double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
	        int temperatureCelsius = (int) (temperatureKelvin - 273.15);
	       
	        //Humidity
	        int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
	        
	        //Wind Speed
	        double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
	        
	        //Weather Condition
	        String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
	        
	     // Set the data as request attributes (for sending to the jsp page)
	        request.setAttribute("date", date);
	        request.setAttribute("city", city);
	        request.setAttribute("temperature", temperatureCelsius);
	        request.setAttribute("weatherCondition", weatherCondition); 
	        request.setAttribute("humidity", humidity);    
	        request.setAttribute("windSpeed", windSpeed);
	        request.setAttribute("weatherData", responseContent.toString()); 
	        
	        connection.disconnect();
		}
		catch(IOException e){
			e.printStackTrace();
		}
        
     // Forward the request to the weather.jsp page for rendering
        request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
