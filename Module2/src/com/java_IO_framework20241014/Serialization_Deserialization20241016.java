package com.java_IO_framework20241014;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Person implements Serializable {
	/*
	永續性（Persistence）:
		1.將應用程式中的物件狀態保存到永久性儲存裝置（如資料庫）中，以便在應用程式重新啟動或跨不同執行個體時仍然能夠存取這些資料
		2.記憶體中的資料為非永續性資料:JVM關閉後,資料即不存在
		
	常見純文字型態永續性資料儲存:CSV(Comma-Separated Values)、XML(很少用了)(eXtensible Markup Language)、JSON(JavaScript Object Notation)
	
	 */
	
	//////////////////////////////////////////////////
	
	/*
	序列化 (Serialization):
	
		1.序列化是將 Java 物件轉換為位元流，方便傳輸或儲存。
		2.要進行序列化的物件必須實作 Serializable 介面，這是 Java 中的一個標記介面（marker interface），它沒有任何方法，但表明該物件可被序列化。
		3.使用 ObjectOutputStream 來將物件寫入檔案。
		
			ObjectOutputStream:
			
				1.封裝了一個基礎的 OutputStream
				2.將 Java 物件轉換為（byte stream），以便儲存到檔案、資料庫或透過網路傳輸。
				3.支援物件的深度複製（deep copy）。
				
				常用方法：
					1.writeObject(Object obj): 序列化並寫入物件到流中。
					2.flush(): 清除緩衝區並確保所有的資料都被寫出。
					3.close(): 關閉流，釋放資源。
		
	反序列化 (Deserialization):
		
		1.反序列化是將序列化後的位元流轉換回原始物件的過程，這使得物件的狀態可以被恢復。反序列化同樣需要類別實作 Serializable 介面。	
		2.使用 ObjectInputStream 來從檔案中讀取物件。
		
			ObjectInputStream:
			
				1.封裝了一個基礎的 InputStream
				2.從位元流中重建 Java 物件。
				3.復原儲存或傳輸的物件狀態。
				
					常用方法：
						readObject(): 從流中讀取並反序列化物件。
						close(): 關閉流，釋放資源.
		
		
	序列化中的注意事項：
	
		1.serialVersionUID：每個序列化的類別都建議定義一個 serialVersionUID 來確保序列化與反序列化過程中的版本一致性。
		2.transient 關鍵字：標記 transient 的變數不會被序列化，適用於敏感資料或不需要保存的狀態。
		3.靜態變數：靜態變數（static）不會被序列化，因為它們屬於類別本身而非物件的狀態。	
	 */
	
	
	
	
	
	// 定義要序列化的類別與反序列化的類別
    private static final long serialVersionUID = 1L; // 避免反序列化版本不匹配
    String name;
    int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}


public class Serialization_Deserialization20241016 {

	
	////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		Person person = new Person("Alice", 25);

        try (FileOutputStream fileOut = new FileOutputStream("person.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(person);  // 將 person 物件序列化並寫入檔案
            System.out.println("物件已序列化");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
