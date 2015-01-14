/*Class that creates a checking account and then saves it in the database. 
 *Inherits from Account class. Called by AccountCreationController 
 *and RegistrationController.
 *@author: Team-USA Date: 10/12/2014
 */
package model;



import java.util.Date;


public class Checkings extends Account {
	
	Boolean overdraftProtection; //flag to see if account has od protection

	private static final String ACCOUNT_TYPE = "checking";
	public static final double DEFAULT_INTEREST_RATE = 0.00; // %0
	public static final double INTEREST_RATE_1000_TO_2000 = 0.01; // %2
	public static final double INTEREST_RATE_2000_TO_3000 = 0.02; // %3
	public static final double INTEREST_RATE_OVER_3000 = 0.03; // %4
	
	public Checkings(){
		super(ACCOUNT_TYPE);
		this.overdraftProtection = false;
	}

	public Checkings(User user){
		super(user, ACCOUNT_TYPE);
		this.overdraftProtection = false;
	}

	public Checkings(User user, double firstBalance, Boolean hasOverdraft ){
		super(user, ACCOUNT_TYPE);
		this.balance = firstBalance;
		this.overdraftProtection = hasOverdraft;
	}

	public Checkings(double firstBalance, Boolean hasOverdraft ){
		super(ACCOUNT_TYPE);
		this.balance = firstBalance;
		this.overdraftProtection = hasOverdraft;
	}

	public Checkings( long accountNum,
					  double accountBal,
					  Date dateMade,
					  User user,
					  Boolean hasOverdraft )
	{
		super(accountNum, accountBal, dateMade, user, ACCOUNT_TYPE);
		this.overdraftProtection = hasOverdraft;
	}

	public Checkings(Checkings aCheckingsAccount){
		this(aCheckingsAccount.getNumber(),
			 aCheckingsAccount.getBalance(),
			 aCheckingsAccount.getdateCreated(),
			 aCheckingsAccount.getUser(),
			 aCheckingsAccount.getoverdraftProtection());
	}

	public Boolean getoverdraftProtection(){
		return overdraftProtection;
	}

	public void setoverdraftProtection(Boolean checkFlag){
		overdraftProtection = checkFlag;
	}

	@Override
	public void setInterestRate()
	{
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

