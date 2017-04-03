package com.alphatrader.javagui;

import java.util.List;

import com.alphatrader.rest.Bond;

public class Bondbuy {
	int a;

	public void getBonds() {

		List<Bond> bonds = Bond.getAllBonds();
		for (Bond bond : bonds) {
			a++;
			System.out.println("Bond" + bond + a);

		}

	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new Bondbuy().getBonds();
	}

}
