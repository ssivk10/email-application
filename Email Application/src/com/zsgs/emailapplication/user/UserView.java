package com.zsgs.emailapplication.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.zsgs.emailapplication.datalayer.Database;
import com.zsgs.emailapplication.login.LoginView;
import com.zsgs.emailapplication.models.Email;
import com.zsgs.emailapplication.models.User;

public class UserView {

	Database database = Database.getInstance();
	Scanner sc = new Scanner(System.in);
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
	
	LoginView loginView;
	UserModel userModel;

	public UserView() {
		this.userModel = new UserModel(this);
	}

	public void onInit(User user) {
		System.out.println("\nHello user! ");
		System.out.println(
				"Choose an option: \n1. View your inbox\n2. Compose Email\n3. View/Edit Profile\n4. View sent history\n5. Deleted bin\n6. Log out\nEnter your choice: ");
		int ch;
		while (true) {
			try {
				ch = sc.nextInt();
				sc.nextLine();
				break;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Invalid input type. Please enter a valid input.");
				continue;
			}
		}
		switch (ch) {
		case 1:
			userModel.viewInbox(user);
			break;
		case 2:
			compose(user);
			break;
		case 3:
			profile(user);
			break;
		case 4:
			userModel.viewSent(user);
			break;
		case 5:
			userModel.viewDeleted(user);
			break;
		case 6:
			loginView = new LoginView();
			loginView.onInit();
			break;
		default:
			onInit(user);
			break;
		}
	}

	public void compose(User user) { // new email
		ArrayList<String> content = new ArrayList<>();
		System.out.println("Compose your email: ");
		String to;
		do {
			System.out.println("To: ");
			to = sc.nextLine();
			if(!userModel.validate(to))
				System.out.println("Username not found. Please enter a username that exists.");
		}while(!userModel.validate(to));
		System.out.println("Subject: ");
		String subject = sc.nextLine();
		System.out.println("Content: ");
		char ch = 'y';
		do {
			String c = sc.nextLine();
			content.add(c);
			System.out.println("Would you like to write another paragraph? y/n");
			ch = sc.nextLine().charAt(0);
		} while (ch == 'y' || ch == 'Y');
		System.out.println("Closing: ");
		String closing = sc.nextLine();
		System.out.println("Signature: ");
		String sign = sc.nextLine();
		userModel.compose(user, to, subject, content, closing, sign);
	}
	
	public void openEmail(User user, Email email) { //view email
		System.out.println("---------------------------------Email--------------------------------------");
		System.out.println();
		System.out.println("From: "+email.getFrom());
		System.out.println("Date: "+sdf.format(email.getDate()));
		System.out.println("To: "+email.getTo());
		System.out.println("Subject: "+email.getSubject()+"\n");
		for(String s:email.getContent()) {
			System.out.println(s+"\n");
		}
		System.out.println("\n"+email.getClosing());
		System.out.println(email.getSignature());
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("Would you like to delete this email? y/n");
		String ch = sc.nextLine();
		if(ch.equalsIgnoreCase("yes") || ch.contains("y")) {
			userModel.delete(user, email);
		}
		userModel.viewInbox(user);
	}

	public void alert(String msg) {
		System.out.println(msg);
	}
	
	public String getData() {
		String s = sc.nextLine();
		return s;
	}

	public void profile(User user) { //view profile
		System.out.println("------------------------------------------------------------");
		System.out.println("\nWelcome to your profile: "+user.getName()+".");
		System.out.println();
		System.out.println("Email ID: " + user.getUsername());
		System.out.println("\n----- Statistics -----\n");
		System.out.println(user.getReceived().size() + " emails in your inbox.");
		System.out.println(user.getSent().size() + " emails you have sent.");
		System.out.println(user.getDeleted().size() + " emails in your bin.\n");
		System.out.println("------------------------------------------------------------");
		System.out.println("\nChoose an option: \n1. Edit profile\n2. Main Menu\nEnter your choice:");
		int x = sc.nextInt();
		sc.nextLine();
		switch (x) {
		case 1:
			edit(user);
			break;
		case 2:
			onInit(user);
			break;
		default:
			System.out.println("Invalid choice. Please enter a valid choice.");
			profile(user);
			break;
		}
	}
	
	public void edit(User user) { //edit profile
		System.out.println("Which field would you like to edit?\n1. Edit Name\n2. Edit username\n3. Edit Password\n4. None of the above, go back ");
		int x = sc.nextInt();
		sc.nextLine();
		switch(x) {
		case 1:
			System.out.println("Enter new name: ");
			String name = sc.nextLine();
			userModel.editName(user, name);
			break;
		case 2:
			System.out.println("Enter new username: ");
			String username = sc.nextLine();
			userModel.editUsername(user, username);
			break;
		case 3:
			System.out.println("Enter password: ");
			String password = sc.nextLine();
			userModel.editPassword(user, password);
			break;
		case 4:
			profile(user);
			break;
		default:
			System.out.println("Invalid input. Please enter a valid input value.");
			break;
		}
	}
}
