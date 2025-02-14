package com.example.demo.mapper;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/*
除了信件中提到的問題外，我還發現既有的程式碼存在其他問題:

1.關於存取控制問題：
	目前的 Account 類別有 public constructor，這違反了只能透過 getInstance 存取的需求
	需要將 constructor 改為 private
	cacheMap 也應該改為 private，避免外部直接存取
	
	
2.目前程式碼在多執行緒下會有以下問題：

	檢查 cache 和放入 cache 之間有競速條件(race condition)
	可能導致同一個 key 重複初始化多次
	init() 方法耗時較長(10秒)，如果用同步鎖會嚴重影響效能
	

3.以下是我的修改思路

	實現執行緒安全：

		1.使用 ConcurrentHashMap 代替 HashMap，他提供了執行緒安全的操作
		2.使用 computeIfAbsent，能要麼全部執行成功、要麼全部不執行地完成「檢查並新增」的操作
		3.避免了傳統 double-checked locking 的複雜性，同時保證了執行緒安全


	實現「最近最少使用 (LRU, Least Recently Used)」快取：

		1.LRU（Least Recently Used）：當快取達到容量上限時，應該移除那些最久未被使用的資料，以釋放空間給新的資料
		2.使用 LinkedHashMap 搭配 accessOrder=true 來追蹤訪問順序
		3.分離儲存層(cacheMap)和訪問順序追蹤(accessOrder)的職責
		4.使用獨立的 lock 物件來同步 LRU 相關操作，只有在更新訪問順序和清理快取時才需要同步，且不會影響到主要的資料存取操作

	封裝性保護：

		1.constructor 設為 private，確保只能通過 getInstance 取得實例
		3.所有的快取相關變數都是 private static final
		3.內部的 evictIfNecessary 方法也是 private

	效能考量：

		1.讀取操作不需要完整的同步，大部分時候直接從 ConcurrentHashMap 獲取
		2.只有在更新 LRU 順序時才需要短暫的同步
		3.清理舊資料的操作也在同步塊中，但因為有數量限制，執行時間可控
 */





public class Account {
    private static final int CACHE_LIMIT = 50;
    //設定快取的最大容量為 50，當快取的數量超過此值時，最舊的資料會被移除。
    
    private static final ConcurrentHashMap<String, Account> cacheMap = new ConcurrentHashMap<>();
    /*
    ConcurrentHashMap:一種線程安全的 Map ，多個線程可以同時讀寫 ConcurrentHashMap而不需要顯式同步。他使用了分段鎖保證線程安全，避免了整個 Map 被鎖住的情況
     */
    private static final LinkedHashMap<String, String> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
    /*
     LRU（Least Recently Used）：當快取達到容量上限時，移除那些最久未被使用的資料，以釋放空間給新的資料
     
     accessOrder:使用 LinkedHashMap 來記錄每個 Account 的訪問順序，這裡透過LinkedHashMap 來實現 LRU 快取，true 表示使用LRU來確保最新訪問的資料會移動到最後一位
     
     LinkedHashMap:HashMap 的一種變體，具有雙鏈表的特點，可以保留插入順序或訪問順序
     	第一個參數16 initialCapacity：初始容量
		第二個參數0.75f  loadFactor：負載因子（決定何時擴容）， 0.75f 是 HashMap 的預設值
		第三個參數true  accessOrder：設為 true，會根據最近的訪問順序來排序鍵值對，這樣可以實現 LRU (Least Recently Used) 
     
     。
     */
    
    
    private static final Object lock = new Object();
    //定義一個鎖用來保證在多執行緒環境下對 LRU 訪問順序表的操作安全。

    private String key;
    private String cachedData;

    private Account(String key) {
        this.key = key;
    }

    public static Account getInstance(String key) {
    	//根據 key 從快取中獲取或創建一個 Account 物件。
    	
        Account account = cacheMap.computeIfAbsent(key, k -> {
       /*
       computeIfAbsent:查詢 Map 中的某個 key，當 key 不存在時初始化一個預設值。這個方法是執行緒安全的。
        */
        	     	
            Account newAccount = new Account(k);
            newAccount.init();
       //如果該 key 不存在於 cacheMap 中，則會創建一個新的 Account 物件並進行初始化。
            
            
            synchronized (lock) {
      /*
      使用同步塊來保證訪問順序更新和驅逐操作的執行緒安全。具體操作是將新帳戶的 key 加入 accessOrder，並檢查是否需要移除最舊的快取資料。      	
       */
            	
                accessOrder.put(k, k); 
      /*
      這裡更新訪問順序，關於這裡為什麼是用兩個k:
      	
      	LinkedHashMap 是繼承自 HashMap ，而 HashMap 需要存放成對的鍵和值。因此即使只需要key，仍然必須提供一個與之對應的值。即：
			key(k) :是要追蹤訪問順序的帳戶 key。
			value(k):也是相同的 key，因為不需要儲存額外的資料作為值。
			
	  為何不只用一個k:
		
		如果想只儲存鍵，可以使用 Set 來儲存鍵的集合，但這裡需要訪問順序，LinkedHashMap 能提供這種順序管理，而 Set 並沒有這個能力。
		因此必須使用 LinkedHashMap，他強制要求鍵-值對
       */
                
                evictIfNecessary();             
      //當達到快取上限（CACHE_LIMIT）時，使用 evictIfNecessary() 來移除最久未使用的資料
            }
            return newAccount;
        });

       
        
        synchronized (lock) {
       //確保當帳戶存在於快取中時，更新訪問順序，使其移到最近訪問的位置（根據 LRU 原則）。 	
            if (accessOrder.containsKey(key)) {
                accessOrder.put(key, key);
            }
        }
        return account;
    }

    private static void evictIfNecessary() {
    //在快取超過限制時執行驅逐操作。	
   
        if (accessOrder.size() > CACHE_LIMIT) {
            String oldestKey = accessOrder.keySet().iterator().next(); 
    //檢查 accessOrder 中的資料數是否超過了快取限制，並使用迭代器來獲取最舊訪問的 key。         
            
            accessOrder.remove(oldestKey);
            cacheMap.remove(oldestKey); 
   // accessOrder 和 cacheMap 中移除最舊的 Account 資料，騰出空間給新的資料。
        }
    }

    private void init() {
        // 模擬初始化資料
    }
}
