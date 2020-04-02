package testng;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelInteraction {
	
	Workbook wb;
	Sheet sheet;
	public ExcelInteraction(String filePath, String fileName) {
		try {
		File file = new File(filePath+"\\"+fileName);
		FileInputStream stream = new FileInputStream(file);
		
		String fileExtension = fileName.substring(fileName.indexOf("."));
		
		if(fileExtension.equals(".xlsx")) {
			wb = new XSSFWorkbook(stream);
		}else if(fileExtension.equals(".xls")) {
			wb = new HSSFWorkbook(stream);
		}
		}catch(Exception ex) {
			System.out.println("Exception occurred while locating the excel file : "+ex.getMessage());
		}
	}
	
	public int getRowCount(String sheetName) {
		sheet = wb.getSheet(sheetName);
		return sheet.getLastRowNum()-sheet.getFirstRowNum();
	}
	public String getCellData(String sheetName, int rowno, int colno) {
		sheet = wb.getSheet(sheetName);
		Row row = sheet.getRow(rowno);
		Cell cell = row.getCell(colno);
		String data = cell.getStringCellValue();
		return data;
		// return wb.getSheet(sheetName).getRow(rowno).getCell(colno).getStringCellValue();
	}
	
	
	public void closeWorkbook() {
		try {
		wb.close();
		}catch(Exception ex) {
			System.out.println("Exception occurred while closing the workbook : "+ex.getMessage());
		}
	}

}
