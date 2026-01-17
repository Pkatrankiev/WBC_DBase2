package PersonReferenceInWebKD;

import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import Aplication.ReadExcelFileWBC;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;


public class ChangeKodeInKDByExcelLog {

	
	public static void ChangeKodeInKD() {
	
		List<String[]> listPerson = readPersonFromExcelFile();
		boolean NOTviewBrauser = false;
		ChromeDriver driver = PersonReferenceInWebKD_Methods.openChromeDriver(NOTviewBrauser);

		String textArea = "";
		if(PersonReferenceInWebKD_Methods.logInToWebSheet( null, driver)) {
		String pathFile = "D:\\logKD.xls";
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		for (int m = 0; m < listPerson.size(); m++) {
		String[] strings = listPerson.get(m);
		if(strings[11] == null || strings[11].isEmpty()) {
			textArea = strings[10]+" \n";
			String egn = strings[0];
			String firstName = "";
			String secontName = "";
			String lastName = "";
				for (int i = 0; i < 11; i++) {
					if(strings[i] == null || strings[i].isEmpty()) {
						strings[i] = "    ";
					}
					textArea += strings[i]+" ";
					if(i==5) {
						textArea +="\n"+strings[0]+" ";
					}
				}
				System.out.println(textArea);
			String[] masiveData = extractedMasiveInfoPerson(driver, firstName, secontName, lastName, egn);
			System.out.println("masiveData[0] "+masiveData[0]);
			if (masiveData[0] == null) {
				textArea = "Няма намерени резултати";
			}
			if(OptionDialog(textArea, "")==0) {
				if(!textArea.equals("Няма намерени резултати")) {
				updatePersonFromExcelFile( workbook,  egn);
				}
			}else {
				m = listPerson.size();
					try {
				FileOutputStream fileOut = new FileOutputStream(pathFile);
				workbook.write(fileOut);
				fileOut.close();
				
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		}

			}
			
			driver.quit();
		
	}
	
	public static String[] extractedMasiveInfoPerson(ChromeDriver driver, String firstName, String midName,
			String lastName, String egn) {

		List<String> listAllInfo;
		// 4. Навигация към подстраница
		driver.get("https://sis-09/ACS/Personal/Persons.aspx");

		String[] masiveData = new String[9];;

		// 5. Попълване на поле с Име
		WebElement firstNameField = driver.findElement(By.id("ContentPlaceHolderBody_tb_FirstName"));
		firstNameField.clear();
		firstNameField.sendKeys(firstName);

		// 5. Попълване на поле с Презиме
		WebElement midNameField = driver.findElement(By.id("ContentPlaceHolderBody_tb_MidName"));
		midNameField.clear();
		midNameField.sendKeys(midName);

		// 5. Попълване на поле с Фамилия
		WebElement lastNameField = driver.findElement(By.id("ContentPlaceHolderBody_tb_LastName"));
		lastNameField.clear();
		lastNameField.sendKeys(lastName);

		// 5. Попълване на поле с ЕГН
		WebElement egnField = driver.findElement(By.id("ContentPlaceHolderBody_tb_PID"));
		egnField.clear();
		egnField.sendKeys(egn);

		// 6. Натискане на бутон за търсене/потвърждение
		WebElement searchButton = driver.findElement(By.id("ContentPlaceHolderBody_btn_Search"));
		searchButton.click();

		// намираме таблицата по ID (може и по xpath, css)
		listAllInfo = PersonReferenceInWebKD_Methods.extractedInfoByTable(driver, "ContentPlaceHolderBody_gv_Result");
	
		
		String str = null;
		System.out.println("listAllInfo.size() "+listAllInfo.size());
		if (listAllInfo.size() > 0) {
		
			System.out.println("11111111111222222222222");
			if (listAllInfo.size() > 25) {
				System.out.println("2222222222222222");
				PersonReferenceInWebKD_Frame.MessageDialogText("Прецизирайте търсенето", "Има повече от 25 резултата");
				
			} else {
				System.out.println("333333333333333");
				if (listAllInfo.size() == 1) {
					System.out.println("4444444444444444");
					masiveData = readMasive(driver);
				} else {
					System.out.println("55555555555555555");
					String[] masive = new String[listAllInfo.size()];
					int i = 0;
					for (String string : listAllInfo) {
						System.out.println(string);
						String[] mstr = string.replace("&*", "").split("@#");
						masive[i] = mstr[1].trim() + " - " + mstr[2];
						i++;
					}
					str = PersonReferenceInWebKD_Frame.InputDialog(masive);
					System.out.println("1 " + str);
					if (str != null) {
						String[] masiveChoise = new String[4];
						for (String string : listAllInfo) {
							String[] mstr = string.split("@#");
							System.out.println("mstr[1] " + mstr[1]+" - "+str.split("-")[0].trim());
							if(mstr[1].trim().equals(str.split("-")[0].trim())){
								System.out.println("mstr[k] " + mstr[1]+" "+mstr[2]);
								masiveChoise[0] = mstr[1].trim();
								
								if(mstr[2].trim().split(" ").length > 2) {
								masiveChoise[1] = mstr[2].trim().split(" ")[0].trim();
								masiveChoise[2] = mstr[2].trim().split(" ")[1].trim();
								masiveChoise[3] = mstr[2].trim().split(" ")[2].trim();
								}else {
									masiveChoise[1] = mstr[2].trim().split(" ")[0].trim();
									masiveChoise[2] = "";
									masiveChoise[3] = mstr[2].trim().split(" ")[1].trim();
								}
							}
						}
					System.out.println("66666666666666666");
					System.out.println(masiveChoise[0]+" "+masiveChoise[1]+" "+masiveChoise[2]+" "+ masiveChoise[3]);
//					masiveData = extractedMasiveInfoPerson(driver, masiveChoise[1],"", "", masiveChoise[0]);
					}
				}
			}
		}

		return masiveData;
	}

	private static String[] readMasive(ChromeDriver driver) {
		
		String firstName;
		String midName;
		String lastName;
		String[] masiveData = new String[10];
		try {
		
			WebElement table = driver.findElement(By.id("ContentPlaceHolderBody_gv_Result"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));

			List<WebElement> cells = rows.get(1).findElements(By.tagName("td"));
			cells.get(0).click();

			System.out.println("+++++++++++++++++++");

			
			firstName = driver.findElement(By.id("ContentPlaceHolderBody_uc_Person_Basic_Info_lbl_First_Name"))
					.getText();
			midName = driver.findElement(By.id("ContentPlaceHolderBody_uc_Person_Basic_Info_lbl_Mid_Name")).getText();
			lastName = driver.findElement(By.id("ContentPlaceHolderBody_uc_Person_Basic_Info_lbl_Last_Name")).getText();

			masiveData[0] = firstName + " &&" + midName + " &&" + lastName;

			masiveData[1] = driver.findElement(By.id("ContentPlaceHolderBody_uc_Person_Basic_Info_lbl_PID")).getText();
			
			masiveData[2] = "";
			masiveData[3] = "";
			masiveData[4] = "";
			masiveData[5] = "";
			masiveData[7] = "";
			masiveData[9] = "";
			try {masiveData[2] = driver.findElement(By.id("ContentPlaceHolderBody_lbl_Company_Name")).getText();} catch (NoSuchElementException e) {}
			try {masiveData[3] = driver.findElement(By.id("ContentPlaceHolderBody_lbl_Department_Code")).getText();} catch (NoSuchElementException e) {}
			try {masiveData[4] = driver.findElement(By.id("ContentPlaceHolderBody_lbl_Position")).getText();} catch (NoSuchElementException e) {}
			try {masiveData[5] = driver.findElement(By.id("ContentPlaceHolderBody_lbl_Unfit")).getText();} catch (NoSuchElementException e) {}
			try {masiveData[7] = driver.findElement(By.id("ContentPlaceHolderBody_lbl_SMM_Measurement_Date")).getText();} catch (NoSuchElementException e) {}
			try {masiveData[9] = driver.findElement(By.id("ContentPlaceHolderBody_uc_Person_Basic_Info_lbl_Sex")).getText();} catch (NoSuchElementException e) {}
			

			List<String> listKods = PersonReferenceInWebKD_Methods.extractedInfoByTable(driver, "ContentPlaceHolderBody_gv_Dosimetry");
			masiveData[6] = "";
			for (String string : listKods) {
				masiveData[6] += string + "@2#";
			}

			masiveData[8] = "";
			try {
			// Намираме изображението (примерно по ID)
			WebElement imgElement = driver.findElement(By.id("ContentPlaceHolderBody_uc_Person_Basic_Info_img_Photo"));
			// Зареждаме изображението от URL
			masiveData[8] = imgElement.getAttribute("src");
			} catch (NoSuchElementException e) {}
			
//			System.out.println("masiveData[8] "+ masiveData[8].substring(0, 20));
			
		} catch (NoSuchElementException e) {
	}		
			
			
			
			
			
			
			
			
			
			
			
			
			 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		        // 1️⃣ Намираме контейнера, върху който трябва да се "hover"-не
		        WebElement toolbarPanel = wait.until(
		            ExpectedConditions.presenceOfElementLocated(
		                By.id("ContentPlaceHolderBody_pnl_Dosimetry_Toolbar")
		            )
		        );

		        // 2️⃣ Намираме самата икона (input type="submit")
		        WebElement editIcon = driver.findElement(
		            By.id("ContentPlaceHolderBody_btn_Dosimetry_Edit")
		        );

		        // 3️⃣ Симулираме hover върху панела
		        Actions actions = new Actions(driver);
		        actions.moveToElement(toolbarPanel).perform();

		        // 4️⃣ Изчакваме иконата да стане видима и кликаема
		        wait.until(ExpectedConditions.elementToBeClickable(editIcon));

		        // 5️⃣ Кликваме върху иконата
		        editIcon.click();

		        System.out.println("Иконата е натисната успешно.");

		return masiveData;
	}

	public static int OptionDialog(String dialogText, String text) {
		String[] options = {"next", "end"};
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
         return JOptionPane.showOptionDialog(jf,dialogText, text, 
        		 JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
       
        /**
         * select "abc" -> 0;
         * select "def" -> 1;
         * select "ghi" -> 2;
         * select "jkl" -> 3;
         */
	}

	public static  void updatePersonFromExcelFile(Workbook workbook, String egn) {
		
		Sheet sheet = workbook.getSheetAt(0);
		CellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
		Cell cell,cell1;
		
		int StartRow = 0;
		int endRow = sheet.getLastRowNum();
		
		for (int row = StartRow; row < endRow ; row += 1) {
	if (sheet.getRow(row) != null && row > 0) {
				cell = sheet.getRow(row).getCell(0);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					if (ReadExcelFileWBC.getStringEGNfromCell(cell).equals(egn)) {
						cell.setCellStyle(cellStyle);
						cell1 = sheet.getRow(row).createCell(11,  CellType.STRING);
						cell1.setCellValue("1");
					}
				}
			
			}
			
		}
	
		
	}

	public static List<String[]> readPersonFromExcelFile() {
		String pathFile = "D:\\logKD.xls";
		Workbook workbook = ReadExcelFileWBC.openExcelFile(pathFile);
		Sheet sheet = workbook.getSheetAt(0);
		Cell cell;
		List<String[]> listPerson = new ArrayList<>();
		String[] masive = new String[12];
		int endRow = 0;
		
		endRow = sheet.getLastRowNum();
		
		for (int row = 0; row < endRow ; row += 1) {

			if (sheet.getRow(row) != null && row > 0) {
				masive = new String[12];
				for (int i = 0; i < 12; i++) {
				cell = sheet.getRow(row).getCell(i);
				if (ReadExcelFileWBC.CellNOEmpty(cell)) {
					masive[i] = ReadExcelFileWBC.getStringEGNfromCell(cell);
					System.out.println(row+"-"+i+" "+masive[i]);
			
				}
			}
		
				listPerson.add(masive);
			}
			
		}
		System.out.println("sssssssssssssssss "+listPerson.size());
	
		return listPerson;
	}
	
}	

