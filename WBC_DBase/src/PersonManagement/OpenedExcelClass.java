package PersonManagement;

import org.apache.poi.ss.usermodel.Workbook;

public class OpenedExcelClass {
	private String filePath;
	private Workbook workbook;
	
		public OpenedExcelClass (String filePath, Workbook workbook) 
	{
		this.filePath = filePath;
		this.workbook = workbook;
		
	}
	
		public Workbook getWorkbook() {
			return workbook;
		}

		public void setWorkbook(Workbook workbook) {
			this.workbook = workbook;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
	}
