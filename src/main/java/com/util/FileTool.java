package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.cobin.util.CDate;

public class FileTool {

	
	public static void WriteLine(String filename, String content) throws Exception  {
		WriteLine(filename,content,"utf-8");
	}	
	public static List<String> readFileByLines(String fileName,int lineNum) throws Exception {
		return readFileByLines(fileName,lineNum,",","utf-8");
	}
	
	/**
	 * 新建目录
	 */
	public static void CreateFolder(String folderPath)  throws Exception{
		try {
			File myFilePath = new File(folderPath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			throw new Exception("新建目录"+folderPath+"操作出错:"+getLogErrorInfo(e));
		}
	}
	
	public static void WriteLine(String filename, String content,String encoding) throws Exception  {
		FileWriter fileWriter=null;
		try {
//			String   crlf=System.getProperty("line.separator");
			String   crlf="\r\n";
			content = content + crlf;
			File file = new File(filename);
			fileWriter = new FileWriter(file, true);
			fileWriter.write(new String(content.getBytes(encoding)));			
		}catch (Exception e) {
			throw new Exception("写入文件"+filename+",写入内容:"+content+"时出错"+getLogErrorInfo(e));
		}finally{
			if(fileWriter!=null){
				try {
					fileWriter.close();
				} catch (Exception e1) {
				}
			}
		}
	}
	/**
	 * 以行为单位读取文件 
	 * @param fileName文件名
	 */
	public static List<String> readFileByLines(String fileName,int lineNum,String strTail,String encoding) throws Exception {
		BufferedReader reader = null; 
		StringBuilder strLine =new StringBuilder();
		List<String> list=new ArrayList<String>();
		
		try {
			File file = new File(fileName);
			if (file.exists()) {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encoding));
				int line = 1;
				// 一次读入一行，直到读入null为文件结束
				String strTemp=null;
				while ((strTemp = reader.readLine()) != null&&line<=lineNum) {
					strLine.append(strTemp).append(strTail);
					if(line%lineNum==0){
						strLine.deleteCharAt(strLine.length()-1);
						list.add(strLine.toString());
						strLine =new StringBuilder();
					}
					line++;
				}
				
				if(strLine.length()>0){
					strLine.deleteCharAt(strLine.length()-1);
					list.add(strLine.toString());
				}
			}
		} catch (Exception e) {
			throw new Exception("读取文件"+fileName+"时出错"+getLogErrorInfo(e));
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}
	public static String getLogErrorInfo(Exception e) {
		StackTraceElement[] elements=e.getStackTrace();
		StringBuilder logerror=new StringBuilder(e.toString()+"\r\n");
		for (int i = 0; i < elements.length; i++) {
			logerror.append(elements[i].toString()+"\r\n");
		}
		return logerror.toString();
	}
	
	public static String getCurShortTimeDir() {
		return getCurShortTimeDir(0);
	}
	public static String getCurShortTimeDir(int addHour) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd/HH"); //格式化当前系统日期
		String dateTime = formater.format(addHour==0?new CDate():(new CDate()).addHour(addHour));
		return dateTime;
	}
	
	public static void moveFile(String srcFilePath, String destFilePath)throws Exception{
		try {
			File srcFile = new File(srcFilePath);
			File destFile = new File(destFilePath);
			FileUtils.moveFile(srcFile, destFile);
		}catch (Exception e) {
			throw new Exception("移动文件从"+srcFilePath+"到"+destFilePath+"时出错"+getLogErrorInfo(e));
		}
	}
	  public static boolean copy(String srcFilePath, String destFilePath) throws Exception{
//	        long time=new Date().getTime();
		  File srcfile = new File(srcFilePath);
			File destfile = new File(destFilePath);
	        int length=2097152;
	        FileInputStream in=new FileInputStream(srcfile);
	        FileOutputStream out=new FileOutputStream(destfile);
	        FileChannel inC=in.getChannel();
	        FileChannel outC=out.getChannel();
	        ByteBuffer b=null;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
//	                return new Date().getTime()-time;
	                return true;
	            }
	            if((inC.size()-inC.position())<length){
	                length=(int)(inC.size()-inC.position());
	            }else
	                length=2097152;
	            b=ByteBuffer.allocateDirect(length);
	            inC.read(b);
	            b.flip();
	            outC.write(b);
	            outC.force(false);
	        }
	    }
	public static boolean exist(String filepath)throws SecurityException  {
		//try {
			File myFilePath = new File(filepath);
			return myFilePath.exists();
		//} catch (SecurityException  e) {
			
			//throw new Exception("查看文件"+filepath+"是否存在出错:"+getLogErrorInfo(e));
		//}
	}
//	public static String getCurShortDayDir(int addHour) {
//		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd"); //格式化当前系统日期
//		String dateTime = formater.format(new CDate());
//		return dateTime;
//	}
	
	public static void main(String[] args) {
		String   crlf=System.getProperty("line.separator");
		System.out.println(crlf);
	}
}
