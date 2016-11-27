package dao;

import tableBean.User;

public class UserDao {
	public boolean add(User user) {
		String sql = "INSERT INTO user(userName,password,salt,email) VALUES ('"
				+ user.getUserName()+ "','"
				+ user.getPassword()+ "','"
				+ user.getSalt()+ "','"
				+ user.getEmail() + "') ";
		boolean returnValue = false;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			if(db.update(sql)>0)
				returnValue = true;//成功插入
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return returnValue;
	}
	
	
	public boolean updatePassword(User user) {
		String sql = "update user set password='"+user.getPassword()+"',"+
							"salt='"+ user.getSalt()+ "' where email='"+
							user.getEmail()+ "'";
		
		boolean returnValue = false;
		
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			if(db.update(sql)>0)
				returnValue = true;//成功更新密码和盐
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return returnValue;
	}	
	
	public User getUserByName(String userName){//根据用户名获取用户信息
		User user = new User();
		
		DBAccess db = new DBAccess();

		if (db.createConn()) {
			db.query("select * from user where userName='"+userName+"'");
			while (db.next()) {
				user.setUserNo(db.getIntValue("userNo"));
				user.setUserName(db.getStringValue("userName"));
				user.setPassword(db.getStringValue("password"));
				user.setSalt(db.getStringValue("salt"));
				user.setEmail(db.getStringValue("email"));
			}
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return user;
	}
	
	
	//根据字段名，查是否有字段值为value的记录
	public int hasStringValue(String fieldName, String value){//返回值：1表示有相同值、-1表示没有相同值
		int result = -1;
		DBAccess db = new DBAccess();
		String a="select * from user where "+fieldName+"='"+value+"'";
		if (db.createConn()) {
			db.query("select * from user where "+fieldName+"='"+value+"'");
			while (db.next()) {
				result=1;
			}
			
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return result;
	}
	
	
}
