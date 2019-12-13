package com.application.base.operapi.tool.hive.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: NumValidUtils
 * @DESC: 格式校驗.
 **/
public class FileWrite {
	
    public static void main(String[] args){
        try {
            FileWriter fw = new FileWriter("e:/tmp1.txt");
            BufferedWriter bw=new BufferedWriter(fw);
            fw.write("sss");
            fw.flush();
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 以字符写入
     * @param mapList
     * @param fileName
     * @throws Exception
     */
    public void writeMapList2File(List<List<Object>> mapList, String fileName, String split) throws Exception{
        FileWriter fw = new FileWriter(fileName);
        String sb;
        split = split==null?"|":split;
        for(int i = 0; i < mapList.size(); i++){
            sb = "";
            boolean hasBlank = false;
            List<Object> row = mapList.get(i);
            for(Object value :row){
                if(value instanceof Date){
                    sb += DateTimeUtil.format((Date)value,"yyyy-MM-dd")+split;
                }else if(value instanceof Timestamp){
                    sb += DateTimeUtil.format((Timestamp)value,"yyyy-MM-dd HH:mm:ss")+split;
                }else if(value ==null){
                    sb +=split;
                    hasBlank = true ;
                }else {
                    sb += value+split;
                }
            }
            sb += "\n";
            try{
                fw.write(sb);
                fw.flush();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        fw.close();
    }
	
	/**
	 * list 到文件.
	 * @param mapList
	 * @param fileName
	 * @throws Exception
	 */
	public void writeList2File(List<String> mapList,String fileName) throws Exception{
        FileWriter fw = new FileWriter(fileName);
        for(int i = 0; i < mapList.size(); i++){
            fw.write(mapList.get(i)+"\n");
            fw.flush();
        }
        fw.close();
    }
}
