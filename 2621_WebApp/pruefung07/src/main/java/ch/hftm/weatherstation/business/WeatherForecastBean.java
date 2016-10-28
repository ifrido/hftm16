package ch.hftm.weatherstation.business;

import javax.annotation.Resource;
import javax.ejb.Stateful;

@Stateful (name="WeatherForecastBean")
public class WeatherForecastBean implements WeatherForecastRemote{
	
	public String location;
	public String author;
	@Resource(name="temperatureUnit")
	private String temperatureUnit;
	
	public void writeWeatherReport(String city, int temp) {
		System.out.println("writeWeatherReport");
	}

}
