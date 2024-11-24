package com.exception;

public class CheckedExceptionsAndUncheckedExceptions20241005 {
	/*
	層次結構:
	 	
		Throwable
		|
		+-- Error (Unchecked)
		|
		+-- Exception
		    |
		    +-- RuntimeException (Unchecked)
		    |   |
		    |   +-- NullPointerException
		    |   +-- ArrayIndexOutOfBoundsException
		    |   +-- IllegalArgumentException
		    |   +-- ArithmeticException
		    |   +-- ...
		    |
		    +-- IOException (Checked)
		    +-- SQLException (Checked)
		    +-- ClassNotFoundException (Checked)
		    +-- ...
	
	
	Throwable:

		Throwable是Java異常層次結構的根類。它是所有錯誤(Error)和異常(Exception)的超類。
	
	特點:

		1.包含了其子類所需要的共同方法,如getMessage(), printStackTrace()等。
		2.可以在try-catch語句中被捕獲。
		3.可以被throw語句拋出。	    
		    
	
	
	
	
	
	Error (Unchecked):

		Error類代表了嚴重的問題,通常是系統級的錯誤,不應該被應用程序捕獲和處理。

	特點:

		繼承自Throwable。
		屬於非受檢異常(Unchecked),不需要強制處理。
		例如: OutOfMemoryError, StackOverflowError。
	
	
	
	
	
	Exception:

		Exception類是所有異常的超類。它代表了程序運行時可能出現的各種異常情況。

	特點:

		繼承自Throwable。
		分為受檢異常(Checked)和非受檢異常(Unchecked)。
		所有直接繼承自Exception的子類(除了RuntimeException及其子類)都是受檢異常。
	
	
	
	
	
	
	
	RuntimeException (Unchecked):

		RuntimeException是Exception的一個特殊子類,代表了那些可能在Java虛擬機正常運行期間拋出的異常。

	特點:

		繼承自Exception。
		屬於非受檢異常(Unchecked),不需要強制處理。
		通常由程序邏輯錯誤導致,應該通過修復代碼來預防。
	
    
    
    
    
	受檢例外(Checked Exceptions):

		這些是在編譯時期就必須處理的例外。
		如果方法中可能拋出這類例外,必須使用 try-catch 區塊處理或者在方法簽名中使用 throws 關鍵字聲明。
		常見的受檢例外包括 IOException, SQLException 等。
		直接繼承自Exception類（但不包括RuntimeException及其子類）
		常見的受檢例外包括：
			a. IOException: 輸入/輸出操作相關的例外
				- FileNotFoundException
				- EOFException
			b. SQLException: 數據庫操作相關的例外
			c. ClassNotFoundException: 嘗試加載類時找不到相應的類
			d. InterruptedException: 線程被中斷時拋出的例外
			e. CloneNotSupportedException: 嘗試克隆一個不支持克隆的對象時拋出

	
	
	
	非受檢例外(Unchecked Exceptions):

		這些例外不需要在編譯時期強制處理。
		它們通常是由程式邏輯錯誤導致的,理想情況下應該通過改進代碼來預防。
		所有繼承自 RuntimeException 的例外都屬於這一類。
		常見的非受檢例外包括 NullPointerException, ArrayIndexOutOfBoundsException 等。
	 	常見的非受檢例外包括：                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
			a. NullPointerException: 嘗試使用一個值為null的對象
			b. ArrayIndexOutOfBoundsException: 數組索引越界
			c. IllegalArgumentException: 傳遞給方法的參數不合法
			- NumberFormatException: 嘗試將字符串轉換為數字時格式不正確
			d. ArithmeticException: 算術錯誤，如除以零
			e. ClassCastException: 嘗試進行不允許的類型轉換
			f. IllegalStateException: 在不合法或不適當的時間調用方法
	
	
	主要區別:

		編譯時檢查: 受檢例外在編譯時檢查,非受檢例外不檢查。
		錯誤處理: 受檢例外強制錯誤處理,非受檢例外不強制。
		發生原因: 受檢例外通常是外部錯誤,非受檢例外通常是程式邏輯錯誤。
		繼承關係: 受檢例外直接繼承自Exception類（但不包括RuntimeException及其子類）,非受檢例外繼承自RuntimeException。
	 
	 
	 
	 

		
	 */
		 
		 
}
