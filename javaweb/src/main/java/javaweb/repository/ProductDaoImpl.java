/*
DaoImpl (資料訪問物件實現類):

	用途：DaoImpl繼承BaseDao，並實作DAO中的方法，使用BaseDao中的資料庫連線(conn)來進行資料庫操作。
	協作：DaoImpl的findAll和add方法被Service呼叫，用來從資料庫中查詢所有用戶資料或新增用戶資料。
		  BaseDao提供的共享連線conn使資料庫訪問更加高效。
 */



package javaweb.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javaweb.model.entity.Product;

public class ProductDaoImpl extends BaseDao implements ProductDao {

	@Override
	public List<Product> findAllProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "select product_id, product_name, price, stock_quantity from product";
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
			// 創建PreparedStatement連線物件
			ResultSet rs = pstmt.executeQuery()) {
			// 執行Query語句，並把結果放入rs
			
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getInt("product_id"));
				product.setProductName(rs.getString("product_name"));
				product.setPrice(rs.getDouble("price"));
				product.setStockQuantity(rs.getInt("stock_quantity"));
				// 注入到 products 集合
				products.add(product);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return products; //// 回傳有 user 物件的集合
	}

	@Override
	public Map<String, Double> querySalesRanking() {
		String sql = """
				SELECT p.product_name, SUM(o.subtotal) AS total_sales
				FROM orders o
				LEFT JOIN product p ON o.product_id = p.product_id
				GROUP BY p.product_name
				ORDER BY total_sales DESC
				""".trim();
		// 存放銷售排行 map
		Map<String, Double> map = new LinkedHashMap<>();
		
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
			
			while (rs.next()) {
				
				String key = rs.getString("product_name");
				Double value = rs.getDouble("total_sales");
				// 將排行放到 map 集合中
				map.put(key, value);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

}