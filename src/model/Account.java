/*Class that creates an account object. This is called upon registration by 
 *Registration controller and the account creation controller. 
 *Stores account information into the database upon creation. 
 *This is the super class to the checkings and savings classes.
 *@author: Team-USA Date: 10/12/2014
 */
package model;

import java.util.Date;

public class Account {
	long number; //account number
	double balance;  //account balance
	Date date_created; //date made
	User owner; //owner of account
	String accountType; //type of account
	Date dateBalanceUnderMin; // The date when the balance first went under the minimum
	Date interestPeriod;	  // How many days the balance has been within the current interest period
	double interestRate; // The interest rate for the account

	private static final String DEFAULT_ACCOUNT_TYPE = "checking";
	public static final double MIN_BALANCE = 100.0;
	public static final double LOW_BALANCE_PENALTY_AMOUNT = 25; // If the balance falls below MIN_BALANCE for over 30
																// days then we apply a penalty of this amount
	public static final double DEFAULT_INTEREST_RATE = Checkings.DEFAULT_INTEREST_RATE;

	public Account() {
		this.number = 0;
		this.balance = 0.0;
		this.date_created = new Date();
		this.owner = new User();
		this.accountType = DEFAULT_ACCOUNT_TYPE;
		this.setDateBalanceUnderMin(null);
		this.setinterestRate(DEFAULT_INTEREST_RATE);
		this.setInterestPeriod(new Date());
	}

	public Account(String accountType){
		this.number = 0;
		this.balance = 0.0;
		this.date_created = new Date();
		this.owner = new User();
		this.accountType = accountType;
		this.setDateBalanceUnderMin(null);
		this.setinterestRate(DEFAULT_INTEREST_RATE);
		this.setInterestPeriod(new Date());
	}

	public Account(String accountType, double interestRate) {
		this(accountType);
		this.setinterestRate(interestRate);
		this.setInterestPeriod(new Date());
	}

	public Account(User user)
    {
        this.number = 0;
        this.balance = 0.0;
        this.date_created = new Date();
        this.owner = new User(user);
		this.accountType = DEFAULT_ACCOUNT_TYPE;
		this.setDateBalanceUnderMin(null);
		this.setinterestRate(DEFAULT_INTEREST_RATE);
		this.setInterestPeriod(new Date());
	}

	public Account(User user, double interestRate) {
		this(user);
		this.setinterestRate(interestRate);
		this.setInterestPeriod(new Date());
	}

	public Account(User user, String accountType)
	{
		this.number = 0;
		this.balance = 0.0;
		this.date_created = new Date();
		this.owner = new User(user);
		this.accountType = accountType;
		this.setDateBalanceUnderMin(null);
		this.setInterestPeriod(new Date());

		if(accountType.equalsIgnoreCase("checking"))
			this.setinterestRate(Checkings.DEFAULT_INTEREST_RATE);
		else
			this.setinterestRate(Savings.DEFAULT_INTEREST_RATE);
	}

	public Account(User user, String accountType, double interestRate)
	{
		this(user, accountType);
		this.setinterestRate(interestRate);
		this.setInterestPeriod(new Date());
	}

	public Account(long accountNum, double accountBal, Date dateMade, User user){
		this.number = accountNum;
		this.balance = accountBal;
		this.date_created = new Date(dateMade.getTime());
		this.owner = new User(user);
		this.accountType = DEFAULT_ACCOUNT_TYPE;
		this.setDateBalanceUnderMin(null);
		this.setinterestRate(DEFAULT_INTEREST_RATE);
		this.setInterestPeriod(new Date());
	}

	public Account(long accountNum, double accountBal, Date dateMade, User user, String accountType){
		this.number = accountNum;
		this.balance = accountBal;
		this.date_created = new Date(dateMade.getTime());
		this.owner = new User(user);
		this.accountType = accountType;
		this.setDateBalanceUnderMin(null);
		if(accountType.equalsIgnoreCase("checking"))
			this.setinterestRate(Checkings.DEFAULT_INTEREST_RATE);
		else
			this.setinterestRate(Savings.DEFAULT_INTEREST_RATE);
		this.setInterestPeriod(new Date());
	}
	
