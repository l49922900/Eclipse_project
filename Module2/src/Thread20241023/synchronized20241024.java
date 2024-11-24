package Thread20241023;

public class synchronized20241024 {
	private int count = 0;
	/*
	synchronized:
	
	 	是用來實現多執行緒安全的一個機制。
	 	當我們在多執行緒環境下操作共享資源時，可能會發生多個執行緒同時修改同一個資源，導致資料不一致的問題。
	 	透過 synchronized，可以確保同一時間只有一個執行緒可以進入某個區塊或方法，來避免競爭條件。
	 	不僅可以鎖定某個物件，還可以鎖定類別層級的屬性或方法
	 	
	 	
	 鎖的範圍:
	 
		1.物件鎖（實例鎖）：
		
			當你對非靜態方法使用 synchronized 時，
			不同的物件實例之間的鎖是互不影響的。
			也就是說，如果有兩個不同的物件實例，兩個執行緒可以同時訪問它們各自的同步方法，
			因為它們分別持有各自物件的鎖。
			
			
		2.類別鎖：
		
			當你對靜態方法使用 synchronized，
			不同的物件實例的同一個靜態方法會共享一個鎖，
			即使你有多個這個類的實例，當其中一個執行緒進入靜態同步方法時，
			其他執行緒無論是通過哪個實例調用這個靜態同步方法，都必須等到鎖被釋放後才能進入。
	 
	 注意事項:
	 
		1.每個物件都有一個隱含的鎖。(請見鎖的範圍那邊)
		2.當執行緒進入 synchronized 方法或區塊時，會自動獲得鎖，並在方法或區塊結束時釋放鎖。
		3.如果有其他執行緒嘗試進入已被鎖住的區塊，必須等待鎖被釋放後才能繼續。	
	 	
	 
	 
	 死結Death Lock:兩個或多個threads,各自在等待對方擁有的鎖
	 ////////////////////////////////////////////////////////////
	 	
	 	
	 synchronized 機制的兩種用法:
	 
		1.同步方法 (synchronized method)
		2.同步區塊 (synchronized block)
		
	同步方法:
	
		當一個方法被標記為 synchronized，Java 會保證同一時間內只有一個執行緒可以執行這個方法，
		其他執行緒需要等到鎖被釋放後才可以進入。
	 */
	//例子:
	 public synchronized void increment() {
	        count++;
	    }
	 
	 /*
	 同步區塊:
	 
		如果只想同步某部分程式碼，而不是整個方法，那麼可以使用同步區塊。
		這樣可以只鎖住關鍵部分的程式碼，增加程式的執行效率。
		
	同步區塊的語法:
	
		鎖對象可以是任意一個物件，但通常會使用this（指向當前實例）或某個特定的對象作為鎖，
		來確保同一時間只有一個執行緒可以進入這個同步區塊。
	  
	  
	  
	  
	  方法一自創物件來鎖住物件:
	  
	  	我們使用了一個 lock 對象來作為鎖。只有在 synchronized 區塊內的程式碼會被鎖住
	  
	  	
	  synchronized (this)與自創一個物件來作為鎖的不同:
	  
		  	***synchronized (this) 鎖定當前物件，會影響所有使用該物件鎖的同步區塊或方法，
		  	靈活性較低。自創物件作鎖則讓同步控制更細緻，不會與 this 產生鎖競爭，且更安全，適合複雜場景。
		  	在實務上，自創物件作為鎖的方式更常見
	  */
	 
	 private final Object lock = new Object();  // 鎖對象

	    public void increment2() {
	        // 只鎖住這段關鍵程式碼
	        synchronized (lock) {
	            count++;
	        }
	    }
	    
	    
	    /*
	    方法二使用 this 作為鎖對象:
	    
	    	synchronized (this) 是針對當前物件（this）來做鎖定，
	    	這意味著同一個物件上的其他 synchronized (this) 也會受到影響。
	    	換句話說，如果有多個線程嘗試同時呼叫該物件的同步方法或使用 synchronized (this)，
	    	他們會互相排隊，因為他們使用的是同一個鎖（就是 this）。
	    
	    
	    
	    synchronized (this)與自創一個物件來作為鎖的不同:
	  
		  	***synchronized (this) 鎖定當前物件，會影響所有使用該物件鎖的同步區塊或方法，
		  	靈活性較低。自創物件作鎖則讓同步控制更細緻，不會與 this 產生鎖競爭，且更安全，適合複雜場景。
		  	在實務上，自創物件作為鎖的方式更常見
	     */
	    
	    public void performTask() {
	        synchronized (this) {
	            // 執行一些需要同步的操作
	        }
	    }
	    
	    
	    /*
	    方法三: 使用靜態鎖對象
	    
	    	在這個例子中，globalLock 是靜態的，所有這個類的實例都會共享這個鎖。
	    	當某一個執行緒獲得了這個鎖時，其他執行緒無法同時進入這個同步區塊，直到鎖被釋放。

	     */
	    
	    private static final Object globalLock = new Object();

	    public void performTask2() {
	        synchronized (globalLock) {
	            // 鎖住的是 globalLock，不是 this
	        }
	    }

	    
	 
	 

}
