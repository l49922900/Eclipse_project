package com.java_IO_framework20241014;

public class File20241016 {
	/*
	File 類別:
	
		1.是操作檔案和目錄的基礎類別。
		2.它代表一個檔案或目錄的路徑名，提供了一系列方法來檢查檔案是否存在、取得檔案的資訊、創建、刪除檔案或目錄等等。
		
	重點：

		抽象路徑名： File 物件代表的是一個抽象的路徑名(也就是檔案的路徑)，而非實際的檔案系統物件。
		跨平台性： File 類別提供了跨平台的檔案操作，不依賴於特定的作業系統。	
		
		
	建構子:
	
		File(String pathname)
		File(String parent, String child)
		File(File parent, String child)
		
		pathname: 檔案或目錄的路徑名。
		parent: 父目錄。
		child: 子檔案或子目錄的名稱。
		
	常見方法:	
	
		檢查檔案或目錄狀態
		
			exists(): 判斷檔案或目錄是否存在。 返回值： boolean (存在返回 true，不存在返回 false)
			isFile(): 判斷是否為檔案。 返回值： boolean
			isDirectory(): 判斷是否為目錄。 返回值： boolean
			canRead(): 判斷是否可讀。 返回值： boolean
			canWrite(): 判斷是否可寫。 返回值： boolean
			isAbsolute(): 判斷路徑是否為絕對路徑。 返回值： boolean
			isHidden(): 判斷是否為隱藏檔案。 返回值： boolean
		取得資訊
		
			getName(): 取得檔案或目錄的名稱。 返回值： String
			getPath(): 取得檔案或目錄的路徑。 返回值： String
			getAbsolutePath(): 取得檔案或目錄的絕對路徑。 返回值： String
			getParent(): 取得父目錄。 返回值： String (如果沒有父目錄，則返回 null)
			length(): 取得檔案的大小 (以位元組為單位)。 返回值： long
			lastModified(): 取得最後修改時間。 返回值： long (表示自 1970 年 1 月 1 日 00:00:00 GMT 以來的毫秒數)
		
		操作檔案或目錄
		
			createNewFile(): 創建一個新的檔案。 返回值： boolean (創建成功返回 true，否則返回 false)
			mkdir(): 創建一個目錄。 返回值： boolean (創建成功返回 true，否則返回 false)
			mkdirs(): 創建多層目錄。 返回值： boolean (創建成功返回 true，否則返回 false)
			delete(): 刪除檔案或目錄。 返回值： boolean (刪除成功返回 true，否則返回 false)
			renameTo(File dest): 將檔案或目錄重命名或移動到新的位置。 返回值： boolean (重命名成功返回 true，否則返回 false)
		
		列出目錄內容
		
			list(): 返回一個字串陣列，包含該目錄下的所有檔案和目錄名稱。 返回值： String[] (如果目錄下沒有檔案或目錄，則返回 null)
			listFiles(): 返回一個 File 陣列，包含該目錄下的所有檔案和目錄。 返回值： File[] (如果目錄下沒有檔案或目錄，則返回 null)
			其他
			toURI(): 將 File 物件轉換為 URI。 返回值： URI	
	 */
	
	
}