	public Account(Account anAccount){
		this(anAccount.getNumber(),
			 anAccount.getBalance(),
			 anAccount.getdateCreated(),
			 anAccount.getUser(),
			 anAccount.getAccountType());
		this.setDateBalanceUnderMin(anAccount.getDateBalanceUnderMin());
		this.setinterestRate(anAccount.getinterestRate());
		this.setInterestPeriod(new Date());
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public long getNumber(){
		return number;
	}
	
	public Double getBalance(){
		return balance;
	}
	
	public User getUser(){
		return owner;
	}
	
	public Date getdateCreated(){
		return date_created;
	}
	
	public void setNumber(long value){
		number = value;
	}
	
	public void setBalance(double value) {
		balance = value;
	}
	
	public void setdateCreated(Date date){
		date_created = new Date(date.getTime());
	}
	
	public void setUser(User user){
		owner = new User(user);
	}

	public Date getDateBalanceUnderMin() {
		return dateBalanceUnderMin;
	}

	public void setDateBalanceUnderMin(Date dateBalanceUnderMin) {
		if(dateBalanceUnderMin == null)
			this.dateBalanceUnderMin = null;
		else
			this.dateBalanceUnderMin = new Date(dateBalanceUnderMin.getTime());
	}

	public double getinterestRate(){
		return interestRate;
	}

	public void setinterestRate(double intRate){
		interestRate = intRate;
	}

	public Date getInterestPeriod() {
		return interestPeriod;
	}

	public void setInterestPeriod(Date interestPeriod) {
		if(interestPeriod == null)
			this.interestPeriod = null;
		else
			this.interestPeriod = new Date(interestPeriod.getTime());
	}

	// Applies a penalty to an account
	public void applyPenalty()
	{
		this.setBalance(balance - LOW_BALANCE_PENALTY_AMOUNT);
		// Reset the date the penalty was applied
		this.setDateBalanceUnderMin(new Date());

		setInterestRate();
	}

	/*Method that checks if an account needs a penalty. Returns
	 *true if balance is below minimum and if it's been over
	 *30 days.
	 */
	public boolean needsPenalty()
	{
		long thirtyDays = 2592000000L; // thirty days in milliseconds
		long currentTime = (new Date()).getTime();
		long thirtyDaysAgo = currentTime - thirtyDays;

		return this.getDateBalanceUnderMin() != null && this.getDateBalanceUnderMin().getTime() < thirtyDaysAgo;
	}

	// Applies interest to an account
	public void applyInterest()
	{
		this.deposit(balance * interestRate);
		interestPeriod = new Date();
	}

	public double getEstimatedInterest()
	{
		return balance * interestRate;
	}

	/*Method that checks if an account needs interest.
	 *Returns true if interest period is correct and the time
	 *is past 30 days
	 */
	public boolean needsInterest()
	{
		long thirtyDays = 2592000000L; // thirty days in milliseconds
		long currentTime = (new Date()).getTime();
		long thirtyDaysAgo = currentTime - thirtyDays;

		return this.getInterestPeriod() != null && this.getInterestPeriod().getTime() < thirtyDaysAgo;
	}

	public void withdraw(Double amount) {
		if(amount != null)
			this.setBalance(balance - amount);

		// If the balance has fallen under the minimum amount then we need to set the date it fell below
		// Unless it's balance was already under the amount
		if(balance < MIN_BALANCE && getDateBalanceUnderMin() != null) {
			this.setDateBalanceUnderMin(new Date());
		}

		setInterestRate();
	}

	public void deposit(Double amount) {
		if(amount != null)
			this.setBalance(balance + amount);

		// If the balance has gone back above the minimum then we reset the date.
		if(balance > MIN_BALANCE) {
			this.setDateBalanceUnderMin(null);
		}

		setInterestRate();
	}

	public boolean equals(Object otherAccount) {
		return !(otherAccount == null ||
			   !(otherAccount instanceof Account)) &&
			   ((Account) otherAccount).getNumber() == this.getNumber();
	}

	public void setInterestRate()
	{
		// By default use the checkings interest rates
		if(balance < 1000.0 && interestRate != DEFAULT_INTEREST_RATE)
		{
			interestRate = DEFAULT_INTEREST_RATE;
			interestPeriod = new Date();
		}
		else if(balance >= 1000.0 && balance < 2000.0 && interestRate != Checkings.INTEREST_RATE_1000_TO_2000)
		{
			interestRate = Checkings.INTEREST_RATE_1000_TO_2000;
			interestPeriod = new Date();
		}
		else if(balance >= 2000.0 && balance < 3000.0 && interestRate != Checkings.INTEREST_RATE_2000_TO_3000)
		{
			interestRate = Checkings.INTEREST_RATE_2000_TO_3000;
			interestPeriod = new Date();
		}
		else if(balance >= 3000.0 && interestRate != Checkings.INTEREST_RATE_OVER_3000)
		{
			interestRate = Checkings.INTEREST_RATE_OVER_3000;
			interestPeriod = new Date();
		}
	}
}
