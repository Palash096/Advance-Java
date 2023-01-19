package com.DAL;

import com.pojo.Account;
import java.util.List;
import java.sql.SQLException;

public interface AccountDAL {
	//CRUD Operations
	
	// select * from accounts;
	List<Account> getAllAccounts();
	
	// add new account
	int addAccount(Account act);
	
	// update new account
	int updateAccount(Account act);
	
	// delete account
	int deleteAccount(int id);
	
	// transfer money from one account to another
	String moneyTransfer(int sid, int rid, double amount) throws SQLException; 
}
