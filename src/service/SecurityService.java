package service;

import javax.servlet.http.HttpSession;

import tableBean.User;
import tools.EMailTool;
import tools.Encryption;

import dao.UserDao;

public class SecurityService {
	//验证用户名和密码
	public Integer login(User user, HttpSession session){//1表示成功，-1表示失败
		UserDao userDao=new UserDao();
		User user1=userDao.getUserByName(user.getUserName());//数据库查询，根据用户名获得数据库该用户信息
		user.setSalt(user1.getSalt());

		//检查密码是否正确
		if(Encryption.checkPassword(user, user1.getPassword())){
			session.setAttribute("userName", user.getUserName());
			return 1;
		}else
			return -1;
	}
	
	
	//返回0表示数据库操作错误，1表示成功创建用户，-1表示有重名用户，-10表示emai已被注册，-11表示重名用户且email被注册
	public Integer register(User user){
		UserDao userDao=new UserDao();
		Integer result=0;
		//有同名用户
		if(userDao.hasStringValue("userName",user.getUserName())==1){
			result=-1;
		}
		//email已被注册过
		if(userDao.hasStringValue("email",user.getEmail())==1){
			result+=-10;
		}
		//有同名用户或email被注册过
		if(result<0)
			return result;		
		
		//根据密码生成盐和加密密码
		Encryption.encryptPasswd(user);
		
		if(userDao.add(user))
			return 1;//成功创建用户
		else	
			return 0;//数据库操作失败
	}	
	
	public Integer update(User user){
		UserDao userDao=new UserDao();

		if(userDao.hasStringValue("userName",user.getUserName())!=1){//没有该用户，可以注册
			if(userDao.add(user))
				return 1;//成功创建用户
			else	
				return -1;//数据库操作失败
		}else{//注册失败
			return -2;//有同名用户
		}
	}		
	
	
	public Integer findPasswordByEmail(User user,Integer rand){//返回值：1成功发送邮件，-1发送邮件失败，-2邮箱未注册过
		UserDao userDao=new UserDao();
		Integer result=0;
		
		if(userDao.hasStringValue("email",user.getEmail())==1)//该email存在
			result=EMailTool.sendReturnPassword(user.getEmail(),rand);//发送邮件
		else//该email不存在
			result=-2;	
		
		return result;
	}
	
	public Integer updatePassword(User user){//返回值：1成功发送邮件，-1发送邮件失败，-2邮箱未注册过
		UserDao userDao=new UserDao();
		Integer result;
		
		//根据密码生成盐和加密密码
		Encryption.encryptPasswd(user);
		
		if(userDao.updatePassword(user))//修改密码成功
			result=1;
		else//修改密码失败！
			result=-1;	
		
		return result;
	}
}
