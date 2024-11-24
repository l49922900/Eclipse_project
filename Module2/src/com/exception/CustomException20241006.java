package com.exception;

import java.io.IOException;

public class CustomException20241006 extends Exception {
	/*
	自定義例外:建立特定於您的應用程序邏輯的例外類型，
	
	步驟:
	
		創建自定義例外類：
			(1)通常，我們會創建一個繼承自 Exception 或 RuntimeException 的新類。
	 
	 	定義構造函數：

			(1)至少提供帶有錯誤訊息和帶有錯誤訊息及根本原因的構造函數，這有助於更靈活地處理例外。
	 */
	
	
	//例子:
	// 無參數構造函數
    public CustomException20241006() {  
        super();
        //使用 super() 可以確保父類(Exception 類)的構造函數被正確調用(使我們可以重用父類中已經實現的功能)，初始化從父類繼承的屬性。

    
    }

    // 帶有錯誤信息的構造函數
    public CustomException20241006(String message) {
        super(message);
        //Exception 類有一個接受字符串參數的構造函數，用於設置錯誤信息。
        //使用 super(message) 可以將這個錯誤信息傳遞給父類，確保它被正確設置。
    }

    // 帶有錯誤信息和原因的構造函數
    public CustomException20241006(String message, Throwable cause) {
        super(message, cause);
    }

    // 帶有原因的構造函數
    public CustomException20241006(Throwable cause) {
        super(cause);
    }
}

// 2. 使用自定義例外的示例類
class ExampleClass {
    public void doSomething(int value) throws CustomException20241006 {
    	//這是方法聲明的一部分，表明該方法可能拋出 CustomException 類型的例外。
    	//它不實際拋出例外，而是聲明這個方法可能會拋出這種類型的例外。
    	
    	
    	
        if (value < 0) {
            throw new CustomException20241006("值不能為負數");
            //這是實際拋出（創建並拋出）一個例外的語句。
        }
        // 其他邏輯...
    }

    public static void main(String[] args) {
        ExampleClass example = new ExampleClass();
        try {
            example.doSomething(-5);
        } catch (CustomException20241006 e) {
            System.out.println("捕獲到自定義例外：" + e.getMessage());
            //e.getMessage() 能夠返回 "數據庫連接失敗"，因為我們將此信息傳遞給了父類。
        }
    }

///////////////////////////////////////////////////////////////////////
}



class Parent{
	/*
	 如果父類別的方法沒有「聲明」拋出「特定」的受檢例外，子類別在覆寫這個方法時，也不能聲明拋出這個特定的受檢例外。
	 這是為了確保繼承結構中的一致性，防止子類別覆寫後引入新的例外處理義務，影響父類別的方法調用者。
	 
	 如果子類別的方法能夠拋出父類別未聲明的受檢例外，會導致以下問題：
		1.編譯器無法保證例外被處理：因為呼叫者基於父類別的方法簽名，並不知道子類別可能拋出的新例外，這可能導致例外未被適當處理。
		2.破壞合約（Contract）：父類別的方法簽名就像是一個合約，保證了某些行為和例外處理。子類別不應該打破這個合約，否則會導致使用父類別的代碼在遇到子類別實例時出現不可預期的行為。
	 
	 ***注意上面是說「聲明」拋出，所以這個規則適用於 throws，而不是 throw
	 */
	
	
	//例子
	public void someMethod() throws IOException {
        // 父類別可能會拋出 IOException
    }	
}

class Child extends Parent {
    @Override
    public void someMethod() throws IOException {
        // 子類別可以拋出與父類別相同的 IOException
    }
}

