package RMSV2.RMSCustomerPortal;

import resources.ExcelDataConfig;

public class testdataRead {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExcelDataConfig excel=new ExcelDataConfig("C:\\Users\\nagababu.parepalli\\eclipse-workspace\\RmsCustomerPortal\\src\\main\\java\\resources\\TestData.xlsx");
System.out.println(excel.getCellData(0, 1, 0));
	}

}
