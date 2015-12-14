package com.util.crawl;

import java.security.MessageDigest;

/**
 * Created by harrysa66 on 2015/12/14.
 */
public class Encrypt {

    //MD5����
    public static final String MD5 = "MD5";
    //SHA-1����
    public static final String SHA1 = "SHA-1";
    //SHA-256����
    public static final String SHA256 = "SHA-256";

    /**
     * @param str
     * @param encName ����������
     * @return
     * @Author:lulei
     * @Description: ���ַ�������
     */
    public static String encrypt(String str, String encName) {
        String reStr = null;
        try {
            //������
            MessageDigest digest = MessageDigest.getInstance(encName);
            byte[] bytes = digest.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b&0xff;
                //���С��16����λһ��0
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }

            reStr = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     * @param args
     * @Author:lulei
     * @Description:
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }
}
