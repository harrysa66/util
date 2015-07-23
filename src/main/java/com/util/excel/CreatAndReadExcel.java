package com.util.excel;


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.util.exception.ExcelFormatException;

/**
 * 可以从http://poi.apache.org/ 这里下载到POI的jar包 POI 
创建和读取2003-2007版本Excel文件
 * 
 */

public class CreatAndReadExcel {

	protected   Log log = LogFactory.getLog(getClass());
	public static  void main(String[] args) throws Exception {
		CreatAndReadExcel creatAndReadExcel=new CreatAndReadExcel();
		creatAndReadExcel.test();
	}

	/**
	 * 创建2007版Excel文件
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void creat2007Excel() throws FileNotFoundException,
			IOException {
		// HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象

//		sheet.setColumnWidth(1, 10000);// 设置第二列的宽度为

		XSSFRow row = sheet.createRow(1);// 创建一个行对象

//		row.setHeightInPoints(23);// 设置行高23像素

		XSSFCellStyle style = workBook.createCellStyle();// 创建样式对象

		// 设置字体

		XSSFFont font = workBook.createFont();// 创建字体对象

		font.setFontHeightInPoints((short) 15);// 设置字体大小

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体

		font.setFontName("黑体");// 设置为黑体字

		style.setFont(font);// 将字体加入到样式对象

		// 设置对齐方式

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 设置边框

		style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线

		style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色

		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线

		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框

		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框

		// 格式化日期

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		XSSFCell cell = row.createCell(1);// 创建单元格

		cell.setCellValue(new Date());// 写入当前日期

		cell.setCellStyle(style);// 应用样式对象

		// 文件输出流

		FileOutputStream os = new FileOutputStream("style_2007.xlsx");

		workBook.write(os);// 将文档对象写入文件输出流

		os.close();// 关闭文件输出流
		System.out.println("创建成功 office 2007 excel");
	}

	/**
	 * 创建2003版本的Excel文件
	 */
	private static void creat2003Excel() throws FileNotFoundException,
			IOException {
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象

		HSSFSheet sheet = workBook.createSheet();// 创建一个工作薄对象

		sheet.setColumnWidth(1, 10000);// 设置第二列的宽度为

		HSSFRow row = sheet.createRow(1);// 创建一个行对象

		row.setHeightInPoints(23);// 设置行高23像素

		HSSFCellStyle style = workBook.createCellStyle();// 创建样式对象

		// 设置字体

		HSSFFont font = workBook.createFont();// 创建字体对象

		font.setFontHeightInPoints((short) 15);// 设置字体大小

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体

		font.setFontName("黑体");// 设置为黑体字

		style.setFont(font);// 将字体加入到样式对象

		// 设置对齐方式

		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中

		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中

		// 设置边框

		style.setBorderTop(HSSFCellStyle.BORDER_THICK);// 顶部边框粗线

		style.setTopBorderColor(HSSFColor.RED.index);// 设置为红色

		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线

		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框

		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框

		// 格式化日期

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		HSSFCell cell = row.createCell(1);// 创建单元格

		cell.setCellValue(new Date());// 写入当前日期

		cell.setCellStyle(style);// 应用样式对象

		// 文件输出流

		FileOutputStream os = new FileOutputStream("style_2003.xls");

		workBook.write(os);// 将文档对象写入文件输出流

		os.close();// 关闭文件输出流
		System.out.println("创建成功 office 2003 excel");
	}

	
	
