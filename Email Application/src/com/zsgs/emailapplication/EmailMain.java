package com.zsgs.emailapplication;

import com.zsgs.emailapplication.login.LoginView;

public class EmailMain {
	private static EmailMain emailMain;
	private static LoginView loginView;
	
	private EmailMain() {
		
	}
	
	public static EmailMain getInstance() {
		if(emailMain==null)
			emailMain = new EmailMain();
		return emailMain;
	}
	
	public String getVersion() {
		return "v 1.0.0";
	}
	
	public String getTitle() {
		return "IMail";
	}

	public static void main(String[] args) {
		loginView = new LoginView();
		loginView.onInit();
	}

}
