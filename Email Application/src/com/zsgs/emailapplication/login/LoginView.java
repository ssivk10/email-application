package com.zsgs.emailapplication.login;

import java.util.Scanner;

import com.zsgs.emailapplication.EmailMain;
import com.zsgs.emailapplication.admin.AdminView;
import com.zsgs.emailapplication.models.User;
import com.zsgs.emailapplication.user.UserView;

public class LoginView {

	Scanner sc = new Scanner(System.in);

	LoginModel loginModel;
	UserView userView;

	public LoginView() {
		this.loginModel = new LoginModel(this);
	}

	public void onInit() {
		System.out.println("------- Hello! Welcome to " + EmailMain.getInstance().getTitle() + " "
				+ EmailMain.getInstance().getVersion() + " -------");
		System.out.println(
				"\nWhat would you like to do?\n1. Log-in\n2. Sign-up\n3. Exit app\nEnter your choice: ");
		int x = sc.nextInt();
		sc.nextLine();
		switch (x) {
		case 1:
			login();
			break;
		case 2:
			signup();
			break;
		case 3:
			System.out.println("Thank you for using " + EmailMain.getInstance().getTitle()
					+ ", the new app for electronic mail transfer.\n\nCome again! :)");
			break;
		default:
			System.out.println("Invalid choice. Please enter a valid option: ");
			System.out.println("\n");
			onInit();
			break;
		}
	}

	public void login() {
		System.out.println("Enter your username: ");
		String username = sc.nextLine();
		if (loginModel.validate(username)) {
			System.out.println("Enter your password: ");
			String password = sc.nextLine();
			User user = loginModel.validate(username, password);
			if (user==null) {
				System.out.println("Invalid password.");
				onInit();
			}
			else {
				if(user.getId().equals("AD001")) {
					System.out.println("Admin login successful.");
					AdminView adminView = new AdminView(user);
					adminView.onInit();
				}
				else {
				System.out.println("Login successful.");
				userView = new UserView();
				userView.onInit(user);
				}
			}
		} else {
			System.out.println("Invalid username.");
			onInit();
		}
		
	}

	public void signup() {
		System.out.println("Enter your name: ");
		String name = sc.nextLine();
		String username;
		do {
			System.out.println("Tip: Username must start with an alphabet and must be atleast three letters long. It must not contain any spaces.");
			System.out.println("Set username: ");
			username = sc.nextLine();
			if (loginModel.validate(username))
				System.out.println("Username taken or invalid.");
		} while (loginModel.validate(username));
		String password, password1;
		do {
			System.out.println("Enter your password:  ");
			System.out.println("Tip: password must be a minimum of 8 characters long, and must include a number.");
			password = sc.nextLine();
			if(!loginModel.passwordValidation(password))
				System.out.println("Password must follow rules.");
		} while (!loginModel.passwordValidation(password));
		
		// only for fun don't include below in production
		
		String confirm;
		do {
			System.out.println("Confirm password: ");
			password1 = sc.nextLine();
			
			if(!password.equals(password))
				System.out.println("Passwords do not match.");
		} while (!password.equals(password1));
		do {
			System.out.println(
					"By typing yes, I agree to the terms and conditions of this app and consent for the app to store only necessary cookies to improve the performance of the app.");
			confirm = sc.nextLine();
		}while(!confirm.equalsIgnoreCase("Yes"));
		loginModel.signup(name, username, password);		
	}

	public void message(String msg) {
		System.out.println(msg);
	}

}
