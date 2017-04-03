package com.alphatrader.javagui;

import static com.alphatrader.rest.Event.Type.CASH_OUT_POLL_INITIATED;
import static com.alphatrader.rest.Event.Type.CEO_DISMISSED;
import static com.alphatrader.rest.Event.Type.CEO_EMPLOYED;
import static com.alphatrader.rest.Event.Type.COMPANY_LIQUIDATED;
import static com.alphatrader.rest.Event.Type.NEW_SECURITY;
import static com.alphatrader.rest.Event.Type.NEW_USER;
import static com.alphatrader.rest.Event.Type.ORDER_ACCEPTED;

import java.util.List;

import com.alphatrader.rest.Event;
import com.alphatrader.rest.Event.Type;

public class EventList {

	public void listNewUser() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == NEW_USER) {
				System.out.println("Event: " + event);
			}
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGame();

		new EventList().listTrades();
	}

	public void listNewOrders() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == ORDER_ACCEPTED) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listDismissedCEOs() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == CEO_DISMISSED) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listEmployedCEOs() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == CEO_EMPLOYED) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listCashOutPolls() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == CASH_OUT_POLL_INITIATED) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listLiquidatedCompanies() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == COMPANY_LIQUIDATED) {
				System.out.println("Event: " + event);
			}
		}

	}

	public void listNewSecurities() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == NEW_SECURITY) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listNewCompanies() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == Type.NEW_COMPANY) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listDeletedOrders() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == Type.ORDER_DELETED) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listFilledOrders() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == Type.ORDER_FILLED) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listPaidSalaries() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == Type.SALARY_PAYMENT) {
				System.out.println("Event: " + event);
			}
		}
	}

	public void listTrades() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == Type.SECURITY_TRADED) {
				System.out.println("Event:" + event);
			}
		}
	}
}