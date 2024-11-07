package javaweb.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javaweb.model.dto.OrderDto;
import javaweb.model.entity.Order;
import javaweb.model.entity.Product;
import javaweb.repository.OrderDao;
import javaweb.repository.OrderDaoImpl;
import javaweb.repository.ProductDao;
import javaweb.repository.ProductDaoImpl;

public class OrderService {
	
	private OrderDao orderDao = new OrderDaoImpl();
	private ProductDao productDao = new ProductDaoImpl();
//分別代表訂單和商品的資料存取物件。這兩個物件用於執行資料庫查詢和儲存訂單或商品的資訊。
	
	
	// 同時新增多筆訂單
	// userId: 使用者 id
	// productIds: 每一件商品的 id   [1, 2, 3, 4, 5]
	// unitPrices: 每一件商品的單價    [10, 20, 30, 40, 50]
	// amounts:    每一件商品的購買數量 [5, 0, 3, 0, 2]
    //                        i:  0  1  2  3  4
	// 注意: productIds 的長度必須等於 amounts 的長度
	
	
	public void batchAddOrders(Integer userId, String[] productIds, String[] unitPrices, String[] amounts) {
		//用來批次新增多筆訂單
		
		
		
		List<Order> orders = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//使用 SimpleDateFormat 格式化當前日期作為訂單日期
		
		
		for(int i=0;i<productIds.length;i++) {
	/*
	藉由 for 迴圈遍歷陣列中的每一筆資料，
	確保可以處理任意長度的訂單列表（前提是三個陣列的長度相等），
	並將其存入訂單物件內。
	 */
			
			Integer productId = Integer.parseInt(productIds[i]);
			Double unitPrice = Double.parseDouble(unitPrices[i]);
			Integer amount = Integer.parseInt(amounts[i]);
//這段迴圈的目的是將 productIds、unitPrices 和 amounts 陣列中的字串數值分別轉換為 Integer 和 Double 型態	
			
			
	//若 amount 為 0 或小於 0 則跳過該筆訂單。
			if(amount <= 0) {
				continue;
			}
			
			Order order = new Order();
			order.setUserId(userId);
			order.setOrderDate(sdf.format(new Date()));
			order.setProductId(productId);
			order.setQuantity(amount);
			order.setUnitPrice(unitPrice); // ?
			order.setSubtotal((int)(order.getQuantity() * order.getUnitPrice()));
			order.setOrderStatus("Pending");
			
			orders.add(order);
		}
		
		orderDao.batchAddOrders(orders);
	}
	
	// 查找該使用者的所有訂單，根據訂單狀態進行篩選
	public List<OrderDto> findAllOrders(Integer userId, String orderStatus) {
		// 取得訂單資料
		List<Order> orders = orderDao.findAllOrders(userId, orderStatus);
//		// 取得所有商品
		List<Product> products = productDao.findAllProducts();
//		
//		// 將 List<Order> 轉 List<OrderDto>
		List<OrderDto> orderDtos = new ArrayList<>();
		for(Order order : orders) {
			OrderDto orderDto = new OrderDto();
			orderDto.setOrderId(order.getOrderId());
			orderDto.setUserId(order.getUserId());
			orderDto.setOrderDate(order.getOrderDate());
			orderDto.setProductId(order.getProductId());
			orderDto.setQuantity(order.getQuantity());
			orderDto.setUnitPrice(order.getUnitPrice());
			orderDto.setSubtotal(order.getSubtotal());
			orderDto.setOrderStatus(order.getOrderStatus());
			
			
			// 透過 productId 找到對應的 productName
			Optional<Product> optProduct = products.stream()
			/*
			這行程式碼透過 products 清單（List<Product>）建立資料流（Stream），並將查詢結果儲存在一個 Optional<Product> 物件中。
			Optional 是 Java 8 引入的資料型態，用於處理可能為空的物件，以避免空指標例外（NullPointerException）。		
			 */
					
					.filter(p -> p.getProductId().equals(orderDto.getProductId()))
					.findFirst();
			/*
			使用 filter 方法來篩選出符合條件的商品。
			filter 的參數是一個 Lambda 表達式 p -> p.getProductId().equals(orderDto.getProductId())，這裡的 p 是 Product 物件的簡寫。
			p.getProductId()：從 Product 物件 p 中取得 productId。
			orderDto.getProductId()：取得當前 OrderDto 物件的 productId。
			.equals(...)：比較 Product 的 productId 是否等於 OrderDto 的 productId，若相等則表示找到相對應的商品。
			若找到相符的 Product，則該商品會被保留於 Stream 中；否則，該商品會被過濾掉。
			 */
			
			if(optProduct.isPresent()) { 
		//isPresent() 是 Optional 類別中的方法，用於檢查 Optional 物件是否包含值。如果 optProduct 有值，即表示找到相符的商品
				orderDto.setProductName(
						optProduct.get().getProductName()
			/*
			optProduct.get()：取得 Optional 中包含的 Product 物件
			optProduct.get().getProductName()：從取得的 Product 物件中提取 productName。
			orderDto.setProductName(...)：將提取出的 productName 設定到當前的 OrderDto 物件中，以便訂單資訊中包含商品名稱。
			 */
				);
			}
//			
			// 注入到 orderDtos 集合
			orderDtos.add(orderDto);
		}
		
		return orderDtos;
	}
	
	/** 更新訂單狀態
	 * 
	 * @param userId 例如: 1
	 * @param fromOrderStatus 例如: Pending
	 * @param toOrderStatus   例如: Finished 或 Cancel
	 */
	public void updateOrderStatus(Integer userId, String fromOrderStatus, String toOrderStatus) {
		List<Order> orders = orderDao.findAllOrders(userId, fromOrderStatus);
		// 收集 orders 中所有的 orderId
		List<Integer> orderIds = orders.stream().map(o -> o.getOrderId()).collect(Collectors.toList());
		//List<Integer> orderIds = orders.stream().mapToInt(o -> o.getOrderId()).boxed().collect(Collectors.toList());
		// 進行批次修改
		orderDao.batchUpdateOrderStatus(orderIds, toOrderStatus);
	}
	
}





