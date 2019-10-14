package com.application.base.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author : 孤狼
 * @NAME: ExcelUtils
 * @DESC: execl导入.
 **/
public class ExcelUtils<T> {
	

	/**
	 * @description 将数据导出excel文件，返回List<T>；
	 * @param fileName 文件名
	 * @param path 文件完全路径，包括文件名
	 * @param classPath 返回数据类型的全类名；
	 * 
	 */	
	
	public static<T> List<T> importExcel(String fileName, String path, String classPath) throws Exception {
		if (fileName.contains(".xlsx")) {
    	return	readExcelXLSX(path,classPath);
	    } else {
	    	return	readExcelXLS(path,classPath);
	    }
	}

	 /**
		 * @description 将数据excel文件的数据导出为List<T>；
		 */
	 @SuppressWarnings({ "unchecked", "null" })
	public static<T> List<T> readExcelXLS(String path,  String classPath) throws Exception{
		 List<T> dataList=new ArrayList<T>();
		 POIFSFileSystem fs;
		 HSSFWorkbook wb = null;
		 HSSFSheet sheet;
		 HSSFRow row;
		 //通过路径获取Excel文件的输入流
		 InputStream is=new FileInputStream(new File(path));
		  fs = new POIFSFileSystem(is);
         wb = new HSSFWorkbook(fs);
         sheet = wb.getSheetAt(0);//获取表单；
         // 得到总行数
	        int rowNum = sheet.getLastRowNum();
	        row = sheet.getRow(0);//获取第一行；
	        int colNum = row.getPhysicalNumberOfCells();//获取总列数；
	       //获取存储数据的对象，及属性；   
           try {
        	Class<T>   classt =  (Class<T>) Class.forName(classPath);
        	  Field[] fields = classt.getDeclaredFields();
        
           
	        //获取表头所占行数；
           int i = 0;
		    if (sheet.getHeader() != null) {
		    	   i = sheet.getFirstRowNum()+1;
		    } else {
		    	   i = sheet.getFirstRowNum();
		    }
	        //从表头的下一行读取表格数据内容；
	        for (; i <= rowNum; i++) {               
	            row = sheet.getRow(i);
	            int j = 0;
	            String[]  rowstr = new String[colNum] ;
	            while (j < colNum) {
	                // 每个单元格的数据内容用字符串数组保存；
	            	rowstr[j]= getStringCellValue(row.getCell(j)).trim();
	                j++;
	            }
	          //把获取的一行的数据数组，添加到存储对象中；      
	            T   t = (T)Class.forName(classPath).newInstance();
	            int flag = 0;
	           for(int k=0;k<rowstr.length;k++){
	        	
	        	   //获得实体类的set方法
	                Method m = classt.getMethod("set"+fields[k].getName().substring(0,1).toUpperCase()+fields[k].getName().substring(1), fields[k].getType());
	                String content = rowstr[k];
	                if (StringUtils.isNotBlank(content) && !"-".equals(content)) {//如果为空或者为"-"不执行set方法
	                    //执行set方法
	                    m.invoke(t, StringToType(fields[k].getType(), content));
	                    flag++;
	                } else {
	                    m.invoke(t, StringToType(fields[k].getType(), null));
	                }
	        	   }
	           if (flag > 0) {
	                dataList.add((T) t);
	            }
	            
	        }
		 
	        is.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
			return dataList; 
	 }
	 
	 
	 
	    /**
		 * Description: 读取EXCEL2007
		 * @param path
		 * @param classPath
		 * @throws Exception
		 */
	
		@SuppressWarnings("unchecked")
		public static<T> List<T> readExcelXLSX(String path, String classPath) throws Exception{
			List<T> dataList=new ArrayList<T>();
			FileInputStream is = new FileInputStream(path);  
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = null;
			String cellString = "";
		    int i = 0;
		    if (sheet.getHeader() != null) {
		    	   i = sheet.getFirstRowNum()+1;
		    } else {
		    	   i = sheet.getFirstRowNum();
		    }
		    for (; i < sheet.getPhysicalNumberOfRows(); i++) {
	            //取得第i行
	            row = sheet.getRow(i);
	            String myRow = "";
	            for (short j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
		            //第i行第j列,cellString中存的是读取出来的那个单元格中的内容
	            	XSSFCell cell = row.getCell(j);
	            	if (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
	            		cellString = parseExcel(cell);
	            	} else {
	            	    cellString = "-";
	            	}
	            	System.err.println(cellString);
	            	myRow += cellString + ";";
			    }
	            System.err.println(myRow);
	            String[] rowContent = myRow.split(";");
	          
	            try {
	            	
	            	Class<T>   classt =  (Class<T>) Class.forName(classPath);
	          	  Field[] fields = classt.getDeclaredFields();
	               T   t = (T)Class.forName(classPath).newInstance();
	            int flag = 0;
	            for (int k = 0; k< rowContent.length; k++) {
	                //获得实体类的set方法
	                Method m = classt.getMethod("set"+fields[k].getName().substring(0,1).toUpperCase()+fields[k].getName().substring(1), fields[k].getType());
	                String value = rowContent[k];
	                if (StringUtils.isNotBlank(value) && !"-".equals(value)) {//如果为空或者为"-"不执行set方法
	                    //执行set方法
	                    m.invoke(t, StringToType(fields[k].getType(), value));
	                    flag++;
	                } else {
	                    m.invoke(t, StringToType(fields[k].getType(), null));
	                }
	            }
	            if (flag > 0) {
	                dataList.add(t);
	            }
	            
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
		    }
		    is.close();
		    
			return dataList;
		}
	    
	      
	/**
	 * @description 将数据导入excel文件；
	 */
	 public static File createExcelTable(String excelFileName ,List<Object[]> list,List<String> titlelist) throws IOException {
	        //把一张xls的数据表读到wb里
	 		File file= File.createTempFile(excelFileName.split("\\.")[0], excelFileName.substring(excelFileName.indexOf(".")));
	 		if(file.exists()){
	 			file.delete();
	 			file.createNewFile();
	 		}else{
	 			file.createNewFile();
	 			
	 		}	
	 		OutputStream output=new FileOutputStream(file);
	 		
	 		HSSFWorkbook wb = new HSSFWorkbook();
		      //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
			HSSFSheet sheet = wb.createSheet();
			
			//创建表头
			List<String> title=new ArrayList<String>();
			for(String t:titlelist){
				title.add(t);		
			}	
			//创建一个行对象
			HSSFRow rowo = sheet.createRow(0);
			for(int i=0;i<title.size();i++){
				rowo.createCell(i).setCellValue(title.get(i));	
			}
			//////////////////////////////////////////////
			int startRowNum=2;//从第二行开始
			int startColNum=1;//从第一列开始
			int currentRowNum = startRowNum-1;//从第几行开始写
			int currentColNum = startColNum-1;//从第几列开始写
			int maxRows=60000; //一个表单的最大行数；
			int h=0;
			System.out.println("----list-----"+list.size());
			for (Object[] objects : list) {//遍历list
				if(h!=0 && (h % maxRows==0)){
					System.out.println("----h-----"+h);
					//创建一个表单
				 sheet = wb.createSheet();
				 //添加表头
				 HSSFRow newrow = sheet.createRow(0);
					for(int i=0;i<title.size();i++){
						newrow.createCell(i).setCellValue(title.get(i));	
					}
				 //初始化参数
					 startRowNum=2;//从第二行开始
					 startColNum=1;//从第一列开始
					 currentRowNum = startRowNum-1;//从第几行开始写
					 currentColNum = startColNum-1;//从第几列开始写
					
				}
				
				HSSFRow row = sheet.createRow(currentRowNum);//一个list就是一行，创建一个行
			for (Object object : objects) {//遍历每一个list的Object[]数组
			if (object==null) {
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_BLANK);//如果该单元格为空，则创建一个空格子，不然数据会移位
			cell.setCellValue("");//将这个空格设置为空字符串
			}else if(object instanceof Double || object instanceof Float){//判断这个格子放的数据的类型，其他的同理
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_NUMERIC);//如果是Double或者Float类型的，则用这个方式
			cell.setCellValue(Double.parseDouble(object.toString()));
			}else if(object instanceof Long || object instanceof Integer){
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(object.toString()));
			}else if(object instanceof Date){
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format((Date)object));
			}else if(object instanceof Boolean){
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_BOOLEAN);
			cell.setCellValue((Boolean)object);
			}else{
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(String.valueOf(object));
			}
			}
			currentRowNum++;//写完第一个list，跳到下一行
			currentColNum = startColNum-1;
			h++;
			}
			wb.write(output);
			
			return file;
			}
	 
	 
	     
		/**
		 * @description 将数据导入excel文件；
		 */
	 public static<T> File createExcelTable(String excelFileName ,List<T> list,String[] titlelist,String classPath) throws IOException {
	        //把一张xls的数据表读到wb里
	 		File file=new File(excelFileName);
	 		if(file.exists()){
	 			file.delete();
	 			file.createNewFile();
	 		}else{
	 			file.createNewFile();
	 			
	 		}
	 		OutputStream output=new FileOutputStream(file);
		      HSSFWorkbook wb = new HSSFWorkbook();
		      //读取第一页,一般一个excel文件会有三个工作表，这里获取第一个工作表来进行操作
			HSSFSheet sheet = wb.createSheet();
			//创建表头
			List<String> title=new ArrayList<String>();
			for(String t:titlelist){
				title.add(t);		
			}	
			//创建一个行对象
			HSSFRow rowo = sheet.createRow(0);
			for(int i=0;i<title.size();i++){
				rowo.createCell(i).setCellValue(title.get(i));
				
			}
			//////////////////////////////////////////////
			int startRowNum=2;//从第二行开始
			int startColNum=1;//从第一列开始
			int currentRowNum = startRowNum-1;//从第几行开始写
			int currentColNum = startColNum-1;//从第几列开始写
			
			
			
				Class<T> classt = null;
				 Field[] fields = null;
				try {
					classt = (Class<T>) Class.forName(classPath);
					 fields = classt.getDeclaredFields(); 
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	
			for (T t : list) {//遍历list
				HSSFRow row = sheet.createRow(currentRowNum);//一个list就是一行，创建一个行
		            for (int i=0;i<fields.length;i++) {
		            	Object object=null;
			            Method m;
						try {
							m = (Method) t.getClass().getMethod(  
							        "get" + getMethodName(fields[i].getName()));
							   object =  m.invoke(t);// 调用getter方法获取属性值  
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
	              	            	
		            	//遍历每一个list的Object[]数组
			if (object==null) {
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_BLANK);//如果该单元格为空，则创建一个空格子，不然数据会移位
			cell.setCellValue("");//将这个空格设置为空字符串
			}else if(object instanceof Double || object instanceof Float){//判断这个格子放的数据的类型，其他的同理
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_NUMERIC);//如果是Double或者Float类型的，则用这个方式
			cell.setCellValue(Double.parseDouble(object.toString()));
			}else if(object instanceof Long || object instanceof Integer){
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue(Double.parseDouble(object.toString()));
			}else if(object instanceof Date){
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new SimpleDateFormat("dd-MM-yyyy").format((Date)object));
			}else if(object instanceof Boolean){
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_BOOLEAN);
			cell.setCellValue((Boolean)object);
			}else{
				HSSFCell cell = row.createCell(currentColNum++, HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(String.valueOf(object));
			}
			}
			currentRowNum++;//写完第一个list，跳到下一行
			currentColNum = startColNum-1;
			}
			////////////////////////////
			wb.write(output);
			
			return file;
			}
	 
	 
	 
	 
	 
	// 把一个字符串的第一个字母大写、效率是最高的、  
	    private static String getMethodName(String fildeName) throws Exception{
	        byte[] items = fildeName.getBytes();  
	        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
	        return new String(items);  
	    }  
	 
	 /**
	  * 操作Excel表格的功能类
	  */


	     /**
	      * 读取Excel表格表头的内容
	      * @return String 表头内容的数组
	      */
	     public static  String[] readExcelTitle(InputStream is) {
	    	  POIFSFileSystem fs;
	    	  HSSFWorkbook wb = null;
	    	   HSSFSheet sheet;
	    	    HSSFRow row;

	         try {
	             fs = new POIFSFileSystem(is);
	             wb = new HSSFWorkbook(fs);
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	         sheet = wb.getSheetAt(0);
	         row = sheet.getRow(0);
	         // 标题总列数
	         int colNum = row.getPhysicalNumberOfCells();
	         System.out.println("colNum:" + colNum);
	         String[] title = new String[colNum];
	         for (int i = 0; i <colNum; i++) {
	        	
	             title[i] = getStringCellValue(row.getCell(i));
	         }
	         return title;
	     }

	     /**
	      * 读取Excel数据内容
	      * @return Map 包含单元格数据内容的Map对象
	      */
	     public static  Map<Integer, String> readExcelContent(InputStream is) {
	    	     POIFSFileSystem fs;
	    	     HSSFWorkbook wb = null;
	    	     HSSFSheet sheet;
	    	     HSSFRow row;
	         Map<Integer, String> content = new HashMap<Integer, String>();
	         String str = "";
	         try {
	             fs = new POIFSFileSystem(is);
	             wb = new HSSFWorkbook(fs);
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	         sheet = wb.getSheetAt(0);
	         // 得到总行数
	         int rowNum = sheet.getLastRowNum();
	         row = sheet.getRow(0);
	         int colNum = row.getPhysicalNumberOfCells();
	         // 正文内容应该从第二行开始,第一行为表头的标题
	         for (int i = 1; i <= rowNum; i++) {
	             row = sheet.getRow(i);
	             int j = 0;
	             while (j < colNum) {
	                 // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
	                 // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
	                 // str += getStringCellValue(row.getCell((short) j)).trim() +
	                 // "-";
	                 str += getCellFormatValue(row.getCell(j)).trim() + "    ";
	                 j++;
	             }
	             content.put(i, str);
	             str = "";
	         }
	         return content;
	     }
	     
		/**
		 * @description 将数据excel文件的数据导出为List<Map<Integer, String[]>>；
		 */
	 public static List<Map<Integer, String>> readExcelXLS(InputStream is) throws IOException {
		 List<Map<Integer, String>> contentList=new ArrayList<Map<Integer, String>>();
		 POIFSFileSystem fs;
		 HSSFWorkbook wb = null;
		 HSSFSheet sheet;
		 HSSFRow row;
	//	 InputStream is=new FileInputStream(new File(excelFilePath));
	        try {
	            fs = new POIFSFileSystem(is);
	            wb = new HSSFWorkbook(fs);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        sheet = wb.getSheetAt(0);
	        // 得到总行数
	        int rowNum = sheet.getLastRowNum();
	        row = sheet.getRow(0);
	        int colNum = row.getPhysicalNumberOfCells();
	        // 正文内容应该从第二行开始,第一行为表头的标题
	        for (int i = 1; i <= rowNum; i++) {               
	            row = sheet.getRow(i);
	            int j = 0;
	            String  str = "" ;
	            while (j < colNum) {
	                // 每个单元格的数据内容用字符串数组保存；
	                str+= getStringCellValue(row.getCell(j)).trim()+"   ;";
	                j++;
	            }
	          Map<Integer, String> content = new HashMap<Integer, String>();  
	            content.put(i, str);
	            contentList.add(content);
	            str = null;
	        }
	        return contentList; 
		 
	 }
	
	 
	 /**
		 * Description: 读取EXCEL2007
		 * @throws Exception
		 */
	
		public static List<Map<Integer, String>> readExcelXLSX(InputStream is) throws Exception{
			List<Map<Integer, String>> contentList=new ArrayList<Map<Integer, String>>();
			//FileInputStream is = new FileInputStream(path);  
			XSSFWorkbook wb = new XSSFWorkbook(is);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row = null;
		    
			//行数
			 int rowNum = sheet.getLastRowNum();
		    System.out.println("---------------rowNum-------"+rowNum);
			String cellString = "";
		    int i = 0;
		    if (sheet.getHeader() != null) {
		    	   i = sheet.getFirstRowNum()+1;
		    } else {
		    	   i = sheet.getFirstRowNum();
		    }
		     //列数
		     row=	sheet.getRow(i);
		     int colNum = row.getPhysicalNumberOfCells();
		    
		    for (; i < sheet.getPhysicalNumberOfRows(); i++) {
	            //取得第i行
	            row = sheet.getRow(i);
	            String myRow = "";
	            //跳过空行
	            if(null==row){
	            	continue;
	            }
	            for (short j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
		            //第i行第j列,cellString中存的是读取出来的那个单元格中的内容
	            	XSSFCell cell = row.getCell(j);
	            	//跳过空单元格；
	            	if(null==cell){
	            		continue;
	            	}
	            	if (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {
	            		cellString = parseExcel(cell);
	            	} else {
	            	    cellString = "-";
	            	}
	            	System.err.println(cellString);
	            	myRow += cellString + ";";
			    }
	            
	            System.err.println(myRow);
	            String[] rowContent = myRow.split(";");
	    
		            int j = 0;
		            String  str = "" ;
		            while (j < colNum) {
		                // 每个单元格的数据内容用字符串数组保存；
		                str+= getStringCellValue(row.getCell(j)).trim()+"   ;";
		                j++;
		            }
		          Map<Integer, String> content = new HashMap<Integer, String>();  
		            content.put(i, str);
		            contentList.add(content);
		            str = null;
		        }
		    
		        return contentList; 
			 
		}
	    
	  private static String getStringCellValue(XSSFCell cell) {
		  String strCell = "";
		  if(null!=cell){
	        switch (cell.getCellType()) {
	        case XSSFCell.CELL_TYPE_STRING:
	            strCell = cell.getStringCellValue();
	            break;
	        case XSSFCell.CELL_TYPE_NUMERIC:
	            strCell = String.valueOf(cell.getNumericCellValue());
	            break;
	        case XSSFCell.CELL_TYPE_BOOLEAN:
	            strCell = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case XSSFCell.CELL_TYPE_BLANK:
	            strCell = "";
	            break;
	        default:
	            strCell = "";
	            break;
	        }
	        if (strCell.equals("") || strCell == null) {
	            return "";
	        }
		    }else if (cell == null) {
	            return "";
	        }
	     
	        return strCell;
	}



	/**
	     * 获取单元格数据内容为字符串类型的数据
	     * 
	     * @param cell Excel单元格
	     * @return String 单元格数据内容
	     */
	    private static String getStringCellValue(HSSFCell cell) {
	        String strCell = "";
	        if(null!=cell){
	        switch (cell.getCellType()) {
	        case HSSFCell.CELL_TYPE_STRING:
	            strCell = cell.getStringCellValue();
	            break;
	        case HSSFCell.CELL_TYPE_NUMERIC:
	            strCell = String.valueOf(cell.getNumericCellValue());
	            break;
	        case HSSFCell.CELL_TYPE_BOOLEAN:
	            strCell = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case HSSFCell.CELL_TYPE_BLANK:
	            strCell = "";
	            break;
	        default:
	            strCell = "";
	            break;
	        }
	        
	        if (strCell.equals("") || strCell == null) {
	            return "";
	        }
	        }else if (cell == null) {
	            return "";
	        }
	        return strCell;
	    }

	    /**
	     * 获取单元格数据内容为日期类型的数据
	     * 
	     * @param cell
	     *            Excel单元格
	     * @return String 单元格数据内容
	     */
	    private static String getDateCellValue(HSSFCell cell) {
	        String result = "";
	        try {
	            int cellType = cell.getCellType();
	            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
	                Date date = cell.getDateCellValue();
	                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
	                        + "-" + date.getDate();
	            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
	                String date = getStringCellValue(cell);
	                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
	            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
	                result = "";
	            }
	        } catch (Exception e) {
	            System.out.println("日期格式不正确!");
	            e.printStackTrace();
	        }
	        return result;
	    }

	    /**
	     * 根据HSSFCell类型设置数据
	     * @param cell
	     * @return
	     */
	    private static String getCellFormatValue(HSSFCell cell) {
	        String cellvalue = "";
	        if (cell != null) {
	            // 判断当前Cell的Type
	            switch (cell.getCellType()) {
	            // 如果当前Cell的Type为NUMERIC
	            case HSSFCell.CELL_TYPE_NUMERIC:
	            	cellvalue=String.valueOf(cell.getNumericCellValue());
	            	 break;
	            case HSSFCell.CELL_TYPE_FORMULA: {
	                // 判断当前的cell是否为Date
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                    // 如果是Date类型则，转化为Data格式
	                    
	                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
	                    //cellvalue = cell.getDateCellValue().toLocaleString();
	                    
	                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
	                    Date date = cell.getDateCellValue();
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                    cellvalue = sdf.format(date);
	                    
	                }
	                // 如果是纯数字
	                else {
	                    // 取得当前Cell的数值
	                    cellvalue = String.valueOf(cell.getNumericCellValue());
	                }
	                break;
	            }
	            // 如果当前Cell的Type为STRIN
	            case HSSFCell.CELL_TYPE_STRING:
	                // 取得当前的Cell字符串
	                cellvalue = cell.getRichStringCellValue().getString();
	                break;
	            // 默认的Cell值
	            default:
	                cellvalue = " ";
	            }
	        } else {
	            cellvalue = "";
	        }
	        return cellvalue;

	    }

	   
		/**
		 * 将字符串转换成对应的类型
		 * @param cls
		 * @param value
		 * @return
		 */
		private static Object StringToType(Class cls, String value){
		    if(StringUtils.isNotBlank(value)) {
				if (cls == String.class) {
			        return String.valueOf(value);
				} else if (cls == Long.class) {
			        return Long.valueOf(value);
				} else if (cls == Integer.class) {
			        return Integer.valueOf(value);
				} else if (cls == Double.class) {
					System.err.println(value);
			        return Double.valueOf(value);
				} else if (cls == Date.class) {
					try {
						return DateUtil.parseYYYYMMDDDate(value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (cls == int.class) {
			        return Integer.parseInt(value);
				}
		    } else if (cls == String.class) {
		      return "";
		    } else if (cls == int.class) {
		        return -1;
		    } 
		    return null;
		}
		
		/**
		 * 处理单元格格式为String类型；
		 * @param cell
		 * @return
		 */
		public static String  excelCellformat(Cell cell){
			 String content="";
			 if (null != cell) {   
                 switch (cell.getCellType()) {   
                 case HSSFCell.CELL_TYPE_NUMERIC: // 数字   
                         content=String.valueOf(cell.getNumericCellValue());   
                         break;   
                 case HSSFCell.CELL_TYPE_STRING: // 字符串   
                	 content=cell.getStringCellValue();   
                         break;   
                 case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean   
                	 content=String.valueOf(cell.getBooleanCellValue());   
                         break;   
                 case HSSFCell.CELL_TYPE_FORMULA: // 公式   
                	 content=cell.getCellFormula();   
                         break;   
                 case HSSFCell.CELL_TYPE_BLANK: // 空值   
                	 content="-";   
                         break;   
                 case HSSFCell.CELL_TYPE_ERROR: // 故障   
                	 content="发生故障";   
                         break;   
                 default:   
                	 content="未知类型   ";   
                         break;   
                 }   
         } else {   
        	 content="-";   
         }
			
			 
			 return content;   
			
			
			
		}
		
		
		/**
		 * 处理单元格格式
		 * @param cell
		 * @return
		 */
		private static String parseExcel(Cell cell) {
			String result = new String();
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
				//1、判断是否是数值格式
				if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
					short format = cell.getCellStyle().getDataFormat();
					SimpleDateFormat sdf = null;
					double value = cell.getNumericCellValue();
					if(format == 14 || format == 31 || format == 57 || format == 58 || format == 177){
						//日期
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date date = DateUtil.getJavaDate(value);
						result = sdf.format(date);
					}else if (format == 20 || format == 32) {
						//时间
						sdf = new SimpleDateFormat("HH:mm");
						Date date = DateUtil.getJavaDate(value);
						result = sdf.format(date);
					}  else {
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
	            		result = cell.toString();
					}
				}
				/*if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
					SimpleDateFormat sdf = null;
					if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
							.getBuiltinFormat("h:mm")) {
						sdf = new SimpleDateFormat("HH:mm");
					} else {// 日期
						sdf = new SimpleDateFormat("yyyy-MM-dd");
					}
					Date date = cell.getDateCellValue();
					result = sdf.format(date);
				} else if (cell.getCellStyle().getDataFormat() == 58) {
					// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					double value = cell.getNumericCellValue();
					Date date = org.apache.poi.ss.usermodel.DateUtil
							.getJavaDate(value);
					result = sdf.format(date);
				}*/ else {
					double value = cell.getNumericCellValue();
					CellStyle style = cell.getCellStyle();
					DecimalFormat format = new DecimalFormat();
					String temp = style.getDataFormatString();
					// 单元格设置成常规
					if (temp.equals("General")) {
						format.applyPattern("#");
					}
					result = format.format(value);
				}
				break;
			case HSSFCell.CELL_TYPE_STRING:// String类型
				result = cell.getRichStringCellValue().toString();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				result = "";
			default:
				result = "";
				break;
			}
			return result;
		}

	}
	 

