package com.java_IO_framework20241014;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader20241015 {
	/*
	Reader:
	
		是一個抽象類，用於讀取字符流。它是Character Streams的基類，提供了一系列讀取字符的方法。
		
		
		
	Reader 的常見實現類:	
	
	FileReader:

		功能: 從文件讀取字符數據。
		優點: 針對文件讀取做了優化，效率較高。
		使用場景: 大多數情況下，當需要從文件讀取文本數據時，FileReader 是首選。

	InputStreamReader:

		功能: 將字節輸入流轉換為字符輸入流。
		優點: 可以將任何 InputStream（如 FileInputStream、ByteArrayInputStream）包裝成 Reader，提供字符級的讀取。
		使用場景: 當需要從字節流中讀取字符數據時，例如從網絡套接字或壓縮流中讀取數據。

	BufferedReader:

		功能: 提供緩衝功能，可以一次讀取多個字符，提高讀取效率。
		優點: 對於需要頻繁讀取數據或讀取大文件的情況，BufferedReader 可以顯著提高性能。
		***使用場景: 通常與 FileReader 或 InputStreamReader 結合使用，提供緩衝功能。
			BufferedReader 內部維護了一個緩衝區（通常是字符數據的陣列）。
			當你從 BufferedReader 讀取數據時，它會一次性從底層的 Reader（例如 FileReader）中讀取一大塊數據，存儲在緩衝區中。
			這樣，後續的讀取操作就可以直接從緩衝區中獲取數據，而不需要每次都進行一次耗時的IO操作。
		
	LineNumberReader:
	
	 	是 Java 中一個用於讀取文字檔案的實用類別。
	 	***它繼承自 BufferedReader，並增加了追蹤行號的功能。這在需要知道目前讀取到哪一行的情境中特別有用。
		
		特有方法:
			1.getLineNumber(): 回傳目前行號。
			2.setLineNumber(int lineNumber): 設定目前行號。
			3.readLine(): 讀取一行文字。
		
	常見方法:
		
		read():
			返回值類型: int
			返回讀取的字符（作為整數），如果到達流的末尾則返回-1
		
		
		read(char[] cbuf):
			返回值類型: int
			返回實際讀取的字符數，如果到達流的末尾則返回-1
		
		
		skip(long n):
			返回值類型: long
			返回實際跳過的字符數
		
		
		ready():		
			返回值類型: boolean
			如果下一次讀取不會阻塞則返回true，否則返回false
		
		
		mark(int readAheadLimit):
			返回值類型: void
			不返回任何值，僅標記當前位置
			
		reset():	
			返回值類型: void
			不返回任何值，僅將流重置到之前標記的位置
		
		readLine() (BufferedReader與LineNumberReader特有):
			返回值類型: String
			返回讀取的字符串行，如果到達流的末尾則返回null
		
		close():
			返回值類型: void
			不返回任何值，僅關閉流並釋放資源
	 */
	
	public static void main(String[] args) {
		
//		例子:
		String filePath = "example.txt";
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
//        	包裝 BufferedReader：將 FileReader 作為參數傳遞給 BufferedReader 的構造方法，從而啟用緩衝功能。
        	
        	String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
