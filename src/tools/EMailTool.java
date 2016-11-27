package tools;


import org.apache.commons.mail.SimpleEmail;

public class EMailTool {
	static public String emailHost;
	static public String emailUserEmail;
	static public String emailUserName;
	static public String emailPassword;
	static public String domain;//本网站域名
	
	static public SimpleEmail simpleEmail;
	
	static public boolean sendSimpleEmail(String to, String subject, String emailContent){
		boolean result = false;
		SimpleEmail simpleEmail=new SimpleEmail();
		//设置字符编码方式
		simpleEmail.setCharset("UTF-8");
		try {
			//设置邮件服务器
			simpleEmail.setHostName(emailHost);
			//设置帐号密码
			simpleEmail.setAuthentication(emailUserName,emailPassword);
			//设置发送源邮箱
			simpleEmail.setFrom(emailUserEmail);
			//设置目标邮箱
			simpleEmail.addTo(to);
			//设置主题
			simpleEmail.setSubject(subject);
			//设置邮件内容
			simpleEmail.setMsg(emailContent);
			//发送邮件
			simpleEmail.send();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	static public Integer sendReturnPassword(String to, Integer rand){
		Integer result = -1;
		SimpleEmail simpleEmail=new SimpleEmail();
		//设置字符编码方式
		simpleEmail.setCharset("UTF-8");
		try {
			//设置邮件服务器
			simpleEmail.setHostName(emailHost);
			//设置帐号密码
			simpleEmail.setAuthentication(emailUserName,emailPassword);
			//设置发送源邮箱
			simpleEmail.setFrom(emailUserEmail);
			//设置目标邮箱
			simpleEmail.addTo(to);				
			//设置主题
			simpleEmail.setSubject("找回密码");			
			//设置邮件内容
			String url=domain+"/userManage/newPassword.jsp?rand="+rand.toString();
			simpleEmail.setMsg("找回密码--请复制以下网址，并在浏览器地址栏粘贴并访问："+url);
			//发送邮件
			simpleEmail.send();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}	
}
