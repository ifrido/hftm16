package ch.hftm.weatherstation.business;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton (name="WeatherStation")
public class WeatherStationBean implements WeatherStationRemote {

	@Schedule(dayOfWeek="Mon,Wed,Sat", second="*/20")
	public void calculateWeatherData() {
		System.out.println("calculateWeatherData");
	}

}
