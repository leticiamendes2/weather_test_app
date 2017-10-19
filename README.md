This is an Android app that shows the current temperature of the devices' location and a 5 days forecast.
All weather data comes from http://openweathermap.org/api.

Implemented functionalities:

1) Current weather for a given location;
2) Weather for the next 5 days;
3) Information displayed: temperature, description, humidity, clouds, wind speed, wind degree, icons, min. temp. and max. temp.;
4) Master interface;
5) Displaying icons;
6) Using the phone’s location to detect the default city;
7) Searching city by latitude/longitude;
8) Changing temperature unit when touching the current temperature;
9) Changing background color and text color according to the weather conditions codes;

Architecture: MVP
Framkeworks: Retrofit, GSON, ButterKnife, Glide, Espresso, UiAutomator

Bugs:
1) Test "b_titleCityName", called twice: 1 OK, 2º Error

Future implementations:
1) Layout portrait orientation;
2) State restoration;
3) City search;
4) Save results for 10 minutes;
5) Favourite cities;
6) Expandable list view with details of each day;
7) Graph showing temperature oscilation during a day;
8) Notification tomorrow's weather;
