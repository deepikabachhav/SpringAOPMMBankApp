package com.cg.app.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cg.app.pojo.account.SavingsAccount;

public class SavingsAcountRowMapper implements RowMapper<SavingsAccount> {
	
	 public SavingsAccount mapRow(ResultSet rs, int rownumber) throws SQLException {  
		 SavingsAccount savingsAccount=new SavingsAccount(rs.getInt("account_id"),rs.getString("account_hn"),rs.getDouble(3),rs.getBoolean(4)); 
		 return savingsAccount;  
	  }
}
