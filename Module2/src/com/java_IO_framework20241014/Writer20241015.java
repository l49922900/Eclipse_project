package com.java_IO_framework20241014;

public class Writer20241015 {
	/*
	 Writer :
	 
	 	主要用於寫入Character Streams。
	 	它是所有字符輸出流類的超類，提供了基本的寫入功能，並由多個具體子類（如 BufferedWriter、FileWriter、PrintWriter 等）實現具體的輸出操作。
	 	
	 	
	 常見的實現類:
	 
	 FileWriter:
		1.用於將Character寫入一個文件。
		2.常用於將數據持久化到本地文件系統。
		
		
	 BufferedWriter:
		1.提供字符流緩衝，可以提高寫入效率。
		2.通常與 FileWriter 結合使用，先將數據寫入緩衝區，然後再批量寫入文件。

	PrintWriter:
		1.提供格式化打印功能，可以方便地打印各種數據類型。
		2.常用於將格式化的數據寫入控制台、文件或其他輸出流。
		
		PrintWriter 的常用方法:
		
			1.println(String s)： 寫入一行文本，並在行尾添加換行符。
			2.print(String s)： 寫入一行文本，不添加換行符。
			3.printf(String format, Object... args)： 使用格式化字符串寫入文本，類似於 C 語言的 printf。
			4.format(String format, Object... args)： 與 printf 相同。
			5.flush()： 強制將緩衝區中的數據寫入輸出流。
			6.close()： 關閉 PrintWriter，釋放資源。


	OutputStreamWriter:
		1.將字符流轉換為位元組流，可以將字符數據寫入任何 OutputStream。
		2.常用於將字符數據寫入網絡套接字或其他二進制流。
	 	
	 	
	 常見方法:
	 
		 write(int c)
			描述：寫入單個字符。
			返回值：void
			
			
		write(char[] cbuf)
			描述：寫入字符數組。
			返回值：void
			
			
		write(String str)
			描述：寫入字符串。
			返回值：void	
			
			
		write(String str, int off, int len)
			描述：寫入字符串的部分內容。
			返回值：void	
	 
	 
	    flush()
			描述：刷新緩衝區，確保所有數據都被寫出。
			返回值：void
			***基本上程式結束前都會使用flush
			
			
		close()
			描述：關閉流並釋放與之相關的資源。
			返回值：void	
			
			
		append(CharSequence csq)
			描述：追加字符序列。
			返回值：Writer（返回自身以支持鏈式調用）	
			
			
		append(CharSequence csq, int start, int end)
			描述：追加字符序列的部分內容。
			返回值：Writer	
			
			
		append(char c)
			描述：追加單個字符。
			返回值：Writer	
	 */
}
