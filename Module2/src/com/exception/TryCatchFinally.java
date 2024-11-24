package com.exception;

import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;

public class TryCatchFinally {

	public static void main(String[] args) {
		/*
		 重要提示:

			1.只捕捉你能處理的例外。
			2.盡量使用具體的例外類型,而不是籠統的Exception。
			3.在catch塊中,至少要記錄錯誤素息,不要空著不處理。
			4.適當使用finally塊來確保資源被正確釋放。
		 */
		
		
		
		// 基本的try-catch結構
		try {
			int result = 10 / 0; // 這會拋出 ArithmeticException
		} catch (ArithmeticException e) {
			System.out.println("除以零錯誤: " + e.getMessage());
		}

		///////////////////////////////////////

		// 可以使用多個catch塊來處理不同類型的例外。
		// 注意:更具體的例外應該放在前面的catch塊中。
		try {
			// 可能拋出多種例外的代碼
		} catch (ArithmeticException e) {
			System.out.println("算術錯誤: " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("空指針錯誤: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("其他錯誤: " + e.getMessage());
		} finally {
			//finally塊中的代碼無論是否發生例外都會執行,通常用於清理資源。
			System.out.println("這段代碼總是會執行");
		}
	}
	////////////////////////////////////////////////////
	
	/*
	例外傳遞（Exception Propagation）:
	
	是指當程式碼中發生例外（Exception）時，該例外會沿著調用堆疊（call stack）向上傳遞，直到被適當的例外處理機制（如 try-catch 區塊）捕捉並處理。
	若例外未被捕捉，則最終會導致程式異常終止。
	 */
	
	
	public void checkAge(int age) throws IllegalArgumentException {
		//throws 用於在方法簽名中「聲明」該方法可能拋出的例外，讓呼叫者知道需要處理這些例外。可以聲明多個例外
		// 聲明的意思:不包含具體的錯誤信息，因為它只是一個可能性的聲明。
		//***傳播例外:如果你不想在當前方法中處理某個例外,可以使用 throws 將其傳播給調用者，讓呼叫者知道需要處理這些例外(這樣可以使你的方法暫時不需要處理錯誤，而是讓調用者自行處理錯誤)
		
		
		/*	
		基本上throws只會用於受檢例外，為什麼對非受檢例外使用 throws 通常沒有必要:
		
			編譯器不強制：如上所述，編譯器不會強制你處理非受檢例外。即使你在方法簽名中使用了 throws 聲明，呼叫這個方法的程式碼也可以選擇不處理這個異常。
			程式設計錯誤：非受檢例外通常是由程式設計錯誤引起的，例如陣列越界、空指標引用等。這些錯誤應該在編寫程式碼時就避免，而不是在程式執行時才去處理。
		 */
	    
		
		
		if (age < 0) {
	        throw new IllegalArgumentException("年齡不能為負數");
	        //某些情況下，開發者需要主動拋出例外以指示某種錯誤狀態
	        //throw 用於主動拋出一個例外物件。
	        //***需要創建一個具體的例外物件，通常包含錯誤信息。
	        
	    }
		/////////////////////////////////////////////////
	    
		/*
		利用例外類別的繼承關係，使用父類別來捕捉多組例外。
		這種方法可以讓你在一個 catch 區塊中處理多種類型的例外，特別是當這些例外共享一個共同的父類別時。
		 */
		
		
		/*
		使用共同的父類別捕捉例外:
		
			假設你有多種例外類型，如 IOException 和 SQLException，它們都繼承自 Exception 類別。
			你可以使用 Exception 作為父類別來捕捉這些例外：
		 
		 缺點:
		 
		 	可能會捕捉到你不希望捕捉的其他例外，降低代碼的精確性和可讀性。
		 */
		try {
		    // 可能會拋出 IOException 或 SQLException 的代碼
		} catch (Exception e) {
		    // 處理 IOException 和 SQLException 以及其他所有 Exception 的代碼
		}
		
		///////////////////////////////////////////////
		
		
		/*
		Java 7 引入的多重捕捉（Multi-catch）:
		
			可以在單一的 catch 區塊中捕捉多種類型的例外，並且這些例外類型之間不能有繼承關係（即沒有父子類別關係），以避免不必要的混淆。
		 */
		try {
		    // 可能會拋出 IOException 或 SQLException 的代碼			
			if (Math.random() < 0.5) {
                // 模擬 IOException
                FileReader reader = new FileReader("nonexistent.txt");
            } else {
                // 模擬 SQLException
                DriverManager.getConnection("jdbc:invalid:url");
            }
		} catch ( IOException | SQLException e) {
		    // 處理 IOException 和 SQLException 的代碼
			System.out.println("捕獲到 IO 或 SQL 異常: " + e.getMessage());
		}
		
		
		////////////////////////////////////////////////////////
		
		
		/*
		try-with-resources:用於自動管理資源,特別是那些需要手動關閉的資源。
		
		
		基本概念:

			1.它是一個try語句,可以在括號中聲明一個或多個資源。
			2.這些資源會在try塊結束時自動關閉,無論try塊是正常結束還是因為異常而結束。
			3.可以使用這種方式管理的資源必須實現java.lang.AutoCloseable介面。
		
		
		
		基本語法:
		
		try (Resource resource = new Resource()) {
		    // 使用資源的代碼
		} catch (Exception e) {
		    // 異常處理
		}
		 */  
		
		
		try (FileInputStream input = new FileInputStream("input.txt");
	         FileOutputStream output = new FileOutputStream("output.txt")) {
			//可以同時使用多個資源，兩個資源都會在try塊結束時自動關閉。
	            
	            int data;
	            while ((data = input.read()) != -1) {
	                output.write(data);
	            }
	            System.out.println("File copied successfully");
	        } catch (IOException e) {
	            System.out.println("Error copying file: " + e.getMessage());
	        }
		
		
		/*
		注意事項:

			1.資源的關閉順序與它們的聲明順序相反。
			2.如果try塊和close()方法(內建在 try-with-resources 語句的一個重要特性)都拋出異常,try塊中的異常會被拋出,close()方法的異常會被抑制(Suppressed)。
				close() 方法可能會拋出異常，比如當關閉文件流（FileInputStream 或 FileOutputStream）時，如果底層文件系統出現問題就會出現IOException。
				
				說明:
					(1)「抑制」意味著Java認識到這個異常，記錄下來，但暫時不讓它影響主要的程序流程，而是提供一種方法讓我們之後可以檢查和處理它。
					(2)這是為了確保原始的異常（可能是導致問題的根本原因）不會被資源關閉時的異常所掩蓋。
			3.你可以使用 Throwable.getSuppressed() 方法來獲取被抑制(Suppressed)的異常。
		 */
		
		}
}
