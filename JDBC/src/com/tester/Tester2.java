package com.tester;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.DAL.AccountDALimpl;
import com.pojo.Account;
import com.util.DBUtil;

public class Tester2 {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			DBUtil.openConnection();

			System.out.println("1:Show All Accounts 2: Add New Account 3:Update Account 4:Delete Account: 10.Exit.");
			AccountDALimpl accDal = new AccountDALimpl();

			int choice = 0;
			Scanner sc = new Scanner(System.in);

			do {
				System.out.println("Enter Choice : ");
				choice = sc.nextInt();
				switch (choice) {
				case 1:
					List<Account> acc = accDal.getAllAccounts();
					acc.forEach(a -> System.out.println(a));
					break;
				case 2:
					System.out.println("Enter Id Name Type Balance : ");
					Account dto = new Account(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble());
					int i = accDal.addAccount(dto);
					if(i>0) {
						System.out.println("Data Inserted !!!");
					}
					break;
				case 3:
					System.out.println("Update Id : Enter name type balance : ");
					dto = new Account(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble());
					i = accDal.updateAccount(dto);
					if(i>0) {
						System.out.println("Data Updated !!!");
					}
					break;
				case 4:
					System.out.println("Enter BookId to Delete : ");
					int id = sc.nextInt();
					i = accDal.deleteAccount(id);
					if(i>0) {
						System.out.println("Delete");
					}
					else {
						System.out.println("Id Not Found...");
					}
					break;
				case 5:
					System.out.println("Enter Id : , Amount to be deposited : ");
					dto = new Account(sc.nextInt(), sc.nextDouble());
					i = accDal.dipositeAmount(dto);
					if(i>0) {
						System.out.println("Amount Updated !!!");
					}
					break;
				case 6:
					System.out.println("Enter Id : , Amount to be withdraw : ");
					dto = new Account(sc.nextInt(), sc.nextDouble());
					i = accDal.dipositeAmount(dto);
					if(i>0) {
						System.out.println("Amount Updated !!!");
					}
					break;
				case 7:
					System.out.println("Enter Sender Id Reveiver Id and Amount : ");
					String status = accDal.moneyTransfer(sc.nextInt(), sc.nextInt(), sc.nextDouble());
					System.out.println(status);
					break;
				case 10:
					System.out.println("Bank Application Exited !!!");
					break;
				}
			} while (choice != 10);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
