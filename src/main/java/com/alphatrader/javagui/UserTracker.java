package com.alphatrader.javagui;

import static com.alphatrader.rest.Event.Type.NEW_USER;

import java.util.List;

import com.alphatrader.rest.Event;

public class UserTracker {

	public void listTrackedUser() {
		List<Event> events = Event.getAllEvents();
		for (Event event : events) {
			if (event.getType() == NEW_USER) {
				System.out.println("Event: " + event);
			}
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGame();

		UserTracker list = new UserTracker();
		list.listTrackedUser();
	}

}
