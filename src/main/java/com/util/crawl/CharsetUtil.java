package com.util.crawl;

import info.monitorenter.cpdetector.io.*;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by harrysa66 on 2015/12/14.
 */
public class CharsetUtil {

    //����̽����
    private static final CodepageDetectorProxy detector;

    //��ʼ������̽����
    static {
        detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        detector.add(JChardetFacade.getInstance());
    }

    /**
     * @param url
     * @param defaultCharset
     * @return
     * @Author:lulei
     * @Description: ���URL�µı��뷽ʽ���������ڼ���ļ�
     */
    public static String getStreamCharset(URL url, String defaultCharset){
        if (url == null) {
            return defaultCharset;
        }

        try {
            Charset charset = detector.detectCodepage(url);
            if (charset != null) {
                return charset.name();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultCharset;
    }

    /**
     * @param inputStream
     * @param defaultCharset
     * @return
     * @Author:lulei
     * @Description: ���InputStream�ı��뷽ʽ
     */
    public static String getStreamCharset(InputStream inputStream, String defaultCharset){
        if (inputStream == null) {
            return defaultCharset;
        }
        int count = 200;
        try {
            count = inputStream.available();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Charset charset = detector.detectCodepage(inputStream, count);
            if (charset != null) {
                return charset.name();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return defaultCharset;
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
