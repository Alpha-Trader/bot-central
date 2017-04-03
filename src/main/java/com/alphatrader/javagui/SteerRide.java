package com.alphatrader.javagui;

public class SteerRide {

	int i = 1;

	public void rideSteer() {
		while (i == 1) {
			new BuyShortsideOfMarket().BuyShort();
			new BondEmitter().emittBonds();
		}
	}

	public static void main(String[] args) {
		new LoginDialog().loginGameSteer();
		new SteerRide().rideSteer();

	}
}
