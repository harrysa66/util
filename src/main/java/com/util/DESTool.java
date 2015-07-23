package com.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESTool {

	
	//二十四字节
	private final static String DES = "DES";
	 
 
    public static byte[] encrypt(byte[] src ,String key )throws Exception {
          byte[] kbs=key.getBytes();   
    	  DESKeySpec dks = new DESKeySpec(kbs);
          SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	      SecretKey  securekey = keyFactory.generateSecret(dks);

		  Cipher cipher = Cipher.getInstance(DES);
          cipher.init(Cipher.ENCRYPT_MODE, securekey);
		 return cipher.doFinal(src);
	 }
	 public static byte[] decrypt(byte[] src,String key )throws Exception {
         byte[] kbs=key.getBytes();   
   	     DESKeySpec dks = new DESKeySpec(kbs);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
	      SecretKey  securekey = keyFactory.generateSecret(dks);
	      
		Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey);
		return cipher.doFinal(src);
	}
	public final static String decrypt(String data,String key){
		try {
		  return new String(decrypt(hex2byte(data.getBytes()) ,  key ));
		}catch(Exception e) {
		}
		return null;
	}
	public final static String encrypt(String password,String key){
		try {
		  return byte2hex(encrypt(password.getBytes() ,  key)); }catch(Exception e) {
		}
		return null;
	}
	public static String byte2hex(byte[] b) {
       String hs = "";
       String stmp = "";
       for (int n = 0; n < b.length; n++) {
           stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
           if (stmp.length() == 1)
               hs = hs + "0" + stmp;
           else
               hs = hs + stmp;
       }
       return hs.toUpperCase();
  }
	public static byte[] hex2byte(byte[] b) {

       if((b.length%2)!=0)
          throw new IllegalArgumentException("IllegalArgumentException");
           byte[] b2 = new byte[b.length/2];
           for (int n = 0; n < b.length; n+=2) {
             String item = new String(b,n,2);
             b2[n/2] = (byte)Integer.parseInt(item,16);
           }
       return b2;
	}
	
	public static void main(String args[]){
 		String PASSWORD_CRYPT_KEY = "f975579865159750bdb73f87af31fee9"; 
	     
 		 
		//String pp="4432,1,373,100,1,1295673744247,1,000000000000000,0.5";
 		String pp = "12324321";
		String ds1 = encrypt(pp,PASSWORD_CRYPT_KEY);
		System.out.println(ds1);
		
		 
		//String p0="E87199062CA20E815FD8D3912DCA16093A38237C443204E723D2BAEF441CF33AFFEDAC02B609547387E4FE92D27A61B74CC9B95F9B0F66DC";
		String p0="F5D3356845EFBC98ECF0D439C7C19201";
		//4432,1,373,100,1,1295673744247,1,000000000000000,0.5
		String ds = decrypt(p0,PASSWORD_CRYPT_KEY);
		System.out.println(ds);
		
	}
	
}
