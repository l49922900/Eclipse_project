package com.java_IO_framework20241014;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class JavaIOframework20241015 {
	/*
	Java 的 I/O 系統主要基於串流（Stream）的概念。
	串流是資料的有序序列，能夠從一個來源讀取資料（輸入流），或將資料寫入到一個目的地（輸出流）。
	
	Java I/O 包含兩大類：

		1.位元組流、字節流（Byte Streams）：以 8 位元組為單位處理資料，適用於所有類型的資料（如圖像、音頻等）。
		2.字元流（Character Streams）：以 16 位元組（Java 的 char 類型）為單位處理資料，專為處理文字資料設計，支援字符編碼。
	 
	 	Byte(字節)：
			1.是電腦資訊的基本單位，通常用來表示一個字元（ASCII碼）或一個小整數。
			2.在 Java 中，byte 型別是一個 8 位的有符號整數。
			
			
		Character(字符)：
			1.是文字的抽象表示，可以是一個字母、數字、標點符號或其他 Unicode 字符。
			2.在 Java 中，char 型別是一個 16 位的無符號 Unicode 字符。
	 
	 ///////////////////////////////////////////////////////
	 
	 Node Stream & Processing Stream:
	 
	 
	 Node Stream:
	 
	 	直接連接資料來源或目的地：節點串流與實際的資料來源（例如檔案、網路 socket）直接互動。
		基本功能：僅負責基本的讀寫操作，不提供資料處理或增強功能。
		不支援鏈式操作：節點串流本身無法與其他串流進行組合或裝飾，需透過處理串流來實現額外功能。
	 	***為了增強 I/O 操作的功能和效率，通常會將Node Stream與Processing Stream結合使用
	 	
	 	
	 	常見的Input Node Streams:
	 		1.FileInputStream：用於從檔案中讀取位元組資料。
			2.FileReader：用於從檔案中讀取字元資料。
			3.SocketInputStream：用於從網路連線中讀取資料。
	 
	 	常見的Output Node Streams：
			1.FileOutputStream：用於向檔案中寫入位元組資料。
			2.FileWriter：用於向檔案中寫入字元資料。
			3.SocketOutputStream：用於向網路連線中寫入資料。
				 
				 
	 Processing Stream:
	 
	 	為了提升 I/O 操作的效率和靈活性，Java 引入了處理串流，將節點資料流串接特殊的處理資料流,以便使用特定的方法來存取資料
	 	
	 	緩衝處理串流：
			
			緩衝:一個用於存儲數據的內存區域，通常用於在數據傳輸過程中臨時保存數據。通過在內存中批量處理數據，減少對磁盤或網絡等較慢設備的直接訪問。
			BufferedInputStream 和 BufferedOutputStream：透過增加緩衝區減少實際的 I/O 操作次數，提升讀寫效率。
			BufferedReader 和 BufferedWriter：用於Character Streams，提供逐行讀取與寫入的功能。
			

		轉換處理串流：

			InputStreamReader 和 OutputStreamWriter：用於在Byte Streams與Character Streams之間進行轉換，支援指定的字元編碼。
	 
	 
	/////////////////////////////////////
	
	***主要介面:
	
		位元組流（Byte Streams）:
			InputStream：所有位元組輸入串流的超級類別。
				常見子類別:如 FileInputStream
				
			OutputStream：所有位元組輸出串流的超級類別。
				常見子類別:如 FileOutputStream
			
		字元流（Character Streams）：	
			Reader：所有字元輸入串流的超級類別。
				常見子類別:FileReader
				
			Writer：所有字元輸出串流的超級類別。
				常見子類別:FileWriter
	 */		
	
	public static void main(String[] args) throws IOException {
		
		///////////////////////////////////////////////////////
		
//		常見的輸入與輸出（I/O）處理方法的概念或方式
		
		
		/*
		檔案及目錄（File and Directory）

			
			檔案操作：例如，從檔案中讀取文字或二進位資料，將資料寫入檔案等。
			目錄操作：創建、刪除、列出目錄中的檔案等。
		 */
		Path path = Paths.get("example.txt");
		Files.write(path, "Hello World".getBytes());
		
		
		/*
		終端機（Console）
		
			終端機（Console） 是用來與使用者互動的一個標準I/O介面。
			程式可以透過終端機接收使用者的輸入，並將結果輸出到螢幕上。
			在 Java 中，最常見的終端機操作是使用 System.in 和 System.out。
			
			標準輸入（Standard Input）：透過 System.in 讀取終端機的輸入。
			標準輸出（Standard Output）：透過 System.out 在終端機顯示結果。
		 */
		Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入你的名字: ");
        String name = scanner.nextLine();
        System.out.println("你好, " + name + "!");
		
        /*
        插口端點（Socket-based）
        
			插口端點（Socket） 是用來進行網路通訊的一種方式。
			Socket 提供了一種雙向通訊通道，允許兩個不同的設備通過網路來互相傳輸資料。
			它通常用於建立客戶端-伺服器架構，比如網路伺服器和瀏覽器之間的資料傳輸。
			
			Client Socket（客戶端插口）：用來連接到伺服器，並傳送或接收資料。
			Server Socket（伺服器插口）：伺服器端等待客戶端的連接，並處理傳輸的資料。
         */
	}
}
