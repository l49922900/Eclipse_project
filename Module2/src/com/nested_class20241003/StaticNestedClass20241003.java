package com.nested_class20241003;

class User {
	
	/*
	 * 靜態嵌套(巢狀)類別（Static Nested Class）:
	 * 		是在外部類別內部使用 static 修飾符定義的類別。
	 * 		它與外部類別的實例無關，可以直接通過外部類別訪問。
	 
	 特點：
		(1)靜態嵌套類別與外部類別的實例無關，不需要外部類別的實例即可使用(因為他是靜態的)
		
		(2)***不能直接訪問外部類別的非靜態(實例)成員。必須通過外部類別的「實例」來存取這些成員
			因為靜態嵌套類別在設計上與外部類別的實例無關。
			它們在內存中獨立存在，不依賴於外部類別的實例。
			因此，靜態嵌套類別無法自動訪問外部類別的非靜態成員，因為這些成員屬於外部類別的具體實例。
			直接訪問的意思:指的是在不依賴於外部類別實例的情況下，但這是不行的
			
		(3)可以有自己的靜態和非靜態成員。
		(4)主要用於輔助外部類別的功能。
		
	為什麼使用嵌套類別？
		
		封裝性更強： 將耦合度高的輔助類別封裝在類別中，提高了程式的封裝性和模組化。
		邏輯分組： 當一個類別僅在另一個類別內部使用時，使用嵌套類別可以表達這種關係。
		訪問控制： 嵌套類別可以根據需要設置訪問修飾符，控制其可見性。
	 
	 
	 注意事項:
		
		靜態嵌套類別與內部類別的主要區別在於是否與外部類別的實例相關聯。
		靜態嵌套類別不需要外部類別的實例即可使用，而內部類別則需要。
	 */	
	
	
	//實例:實現建造者模式（Builder Pattern）
	
	
    private String firstName;
    private String lastName;
    private int age;
    private String email;

    // 私有構造方法，只能通過Builder來創建User實例
    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName  = builder.lastName;
        this.age       = builder.age;
        this.email     = builder.email;
    }

    // 靜態嵌套類別
    public static class Builder {
    	/*
    	 * Builder 類別的目的是構建外部類別（如 User）的實例。這意味著在 Builder 類別中，我們需要收集所有必要的屬性，然後創建外部類別的實例。
    	 * 若 Builder 是一個內部類別（非靜態），那麼它將依賴於一個已存在的外部類別實例，這在構建新物件時是不合理的，因為新物件尚未存在。
    	   Builder被定義為static，這意味著它不依賴於User的實例，可以直接通過User.Builder來創建。
    	 */
    	
    	
    	
        private String firstName;
        private String lastName;
        private int age;
        private String email;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
            //每個設置方法（如firstName、lastName等）都返回Builder本身，允許鏈式調用。
            //鏈式調用:new User.Builder().firstName("John").lastName("Doe").build();
            //方法鏈（Method Chaining）允許多個方法在同一物件上連續調用。這通常通過每個方法返回調用物件本身（this）來實現，使得多個方法調用可以在一條語句中串聯起來。
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
            //Builder類負責收集所有必要的屬性，並在build()方法中創建一個新的User物件。
        }
    }

    @Override
    public String toString() {
        return "User{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", age=" + age +
               ", email='" + email + '\'' +
               '}';
    }
}

public class StaticNestedClass20241003 {
	
	public static void main(String[] args) {
        User user = new User.Builder()
                        .firstName("John")
                        .lastName("Doe")
                        .age(30)
                        .email("john.doe@example.com")
                        .build();
        
        
        
        System.out.println(user);
    }
	/*
	 * 使用建造者模式和靜態嵌套類別的好處:
	 * 
	 * 		當一個類有多個屬性時，使用構造方法來初始化物件可能會導致以下問題：
	 * 
	 * 			(1)可讀性差：例如，new User("John", "Doe", 30, "john.doe@example.com")，很難直觀地知道每個參數代表什麼。
				(2)可選參數困難：如果某些屬性是可選的，可能需要多個構造方法來處理不同的參數組合，這會增加代碼的複雜性。
				(3)不可變性：如果不設計為不可變物件，物件的屬性可能會被隨意修改。
	 */
}
