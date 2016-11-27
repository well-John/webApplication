package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBAccess {
	public static String drv;//数据库类型
	public static String url;//数据库网址
	public static String usr;//用户名
	public static String pwd;//密码

	private Connection conn = null;
	private Statement stm = null;
	private ResultSet rs = null;

	//创建数据库连接对
	public boolean createConn() {//返回假表示连接数据库失败
		boolean b = false;
		try {
			Class.forName(drv).newInstance();
			conn = DriverManager.getConnection(url, usr, pwd);
			
			if(conn!=null)
				b = true;
		} catch (Exception e) {}
		return b;
	}

	public int update(String sql) {//执行更新语句
		int result = -1;
		try {
			if(stm==null)
				stm = conn.createStatement();
			
			result=stm.executeUpdate(sql);
			
		} catch (Exception e) {	}
		return result;
	}

	public void query(String sql) {//执行查询语句
		rs=null;
		try {
			if(stm==null)
				stm = conn.createStatement();	
			
			rs=stm.executeQuery(sql);			
		} catch (Exception e) {	}
	}

	public boolean next() {//rs的下一条记录是否存在
		boolean b = false;
		try {
			if (rs.next())
				b = true;
		} catch (Exception e) {
		}
		return b;
	}

	public String getStringValue(String field) {//获取字符串类型字段的值，字段值为null型的，按照空字符串处理
		String value = "";
		try {
			if (rs != null)
				value = rs.getString(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (value == null)
			value = "";
		return value;
	}
	
	public Integer getIntValue(String field) {//获取整数类型字段的值
		Integer value=new Integer(-11111111)  ;
		try {
			if (rs != null)
				value = rs.getInt(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public Float getFloatValue(String field) {//获取实数类型字段的值
		Float value=new Float(0) ;
		try {
			if (rs != null)
				value = rs.getFloat(field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public ArrayList<String> FieldsList(String tableName) {// 获取表的字段名称，并保存到数组中
		ArrayList<String> fieldList = new ArrayList<String>();
		String sql = "select * from " + tableName + " limit 1";// limit 1表示查询结果只包含一条记录
		if (createConn()) {
			query(sql);
			try {
				ResultSetMetaData fields = rs.getMetaData();//ResultSetMetaData记录了表的元数据，如字段名称，字段类型等
				for (int i = 1; i < fields.getColumnCount() + 1; i++) {//getColumnCount（）获取字段数据
					fieldList.add(fields.getColumnName(i));//getColumnName(i)获取字段名称
				}
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
		return fieldList;
	}
	
	public int getCount(String sqlPart){//查询符合条件的记录的数目
		if (createConn()) {
			query("select count(*) as count1 "+sqlPart);
			try{
				while (next()) {
					return this.getRs().getInt("count1");
				}
			}catch(SQLException e){			
			}
			closeRs();
			closeStm();
			closeConn();
		}
		return 0;
	}	
	
	

	//根据表、字段，获取该字段上所有非重复值
	public ArrayList<String> getStringFieldValueByTableAndField(String tableName,
			String fieldName) {
		ArrayList<String> FieldValueList = new ArrayList<String>();
		DBAccess db = new DBAccess();
		if (db.createConn()) {
			db.query("select distinct " + fieldName + " from " + tableName);
			while (db.next()) {
				FieldValueList.add(db.getStringValue(fieldName));
			}
			db.closeRs();
			db.closeStm();
			db.closeConn();
		}
		return FieldValueList;
	}
	
	

	public void closeConn() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		}
	}

	public void closeStm() {
		try {
			if (stm != null)
				stm.close();
		} catch (SQLException e) {
		}
	}

	public void closeRs() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Statement getStm() {
		return stm;
	}

	public void setStm(Statement stm) {
		this.stm = stm;
	}


}
