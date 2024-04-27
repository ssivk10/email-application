package com.zsgs.emailapplication.admin;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.zsgs.emailapplication.login.LoginView;
import com.zsgs.emailapplication.models.User;

public class AdminView {

	AdminModel adminModel;
	Scanner sc = new Scanner(System.in);
	User user;

	public AdminView(User user) {
		adminModel = new AdminModel(this, user);
		this.user = user;
	}

	public void onInit() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("Hello admin: " + user.getName());
		System.out.println(
				"\nWhat would you like to do? \n1. View all users\n2. Delete a user\n3. Log Out\nEnter your choice: ");
		int ch = 0;
		while (true) {
			try {
				ch = sc.nextInt();
				sc.nextLine();
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter an integer value.");
				continue;
			}
		}
		switch (ch) {
		case 1:
			adminModel.viewUsers();
			break;
		case 2:
			adminModel.delete();
			break;
		case 3:
			LoginView loginView = new LoginView();
			loginView.onInit();
			break;
		default:
			break;
		}
	}
	
	public String getData() {
		String s = sc.nextLine();
		return s;
	}
	
	public void alert(String msg) {
		System.out.println(msg);
	}

	public void viewUsers(User u) {
		System.out.println(u.getId() + " " + u.getName() + " " + u.getDate());
	}
}
