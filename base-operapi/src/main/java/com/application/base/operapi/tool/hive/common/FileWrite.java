package com.application.base.operapi.tool.hive.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author : 孤狼
 * @NAME: FileWrite
 * @DESC: 文件写入.
 **/
public class FileWrite {
	
	/**
	 * 测试
	 * @param args
	 */
    public static void main(String[] args){
        try {
            FileWriter fw = new FileWriter("E:/tmp1.txt");
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
        StringBuffer buffer = new StringBuffer("");
        split = split==null ? "|":split;
        for(int i = 0; i < mapList.size(); i++){
            List<Object> row = mapList.get(i);
            for(Object value :row){
                if(value instanceof Date){
	                buffer.append(DateTimeUtil.format((Date)value,"yyyy-MM-dd")+split);
                }else if(value instanceof Timestamp){
	                buffer.append(DateTimeUtil.format((Timestamp)value,"yyyy-MM-dd HH:mm:ss")+split);
                }else if(value ==null){
	                buffer.append(split);
                }else {
                	buffer.append(value+split);
                }
            }
	        buffer.append("\n");
            try{
                fw.write(buffer.toString());
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
