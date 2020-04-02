package testng;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ReadExcelData {
	
	Workbook wb;
	
	@Test
	public void excelReader() throws Exception {
		String filePath = System.getProperty("user.dir")+"\\TestData";
		String fileName = "TestData.xlsx";
		
		File file = new File(filePath+"\\"+fileName);
		FileInputStream stream = new FileInputStream(file);
		
		String fileExtension = fileName.substring(fileName.indexOf("."));
		
		if(fileExtension.equals(".xlsx")) {
			wb = new XSSFWorkbook(stream);
		}else if(fileExtension.equals(".xls")) {
			wb = new HSSFWorkbook(stream);
		}
		
		Sheet sheet = wb.getSheet("Login");
		
		int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();

		System.out.println("Total No. of Rows : "+rowCount);
		
		for(int i=0;i<=rowCount;i++) {
			Row row = sheet.getRow(i);
			System.out.print(row.getCell(0).getStringCellValue() + " | "+row.getCell(1).getStringCellValue()+" | "+row.getCell(2).getStringCellValue());
			System.out.println();
			}
		
		
		stream.close();
		wb.close();
		
	}
	
	

}
