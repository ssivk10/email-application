package com.zsgs.emailapplication.login;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import com.zsgs.emailapplication.admin.AdminView;
import com.zsgs.emailapplication.datalayer.Database;
import com.zsgs.emailapplication.models.Email;
import com.zsgs.emailapplication.models.User;
import com.zsgs.emailapplication.user.UserView;

public class LoginModel {
	LoginView loginView;
	UserView userView;;
	Database database = Database.getInstance();

	public LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}

	/*
	 * public boolean validateUnique(String username) { boolean chk = false; for
	 * (User user : Database.getUsers()) { if (user.getUsername().equals(username))
	 * { chk = true; } } return chk; }
	 */
	
	public void signup(String name, String username, String password) {
		User user = new User();
		String id = "U0"+(int)(database.getUsers().size()+1);
		user.setId(id);
		user.setName(name);
		user.setUsername(username);
		user.setPassword(password);
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");    
		user.setDate(System.currentTimeMillis());
		Date d = new Date(user.getDate());
		database.addUsers(user);
		loginView.message("Signup successful!");
		Email email = new Email();
		email.setId("E01");
		email.setFrom("IMail");
		email.setTo(username);
		email.setSubject("Welcome!");
		ArrayList<String> content = new ArrayList<String>();
		content.add("Hello!");
		content.add("Welcome to IMail, where you can communicate with anyone instantly.");
		content.add("We hope you like your experience here. ");
		email.setContent(content);
		email.setClosing("Regards");
		email.setSignature("IMail Team");
		email.setDate(System.currentTimeMillis());
		user.getReceived().add(email);
		database.setUsers();
		userView = new UserView();
		userView.onInit(user);
	}

	public boolean validate(String username) {
		if(username.equals("admin")) return true;
		String pattern = "^[A-Za-z]\\w{2,}$";
		boolean chk = false;
		for (User user : database.getUsers()) {
			if (user.getUsername().equals(username)) {
				chk = true;
			}
		}
		return chk;
	}

	public User validate(String username, String password) {
		if(username.equals("admin") && password.equals("pw@1")) {
			User user = new User();
			user.setId("AD001");
			user.setName("Sid");
			user.setUsername(username);
			user.setPassword(password);
			return user;
		}
		boolean chk = false;
		for (User user : database.getUsers()) {
			if (user.getUsername().equals(username)) {
				if (user.getPassword().equals(password))
					return user;
				return null;
			}
		}
		return null;
	}
	
	public boolean passwordValidation(String pw1) {
		return Pattern.matches("[a-zA-z]\\w{3,}$", pw1);
	}
}
