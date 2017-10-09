package selenium.automation.common;

import java.io.File;
//no need to check in
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;


/**
 * @Class description : Read the input excel and hold all values in a Map
 * @author  Gopinath Hariharan
 * @version 1.0
 * 
 */

public class ExcelUtil extends BaseTests{
	
	protected final static Logger logger = Logger.getLogger(ExcelUtil.class);
	@SuppressWarnings("unused")
	private static Workbook getWorkbook(InputStream inputStream, String excelPath) throws Exception{
		Workbook workbook = null;
		if(excelPath.endsWith("xlsx")){
			workbook=new XSSFWorkbook(inputStream);
		}else if(excelPath.endsWith("xls")){
			workbook=new HSSFWorkbook(inputStream);
		}else{
			throw new IllegalArgumentException("The Specified file is not a Excel");

		}
		return workbook;

	}


	private static XSSFSheet getSheet(Workbook workbook, String sheetName){

		String actualSheetName=null;
		int numberOfSheets = workbook.getNumberOfSheets();
		for(int i=0; i<numberOfSheets; i++){
			actualSheetName=workbook.getSheetName(i);
			if(actualSheetName.equalsIgnoreCase(sheetName)){
				logger.info("Reading the data from Excel");
				return (XSSFSheet)workbook.getSheetAt(i);
			}else if (i==numberOfSheets){
				logger.error(sheetName + " Sheet not found");
				assert(false);
				return null;
			}
		}
		return null;

	}

	public static  HashMap<String, String> getRowAsMap(String strDataFilePath,String strSheetName,String TestID) throws Exception{
		HashMap<String, String> myDataMap = new HashMap<String, String>();
		InputStream is = ExcelUtil.class.getClass().getResourceAsStream(strDataFilePath);

		//Workbook workbook = getWorkbook(BaseTests.class.getResourceAsStream(strDataFilePath), strDataFilePath);
		Workbook workbook = getWorkbook(is, strDataFilePath);
		XSSFSheet sheet = getSheet(workbook,strSheetName);

		for(int i =1; i<=sheet.getLastRowNum();i++){
			Row tempRow = sheet.getRow(i);
			Cell actualTestId = tempRow.getCell(0);
			actualTestId.setCellType(Cell.CELL_TYPE_STRING);
			try{
				if(actualTestId.getStringCellValue().equals(TestID)){
					for(int j=0; j<sheet.getRow(0).getLastCellNum();j++){
						Cell headerCell = sheet.getRow(0).getCell(j);
						Cell dataCell = sheet.getRow(i).getCell(j);

						if(dataCell !=null){
							dataCell.setCellType(Cell.CELL_TYPE_STRING);
							myDataMap.put(headerCell.toString(), dataCell.toString());
							System.out.println(headerCell.toString() + " : "+ dataCell.toString());
						}
					}
					return myDataMap;

				}else if(i==sheet.getLastRowNum()){
					logger.info(TestID + " Test id does not exist in the excel");
					Assert.assertFalse(false);
				}
			}catch(Exception e){
				logger.info("Exception found in Excel Utils and the exception is " + e);
				Assert.assertFalse(false);

			}

		}

		return myDataMap;
	}
	
	public static  HashMap<String, String> getRowAsMap1(String strDataFilePath,String strSheetName,String TestID) throws Exception{
		HashMap<String, String> myDataMap = new HashMap<String, String>();

		Workbook workbook = getWorkbook(ExcelUtil.class.getClassLoader().getResourceAsStream(strDataFilePath), strDataFilePath);
		XSSFSheet sheet = getSheet(workbook,strSheetName);

		for(int i =0; i<=sheet.getLastRowNum();i++){
			Row tempRow = sheet.getRow(i);
			Cell actualTestId = tempRow.getCell(0);
			try{
				if(actualTestId.toString().equalsIgnoreCase(TestID)){
					for(int j=0; j<sheet.getRow(0).getLastCellNum();j++){
						Cell headerCell = sheet.getRow(0).getCell(j);
						Cell dataCell = sheet.getRow(i).getCell(j);

						if(dataCell !=null){
							dataCell.setCellType(Cell.CELL_TYPE_STRING);
							myDataMap.put(headerCell.toString(), dataCell.toString());
							System.out.println(headerCell.toString() + " : "+ dataCell.toString());
						}
					}

				}else if(i==sheet.getLastRowNum()){
					logger.info(TestID + " Test id does not exist in the excel");
					Assert.assertFalse(false);
				}
			}catch(Exception e){
				logger.info("Exception found in Excel Utils and the exception is " + e);
				Assert.assertFalse(false);

			}

		}

		return myDataMap;
	}

}