	/**
	 * 对外提供读取excel 的方法
	 */
	public   XxlsBig readExcel(File file,int minColumns) throws IOException,ExcelFormatException,Exception {
//		String fileName = uploadFileName;
//		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
//				.substring(fileName.lastIndexOf(".") + 1);
//		extension=extension.toLowerCase();
//		if ("xls".equals(extension)) {
//			return read2003Excel(file);
//		} else if ("xlsx".equals(extension)) {
			String filePath=file.getAbsolutePath();
//			String fileName=file.getName();
			 XxlsBig howto = new XxlsBig(filePath);
			 howto.setMinColumns(minColumns);
//			 howto.isDate1904();
			 return howto;
//		} else {
//			throw new IOException("不支持的文件类型");
//		}
	}

	
	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private  List<List<Object>> read2003Excel(File file)
			throws IOException,ExcelFormatException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb=null;
		try {			
			hwb = new HSSFWorkbook(new FileInputStream(file));
		} catch (OldExcelFormatException e) {
			throw new ExcelFormatException("文件版本太老，仅支持 97/2000/XP/2003");
		}catch (IOException e) {
			throw new ExcelFormatException("文件格式错误");
			
		}
		HSSFSheet sheet = hwb.getSheetAt(0);
		Object value = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		DecimalFormat df = new DecimalFormat("0");// 格式化 number String
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			boolean isAdd=false;
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					isAdd=true;
//					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());

					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					isAdd=true;
//					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					isAdd=true;
//					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
//					System.out.print("  " + value + "  ");
					break;
				default:
					value = cell.toString();
					isAdd=true;
//					System.out.print("  " + value + "  ");
				}
				if (value == null) {
					continue;
				}
				linked.add(value);
			}
			if(isAdd){
				list.add(linked);
			}
		}

		return list;
	}

	/**
	 * 读取Office 2007 excel
	 */

	private  List<List<Object>> read2007Excel(File file)
			throws IOException,ExcelFormatException {

		List<List<Object>> list = new LinkedList<List<Object>>();
		XSSFWorkbook xwb=null;
		long starttime=System.currentTimeMillis();
		log.debug("读取excel数据,读取excel文件开始");
		try{
			xwb = new XSSFWorkbook(new FileInputStream(file));
		} catch (OldExcelFormatException e) {
			throw new ExcelFormatException("文件版本太老，仅支持 97/2000/XP/2003");
		}catch (IOException e) {
			throw new ExcelFormatException("文件格式错误");
		}
		long endtime=System.currentTimeMillis();
		long usetime=(endtime-starttime)/1000;
		log.debug("读取excel数据,读取excel文件结束,用时"+usetime+"秒");
		starttime=System.currentTimeMillis();
		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		DecimalFormat df = new DecimalFormat("0");// 格式化 number String
		// 字符
		SimpleDateFormat sdf = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
		int rows=sheet.getPhysicalNumberOfRows();
		log.debug("读取excel数据开始,共"+rows+"行");
		for (int i = sheet.getFirstRowNum(); i <= rows; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			boolean isAdd=false;
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}

				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					isAdd=true;
//					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());

					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					isAdd=true;
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					value = cell.getBooleanCellValue();
					isAdd=true;
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
					isAdd=true;
				}
				if (value == null) {
					continue;
				}
				linked.add(value);
			}
			if(i>0&&i%10000==0){
				log.debug("读取"+i+"条数据");
			}
