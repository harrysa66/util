package com.util.ws;

import java.util.Date;
import java.util.Map;

public class MapUtil {
	public static double getDouble(Map<Object,Object> map,String key) {
		try {
			if(map==null||map.get(key)==null){
				return 0;
			}else{
				return Double.parseDouble(map.get(key).toString());
			}
		} catch (Exception e) {
			return 0;
		}
//		double paymoney=Double.parseDouble(map.get("paymoney").toString());
	}
	public static float getFloat(Map<Object,Object> map,String key) {
		try {
			if(map==null||map.get(key)==null){
				return 0;
			}else{
				return Float.parseFloat(map.get(key).toString());
			}
		} catch (Exception e) {
			return 0;
		}
//		double paymoney=Double.parseDouble(map.get("paymoney").toString());
	}
	public static String getString(Map<Object,Object> map,String key){
		return getString(map,key,null);
	}	
	public static String getString(Map<Object,Object> map,String key,String defaultvalue){
		try {
			if(map==null||map.get(key)==null){
				return defaultvalue;
			}else{
				return map.get(key).toString();
			}
		} catch (Exception e) {
			return defaultvalue;
		}
	}
	public static int getInt(Map<Object,Object> map,String key){
		return getInt(map,key,0);
	}
	public static int getInt(Map<Object,Object> map,String key,int defaultValue){
		try {
			if(map==null||map.get(key)==null){
				return defaultValue;
			}else{
				return Integer.parseInt(map.get(key).toString());
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static long getLong(Map<Object,Object> map,String key){
		return getLong(map,key,0);
	}
	public static long getLong(Map<Object,Object> map,String key,long defaultValue){
		try {
			if(map==null||map.get(key)==null){
				return defaultValue;
			}else{
				return Long.parseLong(map.get(key).toString());
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static boolean getBoolean(Map<Object,Object> map,String key){
		return getBoolean(map, key,false);
	}
	public static boolean getBoolean(Map<Object,Object> map,String key,boolean defaultValue){
		try {
			if(map==null||map.get(key)==null){
				return defaultValue;
			}else{
				return Boolean.parseBoolean(map.get(key).toString());
			}
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static  Date getDate(Map<Object,Object> map,String key){
		try {
			if(map==null||map.get(key)==null){
				return null;
			}else{
				return (Date)map.get(key);
			}
		} catch (Exception e) {
			return null;
		}
	}
}
