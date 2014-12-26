package com.example.contactmanager;

/**
 * @author Vamsi Katepalli NetId: Vxk142730
 * This class is written as part of User Interface Assignment taught by 
 * Dr. John Cole.
 * start date: 10/27/2014
 * 
 * user object is used as bean and implements comparable interface for sorting the data.
 * 
 * */

public class User implements Comparable<User> {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private int iconId;
	
	public int getIconId() {
		return iconId;
	}
	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public User(String firstName, String lastName, String phoneNumber,
			String emailAddress, int iconId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.iconId = iconId;
	}
	
	public User(String firstName) {
		super();
		this.firstName = firstName;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		
		return true;
	}
	@Override
	public int compareTo(User another) {
		if(another.equals(this))
			return 0;
		else 
			return this.firstName.compareToIgnoreCase(another.firstName);
	}
	
	
	
	
}
