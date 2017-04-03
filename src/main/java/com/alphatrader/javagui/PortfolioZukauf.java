package com.alphatrader.javagui;

import com.alphatrader.rest.Company;
import com.alphatrader.rest.Order;
import com.alphatrader.rest.Portfolio;
import com.alphatrader.rest.Position;
import com.mashape.unirest.http.HttpResponse;

public class PortfolioZukauf {

	public void zukaufen() {
		for (Company company : Company.getAllUserCompanies()) {
			String securitiesAccountId = company.getSecuritiesAccountId();
			Portfolio portfolio = Portfolio.getCompanyPortfolio(securitiesAccountId);
			for (Position position : portfolio.getPositions())
				marketBuy(company, securitiesAccountId, position);
		}

	}

	private void marketBuy(Company company, String securitiesAccountId, Position position) {
		if (!position.getSecurityIdentifier().equals(company.getListing().getSecurityIdentifier())) {
			Long numberOfShares = position.getNumberOfShares();
			HttpResponse<String> response = Order.post(securitiesAccountId, position.getSecurityIdentifier(), "BUY",
					"MARKET", numberOfShares);
			System.out.println(response.getBody());

		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new PortfolioZukauf().zukaufen();
	}

}
