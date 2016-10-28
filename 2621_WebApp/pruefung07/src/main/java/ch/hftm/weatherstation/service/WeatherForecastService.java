package ch.hftm.weatherstation.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import ch.hftm.weatherstation.model.Forecast;

@Stateless
@WebService (name="weatherforecast")
public class WeatherForecastService {
	
	@EJB Forecast forecast;
		
	@WebMethod(operationName = "forecastByCity")
    @WebResult(name = "forecast")
	public Forecast getForecast(@WebParam(name = "city") String city) {
		forecast.setCity(city);
		return forecast;
	}
}
