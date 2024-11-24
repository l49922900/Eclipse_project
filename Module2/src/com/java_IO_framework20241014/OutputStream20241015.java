package com.java_IO_framework20241014;

import java.io.FileOutputStream;
import java.io.IOException;

public class OutputStream20241015 {
	/*
	OutputStream :
	
		是 Java I/O 類別中的一個抽象基類,用於將數據寫入到不同類型的輸出目標。
		它是字節(Byte Streams)輸出流的所有類的超類。
		
	***需要注意的是，OutputStream是一個抽象類，通常我們會使用它的具體子類，如FileOutputStream, 
		
		
	常見的實現類:
	
		FileOutputStream:
			緩衝： 提高寫入效率，減少系統調用次數。
			字符編碼： 將character數據轉換為byte流寫入文件。
			自動刷新： 定期將緩衝區中的數據寫入文件。
			其他功能： 比如支持隨機訪問、加密等。
	
		BufferedOutputStream：
			作用： 提供一個緩衝區，將要寫入的數據暫時存儲在緩衝區中，當緩衝區滿了或者調用 flush() 方法時，才會將緩衝區中的數據一次性寫入文件。
			優點： 大大提高了寫入效率，特別是當寫入的數據量較大時。
			***通常包裹FileOutputStream一起用
		
		
		DataInputStream 的使用
			DataInputStream 則是用來從輸入串流中讀取基本數據類型。
			它會依照正確的類型讀取二進位數據並將其轉換為對應的Java基本類型。
			
		
		
	常見方法:	
	
		write(int b): void
			將指定的字節寫入輸出流。
		
		
		write(byte[] b): void
			將字節數組寫入輸出流。
		
		
		write(byte[] b, int off, int len): void
			將字節數組的一部分寫入輸出流。
		
		
		flush(): void
			刷新輸出流,強制寫出所有緩衝的輸出字節。
			***基本上程式結束前都會使用flush
		
		
		close(): void
			關閉輸出流並釋放與之相關聯的任何系統資源。
	 */
	///////////////////////////////////
	
//	System 類:
		
//	System.out（標準輸出流）:
	//	類型：PrintStream，但 PrintStream 本質上是基於 OutputStream 的。
	//	用途：用於向標準輸出（通常是控制台）輸出正常的程序訊息或結果。
	//	常見用法：使用 print、println 或 printf 方法來輸出信息。
	
	
//	System.err（標準錯誤流）
	//	類型：PrintStream，但 PrintStream 本質上是基於 OutputStream 的。
	//	用途：用於向標準錯誤（通常也是控制台，但可以獨立於標準輸出進行重定向）輸出錯誤訊息或警告。
	//	常見用法：使用 print、println 或 printf 方法來輸出錯誤信息。
	
	
	///////////////////////////////////////
	
	public static void main(String[] args) {
		String fileName = "example.txt";
        String data = "Hello, OutputStream!";

        try (FileOutputStream out = new FileOutputStream(fileName)) {
            // 使用 write(int b) 方法寫入單個字節
            out.write(72);  // 寫入字符 'H'

            // 使用 write(byte[] b) 方法寫入字節數組
            out.write(data.getBytes());

            // 使用 write(byte[] b, int off, int len) 方法寫入字節數組的一部分
            byte[] moreData = ", Welcome!".getBytes();
            out.write(moreData, 0, 9);  // 只寫入 ", Welcome"

            // 使用 flush() 方法刷新輸出流
            out.flush();

            System.out.println("數據已成功寫入文件: " + fileName);
        } catch (IOException e) {
            System.err.println("寫入文件時發生錯誤: " + e.getMessage());
        }
        // close() 方法在 try-with-resources 結構中自動調用
    

	}
}
