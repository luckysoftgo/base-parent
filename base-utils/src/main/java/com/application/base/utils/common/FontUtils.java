package com.application.base.utils.common;

/** 
* @desc: Unicode与中文互转工具类
* @author 孤狼
*/
public class FontUtils {
    
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String str = "{\"code\": \"200\", \"data\": {\"BASIC\": {\"OPSCOPE\": \"\\u6c7d\\u8f66\\u3001\\u7535\\u52a8\\u8f66\\u53ca\\u96f6\\u90e8\\u4ef6\\u3001\\u6c7d\\u8f66\\u53d1\\u52a8\\u673a\\u53ca\\u96f6\\u90e8\\u4ef6\\u3001\\u8f68\\u9053\\u4ea4\\u901a\\u8f66\\u8f86\\u3001\\u5de5\\u7a0b\\u673a\\u68b0\\u3001\\u5404\\u7c7b\\u673a\\u7535\\u8bbe\\u5907\\u3001\\u7535\\u5b50\\u8bbe\\u5907\\u53ca\\u96f6\\u90e8\\u4ef6\\u3001\\u7535\\u5b50\\u7535\\u6c14\\u4ef6\\u7684\\u5f00\\u53d1\\u3001\\u7814\\u5236\\u3001\\u8bbe\\u8ba1\\u3001\\u751f\\u4ea7\\u3001\\u9500\\u552e\\u53ca\\u552e\\u540e\\u670d\\u52a1\\uff1b\\u7ecf\\u8425\\u672c\\u4f01\\u4e1a\\u81ea\\u4ea7\\u4ea7\\u54c1\\u7684\\u51fa\\u53e3\\u4e1a\\u52a1\\uff1b\\u672c\\u4f01\\u4e1a\\u6240\\u9700\\u7684\\u673a\\u68b0\\u8bbe\\u5907\\u3001\\u96f6\\u914d\\u4ef6\\u3001\\u539f\\u8f85\\u6750\\u6599\\u7684\\u8fdb\\u53e3\\u4e1a\\u52a1\\uff1b\\u5404\\u7c7b\\u7535\\u5b50\\u5143\\u5668\\u4ef6\\u3001\\u7167\\u660e\\u706f\\u5177\\u3001\\u98ce\\u529b\\u53d1\\u7535\\u673a\\u53ca\\u592a\\u9633\\u80fd\\u76f4\\u6d41\\u53d1\\u7535\\u673a\\u7684\\u9500\\u552e\\u3001\\u6279\\u53d1\\uff1b\\u81ea\\u6709\\u8d44\\u4ea7\\u79df\\u8d41\\uff1b\\u6c7d\\u8f66\\u7ef4\\u4fee\\u6280\\u672f\\u54a8\\u8be2\\uff1b\\u4f4f\\u5bbf\\u4e1a\\uff1b\\u7269\\u4e1a\\u7ba1\\u7406\\uff1b\\u666e\\u901a\\u8d27\\u7269\\u4ed3\\u50a8\\u3001\\u7269\\u6d41\\u4ee3\\u7406\\u670d\\u52a1\\uff1b\\u88c5\\u5378\\u642c\\u8fd0\\uff1b\\u666e\\u901a\\u8d27\\u7269\\u9053\\u8def\\u8fd0\\u8f93\\uff1b\\u5305\\u88c5\\u670d\\u52a1\\u3002\\uff08\\u4ee5\\u4e0a\\u7ecf\\u8425\\u8303\\u56f4\\u4e0d\\u5f97\\u6d89\\u53ca\\u5916\\u5546\\u6295\\u8d44\\u51c6\\u5165\\u7279\\u522b\\u7ba1\\u7406\\u63aa\\u65bd\\u8303\\u56f4\\u5185\\u7684\\u9879\\u76ee\\uff09\\uff08\\u4f9d\\u6cd5\\u987b\\u7ecf\\u6279\\u51c6\\u7684\\u9879\\u76ee\\uff0c\\u7ecf\\u76f8\\u5173\\u90e8\\u95e8\\u6279\\u51c6\\u540e\\u65b9\\u53ef\\u5f00\\u5c55\\u7ecf\\u8425\\u6d3b\\u52a8\\uff09\", \"ENTNAME\": \"\\u6bd4\\u4e9a\\u8fea\\u6c7d\\u8f66\\u6709\\u9650\\u516c\\u53f8\", \"REGORG\": \"\\u897f\\u5b89\\u5e02\\u5de5\\u5546\\u884c\\u653f\\u7ba1\\u7406\\u5c40\\u9ad8\\u65b0\\u5206\\u5c40\", \"DOM\": \"\\u897f\\u5b89\\u5e02\\u9ad8\\u65b0\\u533a\\u8349\\u5802\\u79d1\\u6280\\u4ea7\\u4e1a\\u57fa\\u5730\\u79e6\\u5cad\\u5927\\u9053\\u897f1\\u53f7\", \"REGCAP\": 135101.0101, \"ENTTYPE_id\": \"1122\", \"ESDATE\": \"1997-03-21\", \"ENTSTATUS\": \"\\u5728\\u8425\", \"region_id\": \"610131\", \"OPTO\": \"-\", \"RECCAP\": 135101.0101, \"nic_id\": \"C361\", \"OPFROM\": \"2006-03-10\", \"OPSCOANDFORM\": \"\\u6c7d\\u8f66\\u3001\\u7535\\u52a8\\u8f66\\u53ca\\u96f6\\u90e8\\u4ef6\\u3001\\u6c7d\\u8f66\\u53d1\\u52a8\\u673a\\u53ca\\u96f6\\u90e8\\u4ef6\\u7684\\u5f00\\u53d1\\u3001\\u7814\\u5236\\u3001\\u8bbe\\u8ba1\\u3001\\u751f\\u4ea7\\u3001\\u9500\\u552e\\u53ca\\u552e\\u540e\\u670d\\u52a1\\uff1b\\u7ecf\\u8425\\u672c\\u4f01\\u4e1a\\u81ea\\u4ea7\\u4ea7\\u54c1\\u7684\\u51fa\\u53e3\\u4e1a\\u52a1\\uff0c\\u672c\\u4f01\\u4e1a\\u6240\\u9700\\u7684\\u673a\\u68b0\\u8bbe\\u5907\\u3001\\u96f6\\u914d\\u4ef6\\u3001\\u539f\\u8f85\\u6750\\u6599\\u7684\\u8fdb\\u53e3\\u4e1a\\u52a1\\u3002\\u5404\\u7c7b\\u7535\\u5b50\\u5143\\u5668\\u4ef6\\u3001\\u7167\\u660e\\u706f\\u5177\\uff1b\\u98ce\\u529b\\u53d1\\u7535\\u673a\\u53ca\\u592a\\u9633\\u80fd\\u76f4\\u6d41\\u53d1\\u7535\\u673a\\u7684\\u9500\\u552e\\u3001\\u6279\\u53d1\\u3002\\uff08\\u4ee5\\u4e0a\\u7ecf\\u8425\\u8303\\u56f4\\u51e1\\u6d89\\u53ca\\u56fd\\u5bb6\\u6709\\u4e13\\u9879\\u4e13\\u8425\\u89c4\\u5b9a\\u7684\\u4ece\\u5176\\u89c4\\u5b9a\\uff09\", \"OPLOC\": \"\\u897f\\u5b89\\u5e02\\u9ad8\\u65b0\\u533a\\u65b0\\u578b\\u5de5\\u4e1a\\u56ed\\u4e9a\\u8fea\\u8def\\u4e8c\\u53f7\", \"ABUITEM\": \"\\u6c7d\\u8f66\\u3001\\u7535\\u52a8\\u8f66\\u53ca\\u96f6\\u90e8\\u4ef6\\u3001\\u6c7d\\u8f66\\u53d1\\u52a8\\u673a\\u53ca\\u96f6\\u90e8\\u4ef6\\u7684\\u5f00\\u53d1\\u3001\\u7814\\u5236\\u3001\\u8bbe\\u8ba1\\u3001\\u751f\\u4ea7\\u3001\\u9500\\u552e\\u53ca\\u552e\\u540e\\u670d\\u52a1\\uff1b\\u7ecf\\u8425\\u672c\\u4f01\\u4e1a\\u81ea\\u4ea7\\u4ea7\\u54c1\\u7684\\u51fa\\u53e3\\u4e1a\\u52a1\\uff0c\\u672c\\u4f01\\u4e1a\\u6240\\u9700\\u7684\\u673a\\u68b0\\u8bbe\\u5907\\u3001\\u96f6\\u914d\\u4ef6\\u3001\\u539f\\u8f85\\u6750\\u6599\\u7684\\u8fdb\\u53e3\\u4e1a\\u52a1\\u3002\\u5404\\u7c7b\\u7535\\u5b50\\u5143\\u5668\\u4ef6\\u3001\\u7167\\u660e\\u706f\\u5177\\uff1b\\u98ce\\u529b\\u53d1\\u7535\\u673a\\u53ca\\u592a\\u9633\\u80fd\\u76f4\\u6d41\\u53d1\\u7535\\u673a\\u7684\\u9500\\u552e\\u3001\\u6279\\u53d1\\uff1b\\u81ea\\u6709\\u623f\\u5c4b\\u79df\\u8d41\\uff1b\\u6c7d\\u8f66\\u7ef4\\u4fee\\u6280\\u672f\\u54a8\\u8be2\\uff1b\\u4f4f\\u5bbf\\u4e1a\\uff1b\\u7269\\u4e1a\\u7ba1\\u7406\\u3002\\uff08\\u4e0a\\u8ff0\\u7ecf\\u8425\\u8303\\u56f4\\u6d89\\u53ca\\u8bb8\\u53ef\\u7ecf\\u8425\\u9879\\u76ee\\u7684\\uff0c\\u51ed\\u8bb8\\u53ef\\u8bc1\\u660e\\u6587\\u4ef6\\u6216\\u6279\\u51c6\\u8bc1\\u4e66\\u5728\\u6709\\u6548\\u671f\\u5185\\u7ecf\\u8425\\uff0c\\u672a\\u7ecf\\u8bb8\\u53ef\\u4e0d\\u5f97\\u7ecf\\u8425\\uff09\\uff08\\u300a\\u5916\\u5546\\u6295\\u8d44\\u4ea7\\u4e1a\\u6307\\u5bfc\\u76ee\\u5f55\\uff082015\\u5e74\\u4fee\\u8ba2\\uff09\\u300b\\u4e2d\\u9650\\u5236\\u7c7b\\u548c\\u7981\\u6b62\\u7c7b\\uff0c\\u4ee5\\u53ca\\u9f13\\u52b1\\u7c7b\\u4e2d\\u6709\\u80a1\\u6743\\u8981\\u6c42\\u3001\\u9ad8\\u7ba1\\u8981\\u6c42\\u7684\\u9879\\u76ee\\u548c\\u4ea7\\u54c1\\u4e0d\\u5f97\\u751f\\u4ea7\\u7ecf\\u8425\\uff09\", \"CANDATE\": \"\", \"CBUITEM\": \"\\u6c7d\\u8f66\\u3001\\u7535\\u52a8\\u8f66\\u53ca\\u96f6\\u90e8\\u4ef6\\u3001\\u6c7d\\u8f66\\u53d1\\u52a8\\u673a\\u53ca\\u96f6\\u90e8\\u4ef6\\u7684\\u5f00\\u53d1\\u3001\\u7814\\u5236\\u3001\\u8bbe\\u8ba1\\u3001\\u751f\\u4ea7\\u3001\\u9500\\u552e\\u53ca\\u552e\\u540e\\u670d\\u52a1\\uff1b\\u7ecf\\u8425\\u672c\\u4f01\\u4e1a\\u81ea\\u4ea7\\u4ea7\\u54c1\\u7684\\u51fa\\u53e3\\u4e1a\\u52a1\\uff0c\\u672c\\u4f01\\u4e1a\\u6240\\u9700\\u7684\\u673a\\u68b0\\u8bbe\\u5907\\u3001\\u96f6\\u914d\\u4ef6\\u3001\\u539f\\u8f85\\u6750\\u6599\\u7684\\u8fdb\\u53e3\\u4e1a\\u52a1\\u3002\\u5404\\u7c7b\\u7535\\u5b50\\u5143\\u5668\\u4ef6\\u3001\\u7167\\u660e\\u706f\\u5177\\uff1b\\u98ce\\u529b\\u53d1\\u7535\\u673a\\u53ca\\u592a\\u9633\\u80fd\\u76f4\\u6d41\\u53d1\\u7535\\u673a\\u7684\\u9500\\u552e\\u3001\\u6279\\u53d1\\uff1b\\u81ea\\u6709\\u623f\\u5c4b\\u79df\\u8d41\\uff1b\\u6c7d\\u8f66\\u7ef4\\u4fee\\u6280\\u672f\\u54a8\\u8be2\\uff1b\\u4f4f\\u5bbf\\u4e1a\\uff1b\\u7269\\u4e1a\\u7ba1\\u7406\\u3002\\uff08\\u4e0a\\u8ff0\\u7ecf\\u8425\\u8303\\u56f4\\u6d89\\u53ca\\u8bb8\\u53ef\\u7ecf\\u8425\\u9879\\u76ee\\u7684\\uff0c\\u51ed\\u8bb8\\u53ef\\u8bc1\\u660e\\u6587\\u4ef6\\u6216\\u6279\\u51c6\\u8bc1\\u4e66\\u5728\\u6709\\u6548\\u671f\\u5185\\u7ecf\\u8425\\uff0c\\u672a\\u7ecf\\u8bb8\\u53ef\\u4e0d\\u5f97\\u7ecf\\u8425\\uff09\\uff08\\u300a\\u5916\\u5546\\u6295\\u8d44\\u4ea7\\u4e1a\\u6307\\u5bfc\\u76ee\\u5f55\\uff082015\\u5e74\\u4fee\\u8ba2\\uff09\\u300b\\u4e2d\\u9650\\u5236\\u7c7b\\u548c\\u7981\\u6b62\\u7c7b\\uff0c\\u4ee5\\u53ca\\u9f13\\u52b1\\u7c7b\\u4e2d\\u6709\\u80a1\\u6743\\u8981\\u6c42\\u3001\\u9ad8\\u7ba1\\u8981\\u6c42\\u7684\\u9879\\u76ee\\u548c\\u4ea7\\u54c1\\u4e0d\\u5f97\\u751f\\u4ea7\\u7ecf\\u8425\\uff09\", \"ENTTYPE\": \"\\u6709\\u9650\\u8d23\\u4efb\\u516c\\u53f8\\uff08\\u5916\\u5546\\u6295\\u8d44\\u4f01\\u4e1a\\u4e0e\\u5185\\u8d44\\u5408\\u8d44\\uff09\", \"REGCAPCUR\": \"\\u4eba\\u6c11\\u5e01\\u5143\", \"UNISCID\": \"91610131294204008Y\", \"region_name\": \"\\u9655\\u897f\\u7701\\u897f\\u5b89\\u5e02\", \"FRNAME\": \"\\u738b\\u4f20\\u798f\", \"REVDATE\": \"\", \"nic_name\": \"\\u5236\\u9020\\u4e1a-\\u6c7d\\u8f66\\u5236\\u9020\\u4e1a-\\u6c7d\\u8f66\\u6574\\u8f66\\u5236\\u9020\", \"REGNO\": \"610100400006954\"}}}";
        System.out.println(decodeUnicode(str));
    }

    /**
     * 把中文转成Unicode码
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            // 汉字范围 \u4e00-\u9fa5 (中文)
            if (chr1 >= 19968 && chr1 <= 171941) {
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 判断是否为中文字符
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Unicode转中文
     * @param unicode
     * @return
     */
    public static String decodeUnicode(final String unicode) {
        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 0; i < hex.length; i++) {
            try {
                // 汉字范围 \u4e00-\u9fa5 (中文)
                // 取前四个，判断是否是汉字
                if(hex[i].length()>=4){
                    String chinese = hex[i].substring(0, 4);
                    try {
                        int chr = Integer.parseInt(chinese, 16);
                        boolean isChinese = isChinese((char) chr);
                        //转化成功，判断是否在  汉字范围内
                        //在汉字范围内
                        if (isChinese){
                            // 追加成string
                            string.append((char) chr);
                            //并且追加  后面的字符
                            String behindString = hex[i].substring(4);
                            string.append(behindString);
                        }else {
                            string.append(hex[i]);
                        }
                    } catch (NumberFormatException e1) {
                        string.append(hex[i]);
                    }
                }else{
                    string.append(hex[i]);
                }
            } catch (NumberFormatException e) {
                string.append(hex[i]);
            }
        }
        return string.toString();
    }

}
