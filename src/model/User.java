/*Class that creates a user object upon Registration. Called by the
 *Registration controller. Keeps track of all the user's information
 *for account registry and also password lookup.
 *@author: Team-USA Date: 10/12/2014*/
package model;

public class User {
	
	String userfirstName; 
	String userlastName;
	String userPassword;
	String userName;
	String userEmail;
	String userPhone;
	String userAddress;
	boolean isAdmin = false; //flag to check if user is an Admin
	String securityQuestion; //used for getting forgotten password
	String securityAnswer; //used for getting forgotten password
	
	
	public User() {}

    // Ctor for a normal user
	public User(String firstName, String lastName, String password, String login, 
			String phone, String email, String address){
		this.userfirstName = firstName;
		this.userlastName = lastName;
		this.userPassword = password;
		this.userName = login;
		this.userEmail = email;
		this.userPhone = phone;
		this.userAddress = address;
		securityQuestion = "";
		securityAnswer = "";
	}
	
	//Constructor for administrative user
	public User(String firstName, String lastName, String password, String login, 
			String phone, String email, String address, boolean isAdmin){
		this.userfirstName = firstName;
		this.userlastName = lastName;
		this.userPassword = password;
		this.userName = login;
		this.userEmail = email;
		this.userPhone = phone;
		this.userAddress = address;
		this.isAdmin = isAdmin;
		securityQuestion = "";
		securityAnswer = "";
	}

    // Copy ctor
	public User(User aUser){
		this(aUser.getFirstname(), aUser.getLastname(), aUser.getPassword(), aUser.getUsername(), aUser.getPhone(),
				aUser.getEmail(), aUser.getAddress(), aUser.getisAdmin());

		this.setSecurityAnswer(aUser.getSecurityAnswer());
		this.setSecurityQuestion(aUser.getSecurityQuestion());
	}
	
	public String getFirstname(){
		return userfirstName;
	}
	
	public String getLastname(){
		return userlastName;
	}
	
	public String getPassword(){
		return userPassword;
	}
	
	public String getUsername(){
		return userName;
	}
	
	public String getEmail(){
		return userEmail;
	}
	
	public String getPhone(){
		return userPhone;
	}
	
	public String getAddress(){
		return userAddress;
	}
	
	public boolean getisAdmin(){
		return isAdmin;
	}
	
	public void setFirstname(String n){
		userfirstName = n;
	}
	
	public void setLastname(String n){
		userlastName = n;
	}
	
	public void setPassword(String n){
		userPassword = n;
	}
	
	public void setUsername(String n){
		userName = n;
	}
	
	public void setEmail(String n){
		userEmail = n;
	}
	
	public void setPhone(String n){
		userPhone = n;
	}
	
	public void setAddress(String n){
		userAddress = n;
	}
	
	public void setisAdmin(boolean adminFlag){
		isAdmin = adminFlag;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public boolean equals(Object otherUser) {
		return !(otherUser == null ||
			   !(otherUser instanceof User)) &&
			   ((User) otherUser).getUsername().equals(this.getUsername());

	}
}
