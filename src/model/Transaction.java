/*Class that creates a transaction object to keep a record
 *of account transactions for the users. Object is created and saved
 *in the database each time any type of transaction occurs.
 *@author: Team-USA Date: 10/12/2014
 */

package model;
import java.util.Date;

public class Transaction
{
	int id;
	String transactionType; //valid types are withdraw, deposit, and transfer
	double amount;
	Date transactionDate;

	Account userAccount;
	Account otherAccount;
	//User teller;
	
	public Transaction()
	{
	  this.transactionType = "deposit";
	  this.amount = 0.0;
	  this.transactionDate = new Date();
	  this.userAccount = null;
	  this.otherAccount = null;
	  //this.teller = new Teller();
	}

	public Transaction(Account anAccount, String transactionType, double theAmount)
	{
		this.transactionType = transactionType;
		this.amount = theAmount;
		this.transactionDate = new Date();
		this.userAccount = new Account(anAccount);
		this.otherAccount = null;
	}

	public Transaction(Account anAccount, String transactionType, double theAmount, Date dateMade)
	{
		this.transactionType = transactionType;
		this.amount = theAmount;
		this.transactionDate = new Date(dateMade.getTime());
		this.userAccount = new Account(anAccount);
		this.otherAccount = null;
	}
	
	public Transaction(Account anAccount, String transactionType, double theAmount, Date dateMade, Account transferTo)
	{
		this.transactionType = transactionType;
		this.amount = theAmount;
		this.transactionDate = new Date(dateMade.getTime());
		this.userAccount = new Account(anAccount);
		if(transactionType.equalsIgnoreCase("transfer"))
		{
			this.otherAccount = new Account(transferTo);
		}
		else
		{
			this.otherAccount = null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTransactionType(String transType) {
		this.transactionType = transType;
	}

	public String getTransaction()
	{
		return transactionType;
	}

	public double getAmount()
	{
		return amount;
	}
	
	public Date getDate()
	{
		return transactionDate;
	}
	
	public Account getAccount()
	{
		return userAccount;
	}
	
	public void setAccount(Account anAccount)
	{
		this.userAccount = new Account(anAccount);
	}

	public Account getOtherAccount() {
		return otherAccount;
	}

	public void setOtherAccount(Account otherAccount) {
		if(otherAccount != null)
			this.otherAccount = new Account(otherAccount);
	}
	
	public void setAmount(double theAmount)
	{
		this.amount = theAmount;
	}
	
	public void setDate(Date dateMade)
	{
		this.transactionDate = new Date(dateMade.getTime());
	}
	
	
}
