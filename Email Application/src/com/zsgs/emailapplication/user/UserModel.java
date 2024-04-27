package com.zsgs.emailapplication.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.zsgs.emailapplication.datalayer.Database;
import com.zsgs.emailapplication.models.Email;
import com.zsgs.emailapplication.models.User;

public class UserModel {

	UserView userView;
	Database database = Database.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

	public UserModel(UserView userView) {
		this.userView = userView;
	}

	public void viewInbox(User user) { // display all received emails
		if (user.getReceived().size() != 0) {
			userView.alert("\t\t\t\t\t\t\t" + user.getReceived().size() + " Emails");
			for (int i = 0; i < user.getReceived().size(); i++) {
				Email e = user.getReceived().get(i);
				Date d = new Date(e.getDate());
				userView.alert("----------------------------------------------------------------");
				userView.alert(e.getId() + " " + e.getFrom() + "\t\t\t" + e.getSubject() + " " + e.getContent().get(0)
						+ "  " + sdf.format(d));
				userView.alert("----------------------------------------------------------------");
			}
			userView.alert("Would you like to open an email? y/n");
			String ch = userView.getData();
			if (ch.equalsIgnoreCase("yes") || ch.contains("y")) {
				userView.alert("Enter the ID of the email you would like to view: ");
				String id = userView.getData();
				boolean chk = false;
				Email email = null;
				for (Email e : user.getReceived()) {
					if (e.getId().equalsIgnoreCase(id)) {
						chk = true;
						email = e;
						break;
					}
				}
				if (!chk) {
					userView.alert("ID " + id + " does not exist.");
				} else {
					userView.openEmail(user, email);
				}
			} else
				userView.onInit(user);
		} else {
			userView.alert("Your inbox is empty.");
			userView.onInit(user);
		}
	}

	public void delete(User user, Email email) { // delete an email
		boolean chk = false;
		for (Email e : user.getReceived()) {
			if (e.equals(email)) {
				chk = true;
			}
		}
		if (chk) {
			user.getDeleted().add(0, email);
			user.getReceived().remove(email);
			userView.alert("The email has been deleted.");
			database.setUsers();
		}
	}

	// new email
	public void compose(User user, String to, String subject, ArrayList<String> content, String closing, String sign) {
		Email email = new Email(user.getUsername(), to, System.currentTimeMillis(), subject, content, closing, sign);
		String id = "E0" + (user.getSent().size());
		email.setId(id);
		for (User u : database.getUsers()) {
			if (u.getUsername().equals(to)) {
				email.setId("E0" + (int) (u.getReceived().size() + 1));
				user.getSent().add(0, email);
				u.getReceived().add(0, email);
				database.setUsers();
				break;
			}
		}
		userView.onInit(user);
	}

	public boolean validate(String username) {
		boolean chk = false;
		for (User user : database.getUsers()) {
			if (user.getUsername().equals(username)) {
				chk = true;
			}
		}
		return chk;
	}

	public void editName(User user, String name) {
		user.setName(name);
		userView.alert("Name successfully changed!");
		database.setUsers();
		userView.profile(user);
	}

	public void editUsername(User user, String username) {
		user.setUsername(username);
		userView.alert("Username successfully changed!");
		database.setUsers();
		userView.profile(user);
	}

	public void editPassword(User user, String password) {
		user.setPassword(password);
		userView.alert("Password successfully changed!");
		database.setUsers();
		userView.profile(user);
	}

	public void viewSent(User user) { // display all sent emails, retrieve all sent emails
		if (user.getSent().size() != 0) {
			userView.alert("\t\t\t\t\t\t\t\t\t" + user.getSent().size() + " Emails");
			for (int i = 0; i < user.getSent().size(); i++) {
				Email e = user.getSent().get(i);
				Date d = new Date(e.getDate());
				userView.alert("----------------------------------------------------------------");
				userView.alert(e.getId() + " " + e.getTo() + "\t\t\t" + e.getSubject() + " " + e.getContent().get(0)
						+ "  " + sdf.format(d));
				userView.alert("----------------------------------------------------------------");
			}
		} else {
			userView.alert("No emails have been sent yet.");
		}
		userView.onInit(user);
	}

	public void viewDeleted(User user) { // display all received emails
		if (user.getDeleted().size() != 0) {
			userView.alert("\t\t\t\t\t\t\t" + user.getDeleted().size() + " Emails");
			for (int i = 0; i < user.getDeleted().size(); i++) {
				Email e = user.getDeleted().get(i);
				Date d = new Date(e.getDate());
				userView.alert("----------------------------------------------------------------");
				userView.alert(e.getId() + " " + e.getFrom() + "\t\t\t" + e.getSubject() + " " + e.getContent().get(0)
						+ "  " + sdf.format(d));
				userView.alert("----------------------------------------------------------------");
			}
			userView.alert(
					"What would you like to do? \n1. Permanenty delete an email\n2. Restore an email\n3. God back\nEnter your choice: ");
			String ch1 = userView.getData();
			int choice;
			while (true) {
				try {
					choice = Integer.parseInt(ch1.substring(0, 1));
					break;
				} catch (NumberFormatException e) {
					userView.alert("Please enter a valid integer value.");
					continue;
				}
			}

			switch (choice) {
			case 1:
				permanentDelete(user);
				break;
			case 2:
				restore(user);
				break;
			case 3:
				userView.onInit(user);
				break;
			default:
				userView.alert("Please enter a valid choice.");
				viewDeleted(user);
				break;
			}

		} else {
			userView.alert("Your deleted bin is empty.");
			userView.onInit(user);
		}
	}

	public void permanentDelete(User user) {
		boolean chk = false;
		userView.alert("Enter an email to delete: ");
		String email = userView.getData();
		for (int i = 0; i < user.getDeleted().size(); i++) {
			Email e = user.getDeleted().get(i);
			if (e.getId().equalsIgnoreCase(email)) {
				chk = true;
				user.getDeleted().remove(e);
				break;
			}
		}
		if (!chk) {
			userView.alert("Email has been deleted permanently.\n");
		} else {
			userView.alert("No email with that ID has been found.\n");
		}
		viewDeleted(user);
	}

	public void restore(User user) {
		boolean chk = false;
		userView.alert("Enter an email to restore: ");
		String email = userView.getData();
		int ind = 0;
		try {
			ind = Integer.parseInt(email.substring(1));
		} catch (NumberFormatException e) {
			System.out.println("The application may be terminated.");
		}
		for (int i = 0; i < user.getDeleted().size(); i++) {
			Email e = user.getDeleted().get(i);
			if (e.getId().equalsIgnoreCase(email)) {
				chk = true;
				user.getReceived().add(0, e);
				user.getDeleted().remove(e);
				break;
			}
		}
		if (chk) {
			userView.alert("Email has been restored.\n");
		} else {
			userView.alert("No email with that ID has been found.\n");
		}
		viewDeleted(user);
	}

}
