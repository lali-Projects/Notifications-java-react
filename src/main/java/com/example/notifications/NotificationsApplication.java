package com.example.notifications;

import io.github.cdimascio.dotenv.Dotenv;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;

@SpringBootApplication
@EnableScheduling
public class NotificationsApplication {

	public static void main(String[] args) {
		// טעינת הקובץ .env לתוך משתני המערכת
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(NotificationsApplication.class, args);

//		// 1. הגדרת הנתיב הבסיסי לתיקיית הפרויקט
//		String projectPath = System.getProperty("user.dir");
//
//		// הנתיב לתיקייה tesseract-bin (ודא שהיא בשורש הפרויקט)
//		String tessdataPath = "T:\\Group B\\Java_advanced\\Malki Salomon\\spring\\try\\Notifications\\tesseract-bin\\tessdata";
//
//		// 2. הגדרה קריטית למנוע של Tesseract איפה נמצאות השפות
//		// זה מה שפותר את השגיאה של "Failed loading language"
//		System.setProperty("TESSDATA_PREFIX", tessdataPath);
//
//		// 3. יצירת מופע של Tesseract
//		Tesseract tesseract = new Tesseract();
//
//		// הגדרת נתיב הנתונים והשפה
//		tesseract.setDatapath(tessdataPath);
//		tesseract.setLanguage("eng+heb"); // טוען גם אנגלית וגם עברית
//
//		try {
//			// 4. הגדרת התמונה לבדיקה
//			// וודא שיש קובץ בשם test.png בתיקייה הראשית של הפרויקט
//			File imageFile = new File("test.png");
//
//			if (!imageFile.exists()) {
//				System.out.println("שגיאה: הקובץ test.png לא נמצא בתיקיית הפרויקט!");
//				return;
//			}
//
//			System.out.println("מבצע OCR על התמונה...");
//
//			// 5. ביצוע הפענוח
//			String result = tesseract.doOCR(imageFile);
//
//			// 6. הדפסת התוצאה
//			System.out.println("--- תוצאת הפענוח ---");
//			System.out.println(result);
//			System.out.println("---------------------");
//
//		} catch (TesseractException e) {
//			System.err.println("קרתה שגיאה במהלך ה-OCR:");
//			e.printStackTrace();
//		}

//		SpringApplication.run(NotificationsApplication.class, args);
//
//		System.out.println("המערכת עלתה וספריות OpenCV מוכנות לשימוש");
	}
}