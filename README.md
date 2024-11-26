# Weather Forecast (Compose version)
An application for displaying the weather forecast for a day and for three days.<br />

It is possible to display the forecast based on user location or user input. The default location is Minsk.<br />

The application requests permissions and checks for the Internet and GPS.<br />

![WeatherForecastComposeGen](https://github.com/user-attachments/assets/6e52d027-f389-4a46-ae9c-6ebe4dea5c7b)

## Technology stack used in development:<br />
* language - Kotlin<br />
* Single activity app
* UI - Compose <br />
* Architecture - Clean Architecture, UI layer - MVI<br />
* Navigation - View Pager <br />
* Dependency injection - Hilt<br />
* Asynchrony - Сoroutines and Kotlin Flow<br />
* Network - Retrofit2, Coil-compose, parser - GSON<br />
* Permission Internet, Fine_Location, Coars_Location, Access_Network_State <br />
* FusedLocationProviderClient, LocationManager<br />
