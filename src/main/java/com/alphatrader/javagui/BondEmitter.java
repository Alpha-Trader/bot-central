/**
 * 
 */
package com.alphatrader.javagui;

import java.util.Date;
import java.util.List;

import com.alphatrader.rest.Bond;
import com.alphatrader.rest.Company;
import com.mashape.unirest.http.HttpResponse;

/**
 * @author User
 *
 */
public class BondEmitter {

	public void emittBonds() {
		List<Company> companies = Company.getAllUserCompanies();
		for (Company company : companies) {
			{
				// Date maturityDate = null ;
				// int date = (int) (maturityDate.getTime() * 1000);
				int i = 1;
				while (i == 1) {
					Date now = new Date();
					Long longTime = new Long(now.getTime() / 1000);
					Long date = longTime + 72 * 3600;
					String datum = String.valueOf(date);
					datum = datum + "000";
					HttpResponse<String> response = Bond.postBond(company.getId(), "100", "100", "2.0", datum);
					System.out.println(response.getBody());
				}
			}
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new BondEmitter().emittBonds();
	}
}