//			System.out.println("");
			if(isAdd){
				list.add(linked);				
			}
		}
		endtime=System.currentTimeMillis();
		usetime=(endtime-starttime)/1000;
		log.debug("读取excel数据结束,用时"+usetime+"秒");
		return list;
	}
	public  String createExcel(String parentFoler,String foldername,String filename,String sheetname,List<String> title,List<String>  keys,List<Map<Object,Object>> list) throws Exception{
		String targetfile = null;  
		try {
			
//			if(folderPath==null){
//				folderPath = ServletActionContext.getServletContext().getRealPath("/download/_temp/xlsx");		
//			}else{
//				folderPath+="/download/_temp/xlsx";
//			}
			String folderPath=parentFoler;
			if(parentFoler==null){
//				folderPath = ServletActionContext.getServletContext().getRealPath("/download/_temp/xlsx");
			}
			if(foldername!=null&&foldername.length()>0){
				folderPath+="/"+foldername;
			}
			File folder = new File(folderPath);
			if (!folder.exists()) {	// 判断所在目录是否存在
				folder.mkdirs();
			}
			targetfile = "/download/_temp/xlsx/" +foldername+ "/" +filename+".xlsx";  

			File desFile = new File(folderPath + "/" +filename+".xlsx");
			
			OutputStream os = new FileOutputStream(desFile);    
			XSSFWorkbook wb = new XSSFWorkbook();   //工作区   
			XSSFSheet sheet= wb.createSheet(sheetname);   //创建第一个sheet 
			XSSFRow row1 = sheet.createRow(0); //生成第一行    
			
			for (int i = 0; i < title.size(); i++) {
				row1.createCell(i).setCellValue(title.get(i));   //给这一行的第一列赋值    
			}
			int size=list.size();
			for(int i =0;i<size;i++){
				if((i+1)%1000==0||(i+1)==size){
					log.debug("excel处理"+(i+1));
				}
				XSSFRow row = sheet.createRow(i+1); //生成第一行    
				Map<Object,Object> map=list.get(i);
				for (int j = 0; j < keys.size(); j++) {
					String key=keys.get(j);
					String content="";
					Object o = map.get(key);
					if(o instanceof String)
						content = (String)o;
					if(o instanceof Integer) 
						content = (Integer)o+"";
					if(o instanceof BigInteger)
						content = (BigInteger)o+"";                 			
					if(o instanceof Long)
						content = (Long)o+"";
					if(o instanceof Date) 
						content = ((Date)o).toString();
					if(o instanceof Float) 
						content = (Float)o+"";							
					if(o instanceof Double) 
						content = (Double)o+"";				
					row.createCell(j).setCellValue(content);
				}
			}
			wb.write(os);	//写文件    
			os.close();	//关闭输出流    

			log.debug("excel处理完毕");
		} catch (Exception e) {
			log.debug("excel处理失败"+e.getMessage());
			throw e;
		}
		return targetfile;
	}
	
	public  String createExcel(String foldername,String filename,String sheetname,List<String> title,List<String>  keys,List<Map<Object,Object>> list) throws Exception{
		return createExcel(null,foldername,filename,sheetname,title,keys,list);
	}
	
	public void  test(){
		StringBuffer sb = new StringBuffer();
		try {
			DataOutputStream rafs = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(new File(
							"d://test.xml"))));
			sb.append("<?xml version=\"1.0\"?>");
			sb.append("\n");
			sb.append("<?mso-application progid=\"Excel.Sheet\"?>");
			sb.append("\n");
			sb.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"");
			sb.append("\n");
			sb.append("  xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
			sb.append("\n");
			sb.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
			sb.append("\n");
			sb.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"");
			sb.append("\n");
			sb.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
			sb.append("\n");
			sb.append(" <Styles>\n");
			sb.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">\n");
			sb.append("   <Alignment ss:Vertical=\"Center\"/>\n");
			sb.append("   <Borders/>\n");
			sb.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>\n");
			sb.append("   <Interior/>\n");
			sb.append("   <NumberFormat/>\n");
			sb.append("   <Protection/>\n");
			sb.append("  </Style>\n");
			sb.append(" </Styles>\n");
			int sheetcount = 0;
			int recordcount = 20;
			int currentRecord = 0;
			int total = 100;
			int col = 20;
			sb.append("<Worksheet ss:Name=\"Sheet0\">");
			sb.append("\n");
			sb.append("<Table ss:ExpandedColumnCount=\"" + col
					+ "\" ss:ExpandedRowCount=\"" + total
					+ "\" x:FullColumns=\"1\" x:FullRows=\"1\">");
			sb.append("\n");
			for (int i = 0; i < total; i++) {
				if ((currentRecord == recordcount
						|| currentRecord > recordcount || currentRecord == 0)
						&& i != 0) {// 一个sheet写满
					currentRecord = 0;
					rafs.write(sb.toString().getBytes());
					sb.setLength(0);
					sb.append("</Table>");
					sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
					sb.append("\n");
					sb.append("<ProtectObjects>False</ProtectObjects>");
					sb.append("\n");
					sb.append("<ProtectScenarios>False</ProtectScenarios>");
					sb.append("\n");
					sb.append("</WorksheetOptions>");
					sb.append("\n");
					sb.append("</Worksheet>");
					sb.append("<Worksheet ss:Name=\"Sheet" + i / recordcount
							+ "\">");
					sb.append("\n");
					sb.append("<Table ss:ExpandedColumnCount=\"" + col
							+ "\" ss:ExpandedRowCount=\"" + recordcount
							+ "\" x:FullColumns=\"1\" x:FullRows=\"1\">");
					sb.append("\n");
				}
				sb.append("<Row>");
				for (int j = 0; j < col; j++) {
					System.out.println(i);
					sb.append("<Cell><Data ss:Type=\"String\">111</Data></Cell>");
					sb.append("\n");
				}
				sb.append("</Row>");
				if (i % 5000 == 0) {
					rafs.write(sb.toString().getBytes());
					rafs.flush();
					sb.setLength(0);
				}
				sb.append("\n");
				currentRecord++;
			}
			rafs.write(sb.toString().getBytes());
			sb.setLength(0);
			sb.append("</Table>");
			sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
			sb.append("\n");
			sb.append("<ProtectObjects>False</ProtectObjects>");
			sb.append("\n");
			sb.append("<ProtectScenarios>False</ProtectScenarios>");
			sb.append("\n");
			sb.append("</WorksheetOptions>");
			sb.append("\n");
			sb.append("</Worksheet>");
			sb.append("</Workbook>");
			sb.append("\n");
			rafs.write(sb.toString().getBytes());
			rafs.flush();
			rafs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
