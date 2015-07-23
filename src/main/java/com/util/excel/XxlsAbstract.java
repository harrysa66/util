package com.util.excel;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

//import com.gaosheng.util.CopyOfXLSX2CSV.xssfDataType;

/**
 * XSSF and SAX (Event API)
 */
public abstract class XxlsAbstract extends DefaultHandler {
	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;
	private int thisColumn = -1;
	private int lastColumnNumber = -1;
	private int minColumns=-1;

	private int sheetIndex = -1;
	private List<Object> rowlist = new ArrayList<Object>();
	private int curRow = 0;
	private int curCol = 0;
	//日期标志
//	private boolean dateFlag;
	
	private boolean isTElement;
	boolean date1904=false;

	
	public boolean isDate1904() {
		return date1904;
	}

	// Used to format numeric cell values.
	public void setMinColumns(int minColumns){
		this.minColumns=minColumns;
	}
	//excel记录行操作方法，以行索引和行元素列表为参数，对一行元素进行操作，元素为String类型
//	public abstract void optRows(int curRow, List<String> rowlist) throws SQLException ;
	
	//excel记录行操作方法，以sheet索引，行索引和行元素列表为参数，对sheet的一行元素进行操作，元素为String类型
	public abstract void optRows(List<Object> rowlist) throws SQLException;
	
	//只遍历一个sheet，其中sheetId为要遍历的sheet索引，从1开始，1-3
	public void processOneSheet(String filename,int sheetId) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		
		XMLReader parser = fetchSheetParser(sst);

		// rId2 found by processing the Workbook
		// 根据 rId# 或 rSheet# 查找sheet
		InputStream sheet2 = r.getSheet("rId"+sheetId);
		sheetIndex++;
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	/**
	 * 遍历 excel 文件
	 */
	public void process(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		
		SharedStringsTable sst = r.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sst);
		
		//是否1904日期格式
		InputStream inputStream_workbook=r.getWorkbookData();		
		InputSource sheetSource_workbook = new InputSource(inputStream_workbook);
		parser.parse(sheetSource_workbook);
		
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		this.sst = sst;
		parser.setContentHandler(this);
//		System.out.println(sst.getItems());
		return parser;
	}

	private int nameToColumn(String name) {
		int column = -1;
		for (int i = 0; i < name.length(); ++i) {
			int c = name.charAt(i);
			column = (column + 1) * 26 + c - 'A';
		}
		return column;
	}
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		
		// c => 单元格
		if("workbookPr".equals(name)){
			String datetype = attributes.getValue("date1904");
			if(datetype!=null&&datetype.equals("1")){
				date1904=true;
			}
		}else if ("c".equals(name)) {
			String r = attributes.getValue("r");
			int firstDigit = -1;
			for (int c = 0; c < r.length(); ++c) {
				if (Character.isDigit(r.charAt(c))) {
					firstDigit = c;
					break;
				}
			}
			thisColumn = nameToColumn(r.substring(0, firstDigit));
			
			// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
			String cellType = attributes.getValue("t");
			if ("s".equals(cellType)) {
				nextIsString = true;
			} else {
				nextIsString = false;
				//日期格式
				cellType = attributes.getValue("s");
//				if (cellType!=null){
//					dateFlag = true;
//				} else {
//					dateFlag = false;
//				}
			}
			
			
			
		}
		//当元素为t时
		if("t".equals(name)){
			isTElement = true;
		} else {
			isTElement = false;
		}
		
		// 置空
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		
		// 根据SST的索引值的到单元格的真正要存储的字符串
		// 这时characters()方法可能会被调用多次
		if (nextIsString) {
			try {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
						.toString();
			} catch (Exception e) {

			}
		} 
		//t元素也包含字符串
		if(isTElement){
			String value = lastContents.trim();
			rowlist.add(curCol, value);
			curCol++;
			isTElement = false;
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
		} else if ("v".equals(name)) {
			String value = lastContents.trim();
			value = value.equals("")?" ":value;
//			日期格式处理
//			if(dateFlag){
//				 Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value),date1904);
//				 SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
//				 value = dateFormat.format(date);
//			} 
			
//			value=String.valueOf(Double.valueOf(value));
			
			if (lastColumnNumber == -1) {
				lastColumnNumber = 0;
			}
			
			int diffcolumn=thisColumn-lastColumnNumber-1;
			if(diffcolumn>0){
				for (int i = 1; i <= diffcolumn; i++) {
					rowlist.add(curCol,"");
					curCol++;
				}
			}
			
			rowlist.add(curCol, value);
			lastColumnNumber=thisColumn;
			curCol++;
		}else {
			//如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
			if (name.equals("row")) {
//				rowReader.getRows(sheetIndex,curRow,rowlist);
				
				if (minColumns > 0) {
					// Columns are 0 based
					if (lastColumnNumber == -1) {
						lastColumnNumber = 0;
					}
				}
				int diffcolumn=(minColumns-1)-lastColumnNumber;
				if(diffcolumn>0){
					for (int i = 1; i <= diffcolumn; i++) {
						rowlist.add(curCol,"");
						curCol++;
					}
				}
				
				try {
					optRows(rowlist);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				rowlist.clear();
				curRow++;
				curCol = 0;
				lastColumnNumber = -1;
			}
		}
		nextIsString = false;
//		dateFlag=false;
	}
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//得到单元格内容的值
		lastContents += new String(ch, start, length);
	}
}
