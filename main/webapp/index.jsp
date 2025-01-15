<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather App</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="card">
        <div class="search">
            <form action="MyServlet" method="post">
                <input type="text" name="city" id="" placeholder="Enter city name" spellcheck="false">
                <button><img src="images/search.png"></button>
            </form>
        </div>
        <div class="weather">
            <img src="images/rain.png" class="weather-icon" id="weather-icon">
            <h3 class="text">${weatherCondition}</h3>
            <h1 class="temp">${temperature}°C</h1>
            <input type="hidden" id="wc" value="${weatherCondition}">
            <h2 class="city">${city}</h2>
            <div class="details">
                <div class="col">
                    <img src="images/humidity.png">
                    <div>
                        <p class="text">${humidity}%</p>
                        <p>Humidity</p>
                    </div>
                </div>
                <div class="col">
                    <img src="images/wind.png">
                    <div>
                        <p class="text">${windSpeed} km/h</p>
                        <p>Wind Speed</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="myScript.js"></script>
</body>
</html>