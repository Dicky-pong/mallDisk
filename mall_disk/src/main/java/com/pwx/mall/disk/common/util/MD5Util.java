package com.pwx.mall.disk.common.util;

import java.security.MessageDigest;
/**
 * 
 * <MD5加密>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年6月4日 	Created
 *
 * </pre>
 * @since 1.
 */
public class MD5Util {
    
    /**
     * 
     * TODO MD5加密 生成32位md5码.
     * @param   待加密字符串
     * @return  返回32位md5码
     * @throws Exception
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    
    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("jin741016");
        System.out.println("原始：" + str);
        System.out.println("MD5后：" + md5Encode(str));
    }
    
}
