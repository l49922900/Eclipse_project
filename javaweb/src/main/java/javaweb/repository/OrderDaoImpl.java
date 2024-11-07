package javaweb.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javaweb.model.entity.Order;

public class OrderDaoImpl extends BaseDao implements OrderDao {

	@Override
	public List<Order> findAllOrders(Integer userId, String orderStatus) {
		//findAllOrders 方法的目的是根據 userId 和 orderStatus 從資料庫中查找訂單
		
		
		List<Order> orders = new ArrayList<>();
		String sql = """
						select order_id, user_id, order_date, product_id, 
						   	   quantity, unit_price, subtotal, order_status
						from orders
						where user_id = ? and order_status = ?
					 """.trim(); 
/*
""" """（多行字串 / text blocks）：

從 Java 13 開始引入，用三重雙引號（"""）來建立一個字串區塊。這種語法允許字串中直接換行，
非常適合撰寫多行的 SQL 語句或 JSON 格式。
在多行字串中，換行和縮排都會被保留，方便保持代碼整齊，並增加可讀性。
 */
		
/*
.trim()：

trim() 是一個字串方法，用於移除字串開頭和結尾的空白字符，
包括換行、空格或 tab 符號。
因為多行字串可能在開頭和結尾包含額外的換行或縮排，
所以 trim() 可以確保最後的字串沒有多餘的空白字符，避免 SQL 語句執行錯誤。
 */
		
		
		
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, userId);
			pstmt.setString(2, orderStatus);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("order_id"));
					order.setUserId(rs.getInt("user_id"));
					order.setOrderDate(rs.getString("order_date"));
					order.setProductId(rs.getInt("product_id"));
					order.setQuantity(rs.getInt("quantity"));
					order.setUnitPrice(rs.getDouble("unit_price"));
					order.setSubtotal(rs.getInt("subtotal"));
					order.setOrderStatus(rs.getString("order_status"));
//使用 while 迴圈遍歷 ResultSet 中的每一筆資料，並將每一筆資料轉換成 Order 物件後加入到 orders 集合。
					orders.add(order);
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orders;
	}

	@Override
	public void batchAddOrders(List<Order> orders) {
		//用來將多筆訂單以批次（batch）方式新增
		
		String sql = """
						insert into 
						orders(user_id, order_date, product_id, quantity, unit_price, subtotal, order_status) 
						values(?, ?, ?, ?, ?, ?, ?)
					 """.trim();
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.clearBatch(); // 清除批次(重要 !!!)
			
			for(Order order : orders) {
				pstmt.setInt(1, order.getUserId());
				pstmt.setString(2, order.getOrderDate());
				pstmt.setInt(3, order.getProductId());
				pstmt.setInt(4, order.getQuantity());
				pstmt.setDouble(5, order.getUnitPrice());
				pstmt.setInt(6, order.getSubtotal());
				pstmt.setString(7, order.getOrderStatus());
				
				pstmt.addBatch(); // 加入批次
			//使用 for 迴圈設定每個 Order 物件的欄位值並加入批次執行清單。
			}
			
			pstmt.executeBatch(); // 執行批次(一次整批傳送給 MySQL)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void batchUpdateOrderStatus(List<Integer> orderIds, String orderStatus) {
		//批次更新指定訂單的狀態。
		
		String sql = """
						update orders
						set order_status = ?
						where order_id = ?
				""".trim();
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.clearBatch(); // 清除批次(重要 !!!)
			
			for(Integer orderId : orderIds) {
				pstmt.setString(1, orderStatus);
				pstmt.setInt(2, orderId);
				
				pstmt.addBatch(); // 加入批次
			}
			
			pstmt.executeBatch(); // 執行批次(一次整批傳送給 MySQL)
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}