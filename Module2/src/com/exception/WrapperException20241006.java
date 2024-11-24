package com.exception;

import java.sql.SQLException;

public class WrapperException20241006 extends Exception  {
	/*
	包覆例外（Wrapper Exception）:
	
		是一種用來包裹原始例外的設計模式，當應用程式發生例外時，可以將原始例外包在另一個例外中，以提供更多的上下文或更適合當前情境的訊息。
		這種技術在處理異常（exception handling）時相當常見，尤其是在多層次應用中，便於錯誤的追蹤與診斷。
	 
	
	
	包覆例外的主要目的：
	
		1.轉譯例外：將低層的例外訊息轉換成更高層的應用語境。例如，將數據庫相關的例外轉換成應用層可以理解的訊息。
		2.提供額外資訊：可以在包覆的例外中加入一些額外的錯誤訊息，方便除錯。
		3.保持原始例外資訊：即使包覆了例外，通常仍會保留原始例外的詳細資訊（例如使用getCause()方法來取得原始例外）。
		4.常見的包覆例外方式是將捕獲的例外重新丟出，但將原始例外作為新例外的原因，這樣仍然可以追蹤到最初的錯誤根源。
	 
	 何時使用包覆例外：
	 
		1.***當低層的錯誤訊息對高層使用者不太直觀時。
		2.想要在應用層提供更加語義化的錯誤資訊。
		3.想要在不同層次的模組之間隔離低層的技術細節。
	 
	 
	 
	 
	 什麼是低層錯誤訊息與高層錯誤訊息?
	 
	 	***跟DAO (Data Access Object) 設計模式有關，可以去看
	 	當我們開發多層次的應用程式時，應用程式通常會被分為不同的層級，例如資料存取層（Data Access Layer）、業務邏輯層（Business Logic Layer）和表示層（Presentation Layer）。
	 	在這些不同的層級中，各自可能會遇到不同類型的錯誤。
	 	運用包覆例外，可以讓低層的錯誤被包覆成高層次的錯誤，
	 
	 
	 
	 低層次錯誤（Low-level Errors）：

		1.這些錯誤通常來自於應用程式的基礎設施部分，例如資料庫、檔案系統或網路。
		2.這些錯誤往往非常具體，包含詳細的技術資訊，對高層次的業務邏輯或最終用戶來說，可能並不直觀或有用。
	 


	高層次錯誤（High-level Errors）：

		1.這些錯誤更貼近應用程式的業務邏輯，對於應用程式的其他部分或最終用戶來說，具有更明確的意義。
		2.例子包括 UserNotFoundException（用戶未找到）、InsufficientFundsException（資金不足）等。
		3.這些錯誤更容易理解，並且可以提供更具體的錯誤處理邏輯。
	 

	包覆例外的作用:
	
		1.可以將資料庫的具體錯誤轉換成業務相關的錯誤訊息，使得高層次的代碼更專注於業務邏輯。
		2.如果應用程式的某一層需要更換底層技術（例如從 MySQL 轉換到 PostgreSQL），包覆例外可以減少對其他層級的影響。其他層級只需處理高層次的例外，而不需要關心底層的變化。
	 */
}


//例子:
class DatabaseOperations {
    public void saveData(String data) throws DatabaseException {
        try {
            // 假設這裡有一些可能拋出 SQLException 的數據庫操作
            if (data == null) {
                throw new SQLException("Data cannot be null");
            }
            // 數據庫操作...
        } catch (SQLException e) {
            // 將 SQLException 包裝在自定義的 DatabaseException 中
            throw new DatabaseException("Error saving data to database", e);
        }
    }
}

class DatabaseException extends Exception {
	//定義了一個自定義的 DatabaseException 作為包覆例外
	
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
