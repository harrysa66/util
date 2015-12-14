package com.util.crawl;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by harrysa66 on 2015/12/14.
 */
public class JsonUtil {

    //Ĭ��json�ַ�����nullֵ����������·��ظ�ֵ
    private static final String noData = "{\"result\" : null}";
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        //�������ֵΪ�գ�ֱ�Ӻ���
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    /**
     * @param object
     * @return
     * @Author:lulei
     * @Description: ��objectת��Ϊjson�ַ���
     */
    public static String parseJson(Object object) {
        if (object == null) {
            return noData;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return noData;
        }
    }

    /**
     * @param json
     * @return
     * @Author:lulei
     * @Description: ��json�ַ���ת��ΪJsonNode
     */
    public JsonNode json2Object (String json) {
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * @param obj
     * @param root
     * @return ������ʧ�ܷ���{datas:null}
     * @Author: lulei
     * @Description:����java�������ɶ�Ӧjson,����ָ��һ��json��root��
     */
    public static String parseJson(Object obj, String root){

        if(obj == null){
            return noData;
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"");
            sb.append(root);
            sb.append("\":");
            sb.append(mapper.writeValueAsString(obj));
            sb.append("}");
            return sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return noData;
        }
    }

    /***
     * @param json
     * @param var
     * @return ������varΪnull����Ĭ�ϱ�����Ϊdatas
     * @Author: lulei
     * @Description:��json�ַ�����װ��jsonp������var data={}��ʽ
     */
    public static String wrapperJsonp(String json, String var){
        if(var == null){
            var = "datas";
        }
        return new StringBuilder().append("var ").append(var).append("=").append(json).toString();
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
