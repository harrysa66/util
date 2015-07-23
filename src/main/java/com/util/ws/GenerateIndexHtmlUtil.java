package com.util.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.util.constant.UtilConstant;

public class GenerateIndexHtmlUtil {
	
	private static Logger log = LoggerFactory.getLogger(GenerateIndexHtmlUtil.class);
	
	public static void generateIndexHtml(String indexHtmlPath, String templateFileName, Integer sysShowtypeId, 
			String[] showtext, String textStyle, String tel, String outUrl, String outUrlen, 
			String clickUrl, String showUrl, String c, String l, Integer w, Integer h) {
			// 建立输出文件对象 
			String file= UtilConstant.TEMPLATE_PATH+templateFileName+".html";//模板文件
		
			File templateFile = new File(file); 
			try {
				String content = FileUtils.readFileToString(templateFile, "utf-8");
				if(sysShowtypeId==8){
					content = content.replaceAll("\\$tel\\$", tel+"");
				}
				if(showtext!=null ){
					if(showtext.length==1){
						content = content.replace("\\$content\\$", showtext[0]);
					}else if(showtext.length==2){
						content = content.replace("\\$title\\$", showtext[0]);
						content = content.replace("\\$content\\$", showtext[1]);
					}
					content = content.replace("\\$style\\$", textStyle);
				}
				content = content.replaceAll("\\$outUrl\\$", outUrl);
				content = content.replaceAll("\\$_outUrl\\$", outUrlen);
				content = content.replaceAll("\\$_clickScript\\$", clickUrl);
				content = content.replaceAll("\\$_showScript\\$", showUrl);
				content = content.replaceAll("\\$iconColor\\$", c); // 0 代表无填充色
				content = content.replaceAll("\\$iconLocation\\$",l); //无角标
				content = content.replaceAll("\\$isGlide\\$", "onclick"); //单机广告跳出
				if(w != null && h !=null) {
					content = content.replaceAll("\\$w\\$", w>h?h+"":w+"");
					content = content.replaceAll("\\$h\\$", w>h?w+"":h+"");
				}else{
					content = content.replaceAll("\\$w\\$", "");
					content = content.replaceAll("\\$h\\$", "");
				}
				
				File folder = new File(indexHtmlPath); 
				if (!folder.exists()) { folder.mkdirs();}
				String filePath = folder +"/index.html";
				File myfile = new File(filePath); 
				OutputStream fos = new FileOutputStream(myfile);
				fos.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
				fos.write(content.getBytes("UTF-8"));
				fos.flush();
				fos.close();
				
			} catch(Exception e) {
				log.error("生成index.html[generateIndexHtml]异常", e);
				throw new RuntimeException("generateIndexHtml 异常");
			}
		
	}
}
