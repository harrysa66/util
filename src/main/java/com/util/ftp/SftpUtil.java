package com.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.util.PropertiesUtil;

/**
 * 支持断点续传的FTP实用类
 * 
 * @author BenZhou
 * @version 0.1 实现基本断点上传下载
 * @version 0.2 实现上传下载进度汇报
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class SftpUtil {
	private String username; // SFTP 登录用户名
	private String password; // SFTP 登录密码
	private String host; // SFTP 服务器地址IP地址
	private int port; // SFTP 端口

	public SftpUtil() {
//		Properties props = new Properties();
//		InputStream in = SftpUtil.class.getResourceAsStream("SFTP.Properties");
//		try {
//			props.load(in);
//		} catch (IOException e) {
//			System.out.println(e.toString());
//			System.out.println("读取FTP配置文件失败");
//		}
		this.username = PropertiesUtil.getProperty("sftp.username");
		this.password = PropertiesUtil.getProperty("sftp.password");
		this.host = PropertiesUtil.getProperty("sftp.host");
		this.port = Integer.parseInt(PropertiesUtil.getProperty("sftp.port"));
	}

	/**
	 * 连接到SFTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public ChannelSftp connect() {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {

		}
		return sftp;
	}

	public void disConnect(ChannelSftp sftp) {
		if (sftp.isConnected())
			sftp.disconnect();
		System.out.println("Session disconnected");
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 * @throws IOException
	 */
	public void upload(String directory, File uploadFile, ChannelSftp sftp)
			throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(uploadFile);
			sftp.cd(directory);
			sftp.put(in, uploadFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
	}

	public void upLoadDir(String directory, String dirName, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rmdir(dirName);
			sftp.mkdir(dirName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAll(String fileName, ChannelSftp sftp) {
		try {
			if (fileName != null && fileName.indexOf(".") == -1) { // 删除文件夹
				sftp.cd(fileName);
				this.deleteDir(sftp);
				sftp.cd("..");
				sftp.rmdir(fileName);
			} else if (fileName != null && fileName.indexOf(".") > 0) { // 删除文件
				this.deleteFile(fileName, sftp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String fileName, ChannelSftp sftp) throws Exception {
		sftp.rm(fileName);
	}

	public void deleteDir(ChannelSftp sftp) throws Exception {
		List<String> files = listAllFiles(sftp);
		for (String fileName : files) {
			if (!"..".equals(fileName) && !".".equals(fileName)) {
				deleteAll(fileName, sftp);
			}
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	/**
	 * 列出当前目录所有文件名称
	 * 
	 * @param directory进入路径
	 * @param regex
	 * @param needFullMatch
	 * @return
	 * @throws SftpException
	 * @throws SPMException
	 */
	public List<String> listAllFiles(ChannelSftp sftp) throws SftpException {
		List<String> ftpFileNameList = new ArrayList<String>();
		Vector<LsEntry> sftpFile = sftp.ls(".");
		LsEntry isEntity = null;
		String fileName = null;
		Iterator<LsEntry> sftpFileNames = sftpFile.iterator();
		while (sftpFileNames.hasNext()) {
			isEntity = (LsEntry) sftpFileNames.next();
			fileName = isEntity.getFilename();
			ftpFileNameList.add(fileName);
		}
		return ftpFileNameList;
	}

	public List<Map<String, String>> uploadAll(String filePath,
			String directory, ChannelSftp sftp) throws SftpException, IOException {
		sftp.cd(directory);
		File file = new File(filePath);
		File[] files = file.listFiles();
		List<Map<String, String>> list = new ArrayList();
		for (File fl : files) {
			if (fl.isFile()) {
				this.upload(".", fl, sftp);
			} else if (fl.isDirectory()) {
				sftp.mkdir(fl.getName());
				Map<String, String> map = new HashMap<String, String>();
				map.put("filePath", filePath + "/" + fl.getName());
				map.put("upPath", directory + "/" + fl.getName());
				list.add(map);
			}
		}
		return list;
	}

	public void upload(List<Map<String, String>> list, ChannelSftp sftp)
			throws SftpException, IOException {
		for (Map<String, String> map : list) {
			System.out.print("filePath" + map.get("filePath") + ",upPath=="
					+ map.get("upPath"));
			upload(uploadAll(map.get("filePath"), map.get("upPath"), sftp),
					sftp);
		}
	}

	public static void main(String[] args) {
		SftpUtil sf = new SftpUtil();
		String host = "42.62.76.5";
		int port = 22;
		String username = "goldbeans";
		String password = "123QWEasd!@#";
		String directory = "/data/ftp/ad/201405/goldbeans";
		String uploadFile = "D:\\123_icon.png";
		// String downloadFile = "upload.txt";
		// String saveFile = "D:\\tmp\\download.txt";
		// String deleteFile = "shijieb.jpg";
		ChannelSftp sftp = sf.connect();
		sf.upload(directory, uploadFile, sftp);
		// sf.download(directory, downloadFile, saveFile, sftp);
		// sf.delete(directory, deleteFile, sftp);
		try {
			sftp.cd(directory);
			// sftp.cd("jddFile");
			// sftp.ls(".");
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("filePath", uploadFile);
			// map.put("upPath", directory);
			// List<Map<String, String>> list = new ArrayList<Map<String,
			// String>>();
			// list.add(map);
			// sf.uploadTest(list, sftp);
			sf.deleteAll("jddFile", sftp);
			// sftp.rmdir("image");
			// Vector v = sftp.ls(".");
			// System.out.println("v ==" + v);
			// sf.disConnect(sftp);
			System.out.println("finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}