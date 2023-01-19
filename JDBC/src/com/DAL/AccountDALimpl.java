package com.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

import com.pojo.Account;
import com.util.DBUtil;

public class AccountDALimpl implements AccountDAL {
	
	private Connection con;
	private ResultSet rset;
	private Statement stmt;
	private PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4, pstmt5;
	private CallableStatement cstmt;
	
	public AccountDALimpl() throws SQLException {
		con = DBUtil.getCon();
		
		// create statement for static sql
		stmt = con.createStatement();
		System.out.println("--statement crated for static sql---");
		
		pstmt1 = con.prepareStatement("insert into accounts values(?,?,?,?)");
		pstmt2 = con.prepareStatement("update accounts set name=?, type=?, bal=? where id=?");
		pstmt3 = con.prepareStatement("delete from accounts where id=?");
		pstmt4 = con.prepareCall("update accounts set bal = bal + ? where id = ?;");
		pstmt5 = con.prepareCall("update accounts set bal = bal - ? where id = ?;");
		cstmt = con.prepareCall("{call transfer_funds(?,?,?,?,?)}");
		
		cstmt.registerOutParameter(4, Types.DOUBLE);
		cstmt.registerOutParameter(5, Types.DOUBLE);
	}
	
	@Override
	public List<Account> getAllAccounts(){
		try {
			List<Account> allAccount = new ArrayList<Account>();
			
			// stmt.executeQuery: read data : select query
			rset = stmt.executeQuery("select * from accounts");
			while(rset.next()) {
				allAccount.add(new Account(rset.getInt("id"),
						rset.getString("name"),
						rset.getString("type"),
						rset.getDouble("bal")));
			}
			return allAccount;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int addAccount(Account act) {
		//insert into books values(?,?,?,?);
		
		try {
			pstmt1.setInt(1, act.getId());
			pstmt1.setString(2, act.getName());
			pstmt1.setString(3, act.getType());
			pstmt1.setDouble(4, act.getBalance());
			
			int i = pstmt1.executeUpdate(); // write only
			System.out.println("---inserted obj:"+act);
			return i;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int updateAccount(Account act) {
		// update accounts set name=?, type=?, bal=? where id=?
		
		try {
			pstmt2.setString(1, act.getName());
			pstmt2.setString(2, act.getType());
			pstmt2.setDouble(3, act.getBalance());
			pstmt2.setInt(4, act.getId());
			
			int i = pstmt2.executeUpdate();
			System.out.println("Account Updated!!! "+act);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int dipositeAmount(Account act) {
		//update accounts set bal =bal+? where id=?
		try {
			pstmt4.setDouble(1, act.getBalance());
			pstmt4.setInt(2, act.getId());
			
			int i = pstmt4.executeUpdate();
			System.out.println("Balance Updated!!! "+act);
			return i;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public int withdrawAmount(Account act) {
		//update accounts set bal =bal-? where id=?
		try {
			pstmt5.setDouble(1, act.getBalance());
			pstmt5.setInt(2, act.getId());
			
			int i = pstmt5.executeUpdate();
			System.out.println("Balance Updated!!! "+act);
			return i;
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteAccount(int id) {
		// delete from account where id=?
		
		try {
			pstmt3.setInt(1, id);
			int i = pstmt3.executeUpdate();
			System.out.println("Account Deleted!!!");
			return i;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String moneyTransfer(int sid, int rid, double amount) throws SQLException {
		try {
			cstmt.setInt(1, sid);
			cstmt.setInt(2, rid);
			cstmt.setDouble(3, amount);
			// execute stored Procedure
			
			cstmt.execute();
			return "Money Transfer : Sender Balance = "+cstmt.getDouble(4)+", Reciver Balance = "+cstmt.getDouble(5);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
