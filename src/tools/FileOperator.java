package tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileOperator {
	public static String tempDir;
	public static String  tempFullDir;
	public static String importedDictionary;
	public static File  tmpDir = null;// 初始化上传文件的临时存放目录
	public static File saveDir = null;// 初始化上传文件后的保存目录
	public static File importedDir = null;// 初始化已导入数据库的文件的保存目录

	public  static  String fileName;// 文件名

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath) {
		boolean success = false;
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static boolean copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true),
						2 * 1024);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(dst),
						2 * 1024);
			}
			in = new BufferedInputStream(new FileInputStream(src), 2 * 1024);

			byte[] buffer = new byte[2 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		return true;
	}	
	
	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static boolean  delFile(String filePathAndName) {
		boolean success = false;
		try {
			File myDelFile = new File(filePathAndName);
			if (myDelFile.isFile()) {
				if (myDelFile.delete()) {
					success = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public  static boolean delFileByName(String fileName) {
		boolean success = false;
		try {
			File myDelFile = new File(importedDictionary + "\\" + fileName);
			if (!myDelFile.exists() || myDelFile.delete()) {
				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public static boolean delUnimportedFileByName(String fileFullName) {
		boolean success = false;
		try {
			File myDelFile = new File(fileFullName);
			if (myDelFile.delete()) {
				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static boolean moveFile(String oldPath, String newPath) {
		boolean success = true;
		if (copyFile(oldPath, newPath) == false) {
			return false;
		}
		if (delFile(oldPath) == false) {
			success = false;
		}
		
		return success;
	}


	private static boolean deleteDictionary(File file) {
		boolean success = true;
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				if (file.delete() == false) // delete()方法 你应该知道 是删除的意思;
					return false;

			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					if (deleteDictionary(files[i]) == false) // 把每个文件
																	// 用这个方法进行迭代
						return false;
				}
				if (file.delete() == false)// 删除文件夹
					return false;
			}
		}
		return success;
	}
	
	public static boolean exitInDir(String fileName1, File dir) {
		if (dir.exists()) { // 判断文件夹是否存在
			if (dir.isFile()) { // 判断是否是文件
				if (dir.getName().equals(fileName1)) // 文件名相同
					return true;
			} else if (dir.isDirectory()) { // 目录,递归调用
				File files[] = dir.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					if(exitInDir( fileName1, files[i]))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean deleteDictionaryByName(String dictionaryFullName) {
		boolean success = false;
		try {
			File myDelFile= new File(dictionaryFullName);
			if (deleteDictionary(myDelFile)) {
				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	//读文本文件
	public static String loadStringFromFile(File file, String encoding) throws IOException {
        BufferedReader reader = null; 
        try { 
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding)); 
            StringBuilder builder = new StringBuilder(); 
            char[] chars = new char[4096*10];   
            int length = 0;   
            while (0 < (length = reader.read(chars))) {   
                builder.append(chars, 0, length);   
            }   
            return builder.toString();   
        } finally {   
            try {   
                if (reader != null) reader.close();   
            } catch (IOException e) {   
                throw new RuntimeException(e);   
            }   
        }   
    }  
	//追加文本文件
	public static void appendStringToFile(String content, File file, String encoding) throws IOException { 
        BufferedWriter writer = null; 
        try { 
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), encoding)); 
            writer.write(content); 
        } finally { 
         if (writer != null)   
                writer.close();   
        } 
    }  
   
  //保存文本文件,
    public static void saveStringToFile(String content, File file, String encoding) throws IOException {  
        BufferedWriter writer = null;   
        try {   
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), encoding));   
            writer.write(content);   
        } finally {   
            if (writer != null)   
                writer.close();   
        } 
    } 
    
    
    public static String getDir(String fullPath){//获得文件夹名
		return fullPath.substring( 0,  fullPath.lastIndexOf('\\'));
	}
    
    public static void getFileNames(File dir, ArrayList<String> fileNameList) {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			fileNameList.add(fs[i].getName());
			if (fs[i].isDirectory()) {
				try {
					getFileNames(fs[i],fileNameList);
				} catch (Exception e) {
				}
			}
		}
	}

}
