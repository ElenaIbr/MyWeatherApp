# MyWeatherApp

I created a small application to get current weather by coordinates.

Structure (created folders but let's imagine that i created modules ;)):
- app and di: Hilt (created modules for remote, storage and separate module for usecases)
- remote: Retrofit (i also added interceptor to provide api key instead of passing it for each API call)
- storage: DataStore (added storage layer to display last data if there is no remote data)
- ui: Jetpack Compose, Coil to load images by URL


How it works: we get 52.364138 4.891697 weather by default and there is option to input new coordinates and update it. I did it for the video. As you can see the description and the address were changed. I checked with Postman that the icon should be the same - 10d :)

https://github.com/ElenaIbr/MyWeatherApp/assets/87421176/e6ada6f6-5309-487b-878c-ca7a18051143








