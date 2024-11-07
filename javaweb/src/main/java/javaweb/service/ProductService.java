/*
Service (服務層):

	用途：Service負責entity相關的業務邏輯處理，並調用DaoImpl來執行具體的資料庫操作。
	協作：
		findAll()方法：調用DaoImpl的findAll()方法來查詢所有用戶，
		並將每個User物件轉換為Dto物件，最後返回Dto列表給控制器。
		
		append()方法：此方法接收用戶資料，然後創建entity物件並設置相關屬性，最後透過DaoImpl的add方法將新用戶資料存入資料庫。
		
 */


package javaweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javaweb.model.dto.ProductDto;
import javaweb.model.entity.Product;
import javaweb.repository.ProductDao;
import javaweb.repository.ProductDaoImpl;

public class ProductService {
	private ProductDao productDao = new ProductDaoImpl();
	
	public List<ProductDto> findAllProducts() {
		List<Product> products = productDao.findAllProducts();
		List<ProductDto> productDtos = new ArrayList<>();
		
		products.stream().forEach((p) -> {
			productDtos.add(new ProductDto(p.getProductId(), p.getProductName(), p.getPrice(), p.getStockQuantity()));
	/*
	利用 Java Stream API 將每個 Product 實體物件（包含 productId、productName、price、stockQuantity）轉換成 ProductDto 物件。
	 */
		});
		
		return productDtos;
	}
	
	public Map<String, Double> querySalesRanking() {
		return productDao.querySalesRanking();
	}
}
