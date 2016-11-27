package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.mail.SimpleEmail;

import tools.EMailTool;
import tools.FileOperator;
import tools.WebProperties;

import dao.DBAccess;

public class InitServlet extends HttpServlet {

	public void destroy() {
		super.destroy(); 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	}

	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		
		//初始化数据库参数
		DBAccess.drv = conf.getInitParameter("drv");
		DBAccess.url = conf.getInitParameter("url");
		DBAccess.usr = conf.getInitParameter("usr");
		DBAccess.pwd = conf.getInitParameter("pwd");	
		
		//初始化email参数
		EMailTool.simpleEmail=new SimpleEmail();
		EMailTool.emailHost=conf.getInitParameter("emailHost");
		EMailTool.emailUserEmail=conf.getInitParameter("emailUserEmail");
		EMailTool.emailUserName=conf.getInitParameter("emailUserName");		
		EMailTool.emailPassword=conf.getInitParameter("emailPassword");		
		EMailTool.domain=conf.getInitParameter("domain");
		
		ServletContext servletContext=conf.getServletContext();
		String fileDir=servletContext.getRealPath("\\web.properties");
		
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(params.properties().setFileName(fileDir));
		try
		{
			
		    Configuration config = builder.getConfiguration();
		    
		    FileOperator.tempFullDir=servletContext.getRealPath(config.getString("tempDir"));
		    FileOperator.tempDir=config.getString("tempDir");

		    WebProperties.propertiesMap.put("goodsImageFullDir", 
		    		servletContext.getRealPath(config.getString("goodsImageDir")));
		    WebProperties.propertiesMap.put("goodsImageDir",config.getString("goodsImageDir"));
		    
		    WebProperties.propertiesMap.put("projectName",config.getString("projectName"));
		    WebProperties.propertiesMap.put("redirectTime",config.getString("redirectTime"));
		    
		    WebProperties.propertiesMap.put("goodsIconWidth",config.getString("goodsIconWidth"));
		    WebProperties.propertiesMap.put("goodsIconHeight",config.getString("goodsIconHeight"));
 
		    
		}
		catch(ConfigurationException cex)
		{
			cex.printStackTrace();
		}

	}

}
