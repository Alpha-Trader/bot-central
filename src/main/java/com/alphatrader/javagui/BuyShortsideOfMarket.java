
package com.alphatrader.javagui;

import java.util.List;

import com.alphatrader.rest.Company;
import com.alphatrader.rest.Listing;
import com.alphatrader.rest.Order;
import com.alphatrader.rest.PriceSpread;
import com.mashape.unirest.http.HttpResponse;

public class BuyShortsideOfMarket {

	long a = 1;

	public void BuyShort() {
		List<Company> companies = Company.getAllUserCompanies();
		for (Company company : companies) {
			List<Listing> listings = Listing.getAllListings();
			buyShortside(company, a, listings);
		}

	}

	private void buyShortside(Company company, Long a, List<Listing> listings) {

		for (Listing listing : listings) {
			if (listing.getSecurityIdentifier() != company.getListing().getSecurityIdentifier()) {
				PriceSpread pricespread = PriceSpread.getPriceSpread(listing.getSecurityIdentifier());
				a = pricespread.getAskSize();

				Long numberOfShares = a;
				HttpResponse<String> response = Order.post(company.getSecuritiesAccountId(),
						listing.getSecurityIdentifier(), "BUY", "MARKET", numberOfShares);
				System.out.println(response.getBody());

			}
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();

	}

}
