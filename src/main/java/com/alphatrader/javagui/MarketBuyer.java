package com.alphatrader.javagui;

import java.util.List;

import com.alphatrader.rest.Company;
import com.alphatrader.rest.Listing;
import com.alphatrader.rest.Order;
import com.mashape.unirest.http.HttpResponse;

public class MarketBuyer {
	long a = 1;

	public void BuyAll() {
		List<Company> companies = Company.getAllUserCompanies();
		for (Company company : companies) {
			List<Listing> listings = Listing.getAllListings();
			buyListings(company, a, listings);
		}

	}

	private void buyListings(Company company, Long a, List<Listing> listings) {

		for (Listing listing : listings) {
			if (listing.getSecurityIdentifier() != company.getListing().getSecurityIdentifier()) {
				Long numberOfShares = a;
				HttpResponse<String> response = Order.post(company.getSecuritiesAccountId(),
						listing.getSecurityIdentifier(), "BUY", "MARKET", numberOfShares);
				System.out.println(response.getBody());

			}
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new MarketBuyer().BuyAll();
	}

}
