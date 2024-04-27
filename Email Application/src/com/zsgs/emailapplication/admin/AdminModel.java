package com.zsgs.emailapplication.admin;

import java.text.SimpleDateFormat;

import com.zsgs.emailapplication.datalayer.Database;
import com.zsgs.emailapplication.models.User;

public class AdminModel {
	AdminView adminView;
	Database database = Database.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
	User user;

	AdminModel(AdminView adminView, User user) {
		this.adminView = adminView;
		this.user = user;
	}

	public void viewUsers() {
		if (database.getUsers().size() != 0) {
			System.out.println("The users are: ");
			System.out.println("-------------------------------------------------------------------------");

			for (User u : database.getUsers()) {
				System.out.println(u.getId() + " " + u.getName() + " " + sdf.format(u.getDate()));
				System.out.println("-------------------------------------------------------------------------");
			}
		}
		else {
			System.out.println("No users have been registered yet.");
		}
		adminView.onInit();
	}
	
	public void delete() {
		if(database.getUsers().size()==0) {
			
		}
		else {
			viewUsers();
			adminView.alert("Enter the ID of the user you wish to delete: ");
			String id = adminView.getData();
			for(int i=0;i<database.getUsers().size();i++) {
				if(database.getUsers().get(i).getId().equalsIgnoreCase("id")) {
					database.getUsers().remove(i);
					break;
				}
			}
		}
		adminView.onInit();
	}
}
