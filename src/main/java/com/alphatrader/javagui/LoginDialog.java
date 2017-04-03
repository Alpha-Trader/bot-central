package com.alphatrader.javagui;

import java.net.MalformedURLException;
import java.net.URL;

import com.alphatrader.rest.User;
import com.alphatrader.rest.util.ApiLibConfig;

public class LoginDialog {

	public void loginGame() {

		ApiLibConfig config = ApiLibConfig.getInstance();
		try {
			config.setApiUrl(new URL(AppState.getInstance().getApiUrl()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		config.setPartnerId("e1d149fb-0b2a-4cf5-9ef7-17749bf9d144");
		User user = new User("Alain", "lollies");
		user.login();
		config.setUser(user);

	}

	public void loginGameSteer() {

		ApiLibConfig config = ApiLibConfig.getInstance();
		try {
			config.setApiUrl(new URL(AppState.getInstance().getApiUrl()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		config.setPartnerId("e1d149fb-0b2a-4cf5-9ef7-17749bf9d144");
		User user = new User("Steerforth", "lollies");
		user.login();
		config.setUser(user);

	}
}
