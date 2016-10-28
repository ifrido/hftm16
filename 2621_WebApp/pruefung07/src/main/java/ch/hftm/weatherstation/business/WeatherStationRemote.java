package ch.hftm.weatherstation.business;

import javax.ejb.Remote;

@Remote
public interface WeatherStationRemote {
	public void calculateWeatherData();
}
