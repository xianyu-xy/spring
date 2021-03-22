package com.vue.result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.vue.pojo.User;

public class excelEmployee {

	/**
     * Excel 解析成数据集合
     *
     * @return
     */
	public static List<User> excel2Employee(MultipartFile file) {
        List<User> list = new ArrayList<>();
        try {
            //1. 创建一个 workbook 对象
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //2. 获取 workbook 中表单的数量（上传几个文件）
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //3. 获取表单
                HSSFSheet sheet = workbook.getSheetAt(i);
                //4. 获取表单中的行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    //5. 跳过标题行
                    if (j == 0) {
                        continue;//跳过标题行
                    }
                    //6. 获取行
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//防止数据中间有空行
                    }
                    //7. 获取列数,一行有多少列
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    if(physicalNumberOfCells>2){
                    	System.out.println("数据错误");
                    	User user1=new User();
                    	user1.setUsername("200");
                    	user1.setPassword("200");
                    	list.add(user1);
                    	break;
                    }
                    else{
                    		 User user = new User();
                             for (int k = 0; k < physicalNumberOfCells; k++) {
                                 HSSFCell cell = row.getCell(k);
                              // 类型是 String
                                 String cellValue = cell.getStringCellValue();
                                // switch (cell.getCellType()) {
                             
                                 	// 类型是 String 进入此 case 块
                                     //case STRING:
                                         //String cellValue = cell.getStringCellValue();
                                       
                                         switch (k) {
                                             case 0:
                                                 user.setUsername(cellValue);
                                                 break;
                                             case 1:
                                               user.setPassword(cellValue);
                                                 break;          
                                         }
                                        
                                 }
                             list.add(user);
                             }
                    	}
                   
                    // 最后将解析后的数据添加到员工集合中
                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
