package com.zsgs.emailapplication.models;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String id;
	private String name;
	private String username;
	private String password;
	private long date; // of creation of account ?
	private ArrayList<Email> received = new ArrayList<Email>();
	private ArrayList<Email> sent = new ArrayList<Email>();
	private ArrayList<Email> deleted = new ArrayList<Email>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public ArrayList<Email> getReceived() {
		return received;
	}

	public void setReceived(ArrayList<Email> received) {
		this.received = received;
	}

	public ArrayList<Email> getSent() {
		return sent;
	}

	public void setSent(ArrayList<Email> sent) {
		this.sent = sent;
	}

	public List<Email> getDeleted() {
		return deleted;
	}

	public void setDeleted(ArrayList<Email> deleted) {
		this.deleted = deleted;
	}
}
