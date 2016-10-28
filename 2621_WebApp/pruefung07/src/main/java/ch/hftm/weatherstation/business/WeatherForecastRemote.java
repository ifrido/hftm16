package ch.hftm.weatherstation.business;

import javax.ejb.Local;

@Local
public interface WeatherForecastRemote {
	public void writeWeatherReport(String city, int temp);
}
