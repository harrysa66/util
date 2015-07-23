package com.util.excel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XxlsBig extends XxlsAbstract {
	public static void main(String[] args) throws Exception {
//		DateWindow1904Record  s=new DateWindow1904Record(in)
//		System.out.println(s.getWindowing());;
//		
		XxlsBig howto = new XxlsBig("D:/test0218.xlsx");
//		XxlsBig howto = new XxlsBig("D:/new.xlsx");
		  // 设置密 码 保 护 ·
//		   wb.writeProtectWorkbook("password", "owner");
		 howto.setMinColumns(2);
		List<List<Object>> list=howto.readFile();

		
		
//		 Date date = HSSFDateUtil.getJavaDate(39850,true);
//		 System.out.println(date);
	}
	
	public XxlsBig(String fileName) throws SQLException{
//		this.conn = getNew_Conn();
//		this.statement = conn.createStatement();
//		this.tableName = tableName;
		this.fileName=fileName;
		
	}

	private String fileName;
//	private Connection conn = null;
//	private Statement statement = null;
//	private PreparedStatement newStatement = null;
//
//	private String tableName = "temp_table";
//	private boolean create = true;

	public List<List<Object>> readFile()throws Exception{
//		processOneSheet(fileName,1);
		process(fileName);
		return list;
	}
	List<List<Object>> list = new LinkedList<List<Object>>();
	public void optRows( List<Object> rowlist) throws SQLException {
		List<Object> list_tem=new ArrayList<Object>();
		for (int i = 0; i < rowlist.size(); i++) {
			list_tem.add(rowlist.get(i));
		}
		list.add(list_tem);
	}
	
    
}
