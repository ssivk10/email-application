package com.zsgs.emailapplication.models;

import java.util.ArrayList;

public class Email {
	private String id;
	private String to;
	private String from;
	private long date;
	private String subject;
	private ArrayList<String> content = new ArrayList<String>();
	private String closing;
	private String signature;
	
	public Email() {
		
	}
	
	public Email(String from, String to, long date, String subject, ArrayList<String> content, String closing,
			String signature) {
		this.to = to;
		this.from = from;
		this.date=date;
		this.subject = subject;
		this.content = content;
		this.closing = closing;
		this.signature = signature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}

	public String getClosing() {
		return closing;
	}

	public void setClosing(String closing) {
		this.closing = closing;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
