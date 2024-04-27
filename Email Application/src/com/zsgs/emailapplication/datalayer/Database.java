package com.zsgs.emailapplication.datalayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsgs.emailapplication.models.User;

public class Database {
	private static Database database;
	private static ArrayList<User> users = new ArrayList<User>();
	private File usersf = new File("C:\\Users\\Sivakumar\\eclipse-workspace\\Email Application\\src\\com\\zsgs\\emailapplication\\datalayer\\users.json");
	
	ObjectMapper map = new ObjectMapper();
	
	private Database() {
		
	}
	
	public static Database getInstance() {
		if(database==null)
			database = new Database();
		return database;
	}
	
	private ArrayList<User> getUsersJSON() {
		try {
			if (usersf.length() != 0)
				return map.readValue(usersf, new TypeReference<ArrayList<User>>() {
				});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}

	public void setUsers() {
		try {
			map.writeValue(usersf, users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addUsers(User user) {
		users.add(user);
		setUsers();
	}
	
	public ArrayList<User> getUsers() {
		users = getUsersJSON();
		return users;
	}
	
}
