package com.static_block20241001;

public class StaticBlock20241001 {
	/*
	 * 靜態塊（static block）
			靜態塊 是 Java 中的一種特殊塊，用於在類加載時執行初始化操作。
			當 JVM（Java虛擬機）第一次載入這個類時，靜態塊中的代碼就會自動執行，並且只執行一次。
	 */
	
	
	/*
	 * 靜態塊的作用
			初始化靜態變量： 靜態塊常被用來初始化類的靜態變量，確保這些變量在類被使用之前就已經有了正確的值。
			執行一次性的初始化操作： 除了初始化變量，靜態塊還可以執行一些其他的初始化操作，比如建立連接、載入配置文件等。這些操作只需要在類加載時執行一次。
			異常處理： 在 static block 中，可以捕捉和處理初始化過程中可能出現的異常，確保類別在載入時能夠正確處理錯誤情況，避免程式在運行時因為初始化失敗而崩潰。
	 */
	
	
	/*
	 * 靜態塊的特性
			執行順序： 靜態塊的執行順序是在類加載時，按照它們在類中出現的順序依次執行。
			只執行一次： 無論這個類被實例化多少次，靜態塊只會執行一次。
			不能拋出已檢查異常： 靜態塊不能拋出已檢查異常，如果拋出，會導致 ExceptionInInitializerError。但可以拋出未檢查異常
	 */
	
	static String dbURL;
    static String username;
    static String password;
	
	// 靜態區塊
    static {
        System.out.println("靜態區塊執行...");
        dbURL = "jdbc:mysql://localhost:3306/mydb";
        username = "admin";
        password = "password123";
    }

    public static void displayConfig() {
        System.out.println("Database URL: " + dbURL);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    public static void main(String[] args) {
        // 無需創建物件，直接呼叫靜態方法
    	StaticBlock20241001.displayConfig();
    }

}
