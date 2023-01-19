package com.tester;

import static com.util.DBUtil.openConnection;

public class Tester1 {

	public static void main(String[] args) {
		try {
			openConnection();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}
