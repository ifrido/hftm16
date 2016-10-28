package ch.hftm.webservices.soap.client;

import java.util.Scanner;



public class SoapClient {

	private static Scanner scanner;
	
	
	public static void main(String[] args) {
		try {
			//YOUR CODE GOES HERE
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Client wird beendet");
			System.exit(0);
		}

		scanner = new Scanner(System.in);
		while (true) {
			System.out.println("1 convert from °C to F");
			System.out.println("2 convert from F to °C");
			System.out.println("0 Exit");
			System.out.print("> ");
			try {
				int action = Integer.parseInt(scanner.nextLine());
				if (action == 1) {
					convertCelsiusToFahrenheit();
				} else if (action == 2) {
					convertFahrenheitToCelsius();
				} else if (action == 0) {
					System.exit(0);
				}
			} catch (NumberFormatException e) {
				System.err.println("Invalid input");
			} catch (Exception e) {
				System.err.println("Unexpected system error");
			}
			System.out.println();
		}
	}

	
	private static void convertCelsiusToFahrenheit() throws Exception {
		System.out.print("Celsius:  ");
		String celsius = scanner.nextLine();
		try {
			//YOUR CODE GOES HERE
		} catch (Exception e) {
			System.out.println("Konvertierung fehlgeschlagen");
		}

	}

	
	
	private static void convertFahrenheitToCelsius() throws Exception {
		System.out.print("Fahrenheit:  ");
		String fahrenheit = scanner.nextLine();
		try {
			//YOUR CODE GOES HERE
		} catch (Exception e) {
			System.out.println("Konvertierung fehlgeschlagen");
		}
	}

}
