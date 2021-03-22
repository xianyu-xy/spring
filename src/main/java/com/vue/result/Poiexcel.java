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

public class Poiexcel {
	public static List<User> poiexcel(MultipartFile file){
		List<User> list=new ArrayList<>();
		try{
			//创建workbook对象
			HSSFWorkbook workbook=new HSSFWorkbook(file.getInputStream());
			//获取表单中的数量(上传几个文件)
			int number=workbook.getNumberOfSheets();
			for(int i=0;i<number;i++){
				//获取具体表单
				HSSFSheet sheet=workbook.getSheetAt(i);
				//获取总行数
				int rows=sheet.getPhysicalNumberOfRows();
				for(int j=0;j<rows;j++){
					if(j==0){
						continue;//跳过标题行
					}
					//获取具体行数
					HSSFRow row=sheet.getRow(j);
					if(row==null){
						continue;//防止中间有空行
					}
					//获取总列数，一行有多少列
					int cell=row.getPhysicalNumberOfCells();
					if(cell>2){
						System.out.println("数据错误");
						User user1=new User();
						user1.setUsername("200");
						user1.setPassword("200");
						list.add(user1);
						break;
					}else{
						User user=new User();
						for(int k=0;k<cell;k++){
							//获取每列的值
							HSSFCell cell1=row.getCell(k);
							//数据类型是String
							String cellvalues=cell1.getStringCellValue();
							  // 类型是 Date或者数字:cell1.getDateCellValue()
							switch(k){
							case 0:
								user.setUsername(cellvalues);
								break;
							case 1:
								user.setPassword(cellvalues);
								break;
							}
						}
						list.add(user);
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return list;
	}

}
