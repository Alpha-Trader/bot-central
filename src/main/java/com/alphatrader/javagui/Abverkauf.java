package com.alphatrader.javagui;

import com.alphatrader.rest.Company;
import com.alphatrader.rest.Order;
import com.alphatrader.rest.Portfolio;
import com.alphatrader.rest.Position;
import com.mashape.unirest.http.HttpResponse;

public class Abverkauf {

	public void abverkaufen() {

		for (Company company : Company.getAllUserCompanies()) {
			String securitiesAccountId = company.getSecuritiesAccountId();
			Portfolio portfolio = Portfolio.getCompanyPortfolio(securitiesAccountId);
			for (Position position : portfolio.getPositions()) {
				marketSell(company, securitiesAccountId, position);
			}
		}

	}

	private void marketSell(Company company, String securitiesAccountId, Position position) {
		if (!position.getSecurityIdentifier().equals(company.getListing().getSecurityIdentifier())) {
			Long numberOfShares = position.getNumberOfShares();
			HttpResponse<String> response = Order.post(securitiesAccountId, position.getSecurityIdentifier(), "SELL",
					"MARKET", numberOfShares);
			System.out.println(response.getBody());
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new Abverkauf().abverkaufen();
	}

}
