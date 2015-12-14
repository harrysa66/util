package com.util.crawl;

/**
 * Created by harrysa66 on 2015/12/14.
 */
public class ClassUtil {

    /**
     * @param c
     * @return
     * @Author:lulei
     * @Description: ����class�ļ����ڵ�Ŀ¼
     */
    public static String getClassPath(Class<?> c) {
        return c.getResource("").getPath().replaceAll("%20", " ");
    }

    /**
     * @param c
     * @return
     * @Author:lulei
     * @Description: ����class�ļ�������Ŀ�ĸ�Ŀ¼
     */
    public static String getClassRootPath(Class<?> c) {
        return c.getResource("/").getPath().replaceAll("%20", " ");
    }

    /**
     * @param c
     * @param hasName �Ƿ����class��
     * @return
     * @Author:lulei
     * @Description: ����class�ļ����ڵ�Ŀ¼
     */
    public static String getClassPath(Class<?> c, boolean hasName) {
        String name = c.getSimpleName() + ".class";
        String path = c.getResource(name).getPath().replaceAll("%20", " ");
        if (hasName) {
            return path;
        } else {
            return path.substring(0, path.length() - name.length());
        }
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
