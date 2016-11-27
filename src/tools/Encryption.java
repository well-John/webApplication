package tools;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import tableBean.User;

public class Encryption {
    private static final MessageDigest md;  
    private static final Base64  b64Encoder;  
    private static final Integer saltLen = 15; 
    
    
    static{  
        try {  
        	//调用 getInstance 将返回已初始化过的MessageDigest对象
            md = MessageDigest.getInstance("MD5", "SUN");  
            b64Encoder = new Base64();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 

    //将客户输入的密码加盐后加密    
    public static void  encryptPasswd(User user){  
    	String salt="";
    	Random rand = new Random();
    	//可选的字符
    	String base = "abcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()_+";
    	
    	//得到盐salt
		for (int i = 0; i < saltLen; i++) {
			//获取随机字符
			String target = String.valueOf(base.charAt(rand.nextInt(base.length())));
			salt+=target;
		}
    	
        try{              
            md.reset(); 
            //通过一次或多次调用以下update（更新）方法来完成 向MessageDigest对象提供要计算的数据。
            md.update(salt.getBytes("UTF-8"));  
            md.update(user.getPassword().getBytes("UTF-8"));  
            //调用 digest（）方法来计算摘要（即生成散列码）：
            byte[] bys = md.digest();
            //重新编码  
            byte[] lastPassword=b64Encoder.encode(bys);           
            //new String(lastPassword)  根据byte数组创建一个匿名字符串
            user.setPassword(new String(lastPassword));            
        }catch(Exception ex){  
            ex.printStackTrace();   
        }           

        user.setSalt(salt); 
    }  
    
    
    //将客户输入的密码加盐后加密    
    public static boolean  checkPassword(User user, String dataBasePassword){      	
        try{        
        	
            md.reset(); 
            //通过一次或多次调用以下update（更新）方法来完成 向MessageDigest对象提供要计算的数据。
            md.update(user.getSalt().getBytes("utf-8"));  
            md.update(user.getPassword().getBytes("utf-8"));  
            //调用 digest（）方法来计算摘要（即生成散列码）：
            byte[] bys = md.digest();
            //重新编码
            byte[] lastPassword=b64Encoder.encode(bys);

            String inputPassword=new String(lastPassword);
            
            if(dataBasePassword.equals(inputPassword))
            	return true;
            else 
            	return false;  
        }catch(Exception ex){  
            ex.printStackTrace(); 
            return false;
        }           
    }
}
