package com.java_IO_framework20241014;

import java.io.FileInputStream;
import java.io.IOException;

public class InputStream20241015 {
	/*
	 InputStream:
	 
	 	是Java中用於Byte Streams的抽象基類
	 	
	 	***需要注意的是，InputStream是一個抽象類，通常我們會使用它的具體子類，如FileInputStream、ByteArrayInputStream等
	 	
	 	
	 	
	 	常見的實現類:
	 	
	 	
	 	FileInputStream
			功能： 從文件讀取數據。
			常用場景： 讀取本地文件內容，如文本文件、圖片、音頻等。
	 	
	 	
	 	ByteArrayInputStream
			功能： 從Byte數組讀取數據。
			常用場景： 處理內存中的數據，如將Byte數組轉換為 InputStream 對象。
	 	
	 	
	 	
	 	BufferedInputStream
	 		建立緩衝區： 在內部建立一個Byte數組作為緩衝區。
			預讀數據： 從原始的 InputStream 中預讀取一定量的數據到緩衝區中。
			從緩衝區讀取： 當應用程式請求讀取數據時，優先從緩衝區中讀取，只有當緩衝區中的數據不足時，才會再次從原始 InputStream 中讀取數據填充緩衝區。
			
		
		DataOutputStream
			DataOutputStream 是用來將基本數據類型以二進位形式寫入輸出串流中，這樣可以確保數據在不同平台之間可以正確地傳輸。
	 	
	 	
	 	
	 	常見方法:
	 	
	 	
	 	int read(): 從輸入流中讀取下一個字節的數據。
			返回：讀取的字節值（0-255），如果已到達流的末尾則返回-1。
		
		
		int read(byte[] b): 從輸入流中讀取一些字節數，並將它們存儲到緩衝區數組b中。
			返回：實際讀取的字節數，如果已到達流的末尾則返回-1。
			
			
		int read(byte[] b, int off, int len): 從輸入流中讀取最多len字節的數據到數組b中，從off位置開始存儲。
			返回：實際讀取的字節數，如果已到達流的末尾則返回-1。
		
		
		long skip(long n): 跳過並丟棄輸入流中的n個字節的數據。
			返回：實際跳過的字節數。
		
		
		int available(): 返回可以不受阻塞地從此輸入流中讀取的字節數。
			返回：可以不受阻塞地讀取的字節數估計值。
		
		
		void close(): 關閉此輸入流並釋放與該流關聯的所有系統資源。
			返回：無返回值（void）。
		
		
		void mark(int readlimit): 在此輸入流中標記當前的位置。
			返回：無返回值（void）。
		
		
		void reset(): 將此流重新定位到最後一次調用mark方法時的位置。
			返回：無返回值（void）。
		
		
		boolean markSupported(): 測試此輸入流是否支持mark和reset方法。
			返回：如果此流支持mark和reset操作則返回true，否則返回false。
	 */
	/////////////////////////////////////////////////
	
	
//	System 類:
	
//	System.in（標準輸入流）
	//	類型：InputStream
	//	用途：用於從標準輸入（通常是鍵盤）接收輸入數據。
	//	常見用法：配合 Scanner 類來讀取用戶輸入。
	
	
	//////////////////////
	//例子:
	public static void main(String[] args) {
		try (FileInputStream fis = new FileInputStream("example.txt")) {
            int data;
            while ((data = fis.read()) != -1) {
            	//這個循環一次讀取一個字節，直到文件結束（read()方法返回-1）。
            	//每個讀取的字節被轉換為字符並打印出來。
            	
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
