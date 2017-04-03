package com.alphatrader.javagui;

import java.util.List;

import com.alphatrader.rest.Listing;

public class ListingShow {
	int a;

	public void getListings() {
		List<Listing> listings = Listing.getAllListings();
		for (Listing listing : listings) {
			a++;
			System.out.println("Listing" + listing + a);
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new ListingShow().getListings();
	}
}
