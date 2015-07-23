package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class LinuxSHUtil {
	private String outputInfo;
	private String errorInfo;
	
	public String getOutInfo(){
		return outputInfo;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}
	
	
	public  int exeSH(String shpath) throws Exception{
		Process proc=null;
		try {
			String cmd="bash "+shpath;
			System.out.println("cmd==============================="+cmd);
			proc = Runtime.getRuntime().exec(cmd);
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());           
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
			errorGobbler.start();
			outputGobbler.start();
			
	        int exitValue=proc.waitFor();
	        errorGobbler.join();
	        outputGobbler.join();
	        outputInfo=outputGobbler.output;
	        errorInfo=errorGobbler.output;
	        
	        return exitValue;
	        
		} catch (IOException e) {
			throw new Exception("执行sh文件"+shpath+"时发生IO异常"+e.getMessage());
		}catch (InterruptedException e2) { 
			throw new Exception("执行sh文件"+shpath+"时发生InterruptedException异常"+e2.getMessage());
		}catch (Exception e3) {
			throw new Exception("执行sh文件"+shpath+"时发生异常"+e3.getMessage());
		}finally{
			//程序执行到exec的时候，JVM会使用管道，占有3个文件句柄
			//，但程序运行结束后，这三个句柄并不会自动关闭，这样最终会导致java.io.IOException: Too many open files.
			//所以就算外部程序的没有输出，也必须关闭句柄：
			try {
				if(proc!=null){
					proc.getErrorStream().close();
					proc.getInputStream().close();
					proc.getOutputStream().close();
				}				
			} catch (IOException e) {
				throw new Exception("执行sh文件"+shpath+"时关闭通道句柄发生IO错误."+e.getMessage());
			}
		}
	}
	public  int exeSH(String shpath,String paras) throws Exception{
		Process proc=null;
		try {
			String cmd="bash "+shpath+" "+paras;
			
			proc = Runtime.getRuntime().exec(cmd);
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream());           
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
			errorGobbler.start();
			outputGobbler.start();
			
	        int exitValue=proc.waitFor();
	        errorGobbler.join();
	        outputGobbler.join();
	        outputInfo=outputGobbler.output;
	        errorInfo=errorGobbler.output;
	        
	        return exitValue;
	        
		} catch (IOException e) {
			throw new Exception("执行sh文件"+shpath+"时发生IO异常"+e.getMessage());
		}catch (InterruptedException e2) { 
			throw new Exception("执行sh文件"+shpath+"时发生InterruptedException异常"+e2.getMessage());
		}catch (Exception e3) {
			throw new Exception("执行sh文件"+shpath+"时发生异常"+e3.getMessage());
		}finally{
			//程序执行到exec的时候，JVM会使用管道，占有3个文件句柄
			//，但程序运行结束后，这三个句柄并不会自动关闭，这样最终会导致java.io.IOException: Too many open files.
			//所以就算外部程序的没有输出，也必须关闭句柄：
			try {
				if(proc!=null){
					proc.getErrorStream().close();
					proc.getInputStream().close();
					proc.getOutputStream().close();
				}				
			} catch (IOException e) {
				throw new Exception("执行sh文件"+shpath+"时关闭通道句柄发生IO错误."+e.getMessage());
			}
		}
	}
	public static void main(String[] args) {
		LinuxSHUtil shUtil=new LinuxSHUtil();
		String shpath="/data/webroot/admin/WEB-INF/classes/cdnsh/test.sh";
		try {			
			shUtil.exeSH(shpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class StreamGobbler extends Thread {
	InputStream is;
	String output;

	StreamGobbler(InputStream is) {
		this.is = is;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuilder info=new StringBuilder();
			while ((line = br.readLine()) != null) {
				
				info.append(line+"\r\n");
			}
			output=info.toString();
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			System.err.println("StreamGobbler获取字符串时发生io异常"+ioe.getMessage());
		}catch (Exception e) {
			System.err.println("StreamGobbler获取字符串时发生异常"+e.getMessage());
		}
	}
	
	
}
