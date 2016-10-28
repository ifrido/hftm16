package ch.hftm.notenrechner.client;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class SchedunlingClient {

	
	private static Scanner scanner;
	private static Client c = ClientBuilder.newClient();
	
	public static void main(String[] args) {
		
		

		scanner = new Scanner(System.in);
		while (true) {
			System.out.println("0 Test Service");
			System.out.println("1 set Interval Timer");
			System.out.println("2 cancel all Timers");
			System.out.println("9 Exit");
			System.out.print("> ");
			try {
				int action = Integer.parseInt(scanner.nextLine());
				if (action == 0) {
					testService();
				} else if (action == 1) {
					setIntervalTimer();
				} else if (action == 2) {
					cancelAllTimers();
				} else if (action == 9) {
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

	private static void testService() {
		System.out.println(c.target("http://localhost:8080/notenRechner07/scheduling/testService").request().get().readEntity(String.class));
	}

	private static void setIntervalTimer() {
		System.out.print("Interval:  ");
		long interval = Long.parseLong(scanner.nextLine());
		System.out.println(c.target("http://localhost:8080/notenRechner07/scheduling/createNew").queryParam("interval", interval).request().get().getStatus());
	}

	private static void cancelAllTimers() {
		System.out.println(c.target("http://localhost:8080/notenRechner07/scheduling/cancelAll").request().get().getStatus());
	}

}
