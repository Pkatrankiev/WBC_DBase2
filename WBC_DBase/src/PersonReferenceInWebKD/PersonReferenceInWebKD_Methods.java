package PersonReferenceInWebKD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Aplication.ActionIcone;
import Aplication.ReadFileBGTextVariable;


public class PersonReferenceInWebKD_Methods {

	public static ChromeDriver openChromeDriver(boolean NOTviewBrauser) {
		// Задай пътя към ChromeDriver (изтегли от https://chromedriver.chromium.org/)
		
		String chromedriver = ReadFileBGTextVariable.getGlobalTextVariableMap().get("chromedriver");
		String chromeApp = ReadFileBGTextVariable.getGlobalTextVariableMap().get("chromeApp");
	
		
		System.setProperty("webdriver.chrome.driver", chromedriver);

		ChromeOptions options = new ChromeOptions();
		options.setBinary(chromeApp);
       if(NOTviewBrauser) {
    	   options.addArguments("--headless");
       }

		// Стартира браузъра
		ChromeDriver driver = new ChromeDriver(options);
		return driver;
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
		listAllInfo = extractedInfoByTable(driver, "ContentPlaceHolderBody_gv_Result");
	
		
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
					masiveData = extractedMasiveInfoPerson(driver, masiveChoise[1],"", "", masiveChoise[0]);
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

//			listAllInfo = extractedInfoByTable(driver, "ContentPlaceHolderBody_gv_Dosimetry");

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
			

			List<String> listKods = extractedInfoByTable(driver, "ContentPlaceHolderBody_gv_Dosimetry");
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
//		int k=0;
//		for (String string : masiveData) {
//			string = string.replace("&&", "");
//			System.out.println(k+" "+string.substring(0, (string.length() > 40 ? 40:string.length()))+" - "+string.length());
//			k++;
//		}
		
		
		return masiveData;
	}

	static BufferedImage extractedImage(String imgSrc) throws IOException, MalformedURLException {

		BufferedImage image = null;

		if (imgSrc.startsWith("data:image")) {
			// base64 декодиране
			String base64Data = imgSrc.split(",")[1]; // махаме "data:image/png;base64,"
			byte[] imageBytes = Base64.getDecoder().decode(base64Data);
			image = ImageIO.read(new ByteArrayInputStream(imageBytes));
		} else {
			// ако е нормален URL
			image = ImageIO.read(new java.net.URL(imgSrc));
		}
		return image;
	}
	
	public static boolean logInToWebSheet(ActionIcone round, ChromeDriver driver) {
		String nameWebSheet = "https://sis-09/ACS/Default.aspx";
		
		String chromeUser = ReadFileBGTextVariable.getGlobalTextVariableMap().get("chromeUser");
		String chromePas = ReadFileBGTextVariable.getGlobalTextVariableMap().get("chromePas");
		
		
		// 1. Отваряне на страницата за логване
		driver.get(nameWebSheet);
	boolean fl = true;
	int count = 0 ;	
	do {
	try {
		// 2. Попълване на потребител и парола
		WebElement usernameField = driver.findElement(By.id("username")); // замени с реалното id/name
		WebElement passwordField = driver.findElement(By.id("password"));
		usernameField.clear();
		usernameField.sendKeys(chromeUser);
		passwordField.clear();
		passwordField.sendKeys(chromePas);
		try { Thread.sleep(1000); } catch (InterruptedException e) {}
		// 3. Натискане на бутона за логин
		WebElement loginButton = driver.findElement(By.id("submitButton"));
		loginButton.click();
		
		try { Thread.sleep(1000); } catch (InterruptedException e) {}
		
		driver.findElement(By.id("ContentPlaceHolderBody_tb_FirstName"));
			} catch (NoSuchElementException e) {
				fl = false;
			}
	count++;
	System.out.println(count+" - "+fl);
	}while (count < 4 && !fl);
	
	if(!fl) {
		if(round != null) {
			round.StopWindow();
			}
			PersonReferenceInWebKD_Frame.MessageDialogText("Опитайте отново", "Проблем с връзката");
	}
	
		return fl;
	}

	static List<String> extractedInfoByTable(ChromeDriver driver, String nameTable) {

		List<String> listAllInfo = new ArrayList<>();
		
	
		try {
		WebElement table = driver.findElement(By.id(nameTable));
	
		// намираме всички редове (<tr>)
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		// обхождаме всеки ред

		for (WebElement row : rows) {
			// намираме всички клетки (<td> или <th>) в реда
			List<WebElement> cells = row.findElements(By.tagName("td"));

			// печатаме съдържанието
			String info = "";
			int c = 0;
			for (WebElement cell : cells) {
				info += cell.getText().replace("\n", " ") + " @# ";
				c++;

			}
			if (c > 0) {
				listAllInfo.add(info);
			}
			System.out.println();
		}
		} catch (NoSuchElementException e) {
		}
		for (String string : listAllInfo) {
			System.out.println(string);
		}
		
		return listAllInfo;
	}

	


}
