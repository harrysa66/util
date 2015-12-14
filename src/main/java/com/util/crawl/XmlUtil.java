package com.util.crawl;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;

/**
 * Created by harrysa66 on 2015/12/14.
 */
public class XmlUtil {

    private static final String noResult = "<result>no result</result>";

    /**
     * @param xml
     * @return
     * @Author:lulei
     * @Description: ��java����ת��Ϊxml��ʽ���ַ���
     */
    public static Document createFromString(String xml) {
        try {
            return DocumentHelper.parseText(xml);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param object
     * @return
     * @Author:lulei
     * @Description: ��xml String����ת��Ϊxml����
     */
    public static  String parseObject2XmlString (Object object) {
        if (object == null) {
            return noResult;
        }
        StringWriter sw = new StringWriter();
        JAXBContext jAXBContent;
        Marshaller marshaller;
        try {
            jAXBContent = JAXBContext.newInstance(object.getClass());
            marshaller = jAXBContent.createMarshaller();
            marshaller.marshal(object, sw);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return noResult;
        }
    }

    /**
     * @param xpath
     * @param node
     * @return
     * @Author: lulei
     * @Description: ��ȡָ��xpath���ı���������ʧ�ܷ���null
     */
    public static String getTextFromNode(String xpath,Node node){
        try {
            return node.selectSingleNode(xpath).getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param path
     * @Author: lulei
     * @Description: ��ȡxml�ļ�
     * @return xml�ļ���Ӧ��Document
     */
    public static Document createFromPath(String path){
        return createFromString(readFile(path));
    }

    /**
     * @param path
     * @Author: lulei
     * @Description: ���ļ�
     * @return �����ļ������ַ���
     */
    private static String readFile(String path) {
        File file = new File(path);
        FileInputStream fileInputStream;
        StringBuffer sb = new StringBuffer();
        try {
            fileInputStream = new FileInputStream(file);
            //����ʹ��UTF-8��ȡ����
            String charset = CharsetUtil.getStreamCharset(file.toURI().toURL(), "utf-8");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            while ((s = bufferedReader.readLine()) != null){
                s = s.replaceAll("\t", "").trim();
                if (s.length() > 0){
                    sb.append(s);
                }
            }
            fileInputStream.close();
            bufferedReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
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
