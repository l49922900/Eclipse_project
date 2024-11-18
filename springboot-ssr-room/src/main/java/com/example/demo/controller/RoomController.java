package com.example.demo.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.exception.RoomAlreadyExistsException;
import com.example.demo.exception.RoomException;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.service.RoomService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/**
 * Method URI            功能
 * --------------------------------------------------------------------
 * GET    /rooms                查詢所有會議室(多筆)
 * GET    /room/{roomId}        查詢指定會議室(單筆)
 * POST   /room                 新增會議室
 * POST   /room/update/{roomId} 完整修改會議室(同時修改 roomName 與 roomSize)
 * GET    /room/delete/{roomId} 刪除會議室
 * --------------------------------------------------------------------
 * */

@Controller
@RequestMapping(value = {"/room", "/rooms"})
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping
	public String getRooms(Model model, @ModelAttribute RoomDto roomDto) {
	//定義一個 GET 請求方法，當訪問 /rooms 時會被觸發。它從 roomService 獲取所有會議室資料並傳遞到視圖 room/room。	
	/*
	@ModelAttribute:
	在方法參數前使用 @ModelAttribute，Spring 會自動將表單提交的資料映射到指定的物件屬性上。
	這在處理使用者輸入時非常有用，因為我們可以直接從表單資料中提取值並填充到 Java 對象（例如 RoomDto）中。
	
	Model model 的作用是：
	1.將控制器中的數據傳遞到視圖層。
	2.支援使用 addAttribute 方法來添加數據，以便視圖能夠使用這些數據來渲染頁面。
	 */
		List<RoomDto> roomDtos = roomService.getAllRooms();
		//model.addAttribute("roomDto", new RoomDto());
		model.addAttribute("roomDtos", roomDtos);
		return "room/room";
	}
	
	@PostMapping
	// @Valid 進行驗證
	// BindingResult 驗證結果
	public String addRoom(@Valid @ModelAttribute RoomDto roomDto, BindingResult bindingResult, Model model) {
	/*
	定義一個 POST 請求方法，用於新增會議室。如果資料驗證錯誤，則重新返回 room/room 頁面；
	否則，呼叫 roomService 新增會議室，並重導到 /rooms。
	
	@Valid:是一個 Java Bean 驗證註解，用於在控制器中啟用對物件（如 roomDto）的自動驗證。
	當表單數據提交後，Spring 會檢查物件內部的字段是否符合預定的驗證條件（例如非空、長度限制等）
	
	BindingResult bindingResult：
	BindingResult 是一個用於保存驗證結果的物件，它會緊跟在被 @Valid 註解的參數後面。
	當 @Valid 進行驗證後，如果有錯誤，它們會被放入 bindingResult 中。BindingResult 可用於檢查和獲取錯誤訊息，
	以便根據驗證情況來執行不同的邏輯。
	
	
	bindingResult.hasErrors()：
	bindingResult.hasErrors() 是 BindingResult 的一個方法，用來檢查是否有驗證錯誤發生。
	 */
		
		
		if(bindingResult.hasErrors()) { // 若有錯誤發生
			model.addAttribute("roomDtos", roomService.getAllRooms());
			return "room/room"; // 會自動將錯誤訊息傳給 jsp
		}
		roomService.addRoom(roomDto);
		return "redirect:/rooms"; // 重導到 /rooms 頁面
	}
	
	@GetMapping("/delete/{roomId}")
	public String deleteRoom(@PathVariable Integer roomId) {
	/*
	此方法用於刪除指定的會議室，路徑中的 roomId 將映射到方法參數 roomId。執行刪除後，重導到 /rooms。	
	 */
		roomService.deleteRoom(roomId);
		return "redirect:/rooms"; // 重導到 /rooms 頁面
	}
	
	
	@GetMapping("/{roomId}")
	public String getRoom(@PathVariable Integer roomId, Model model) {
	//此方法用於查詢單個會議室的詳細資料，將結果傳遞至 room/room_update 視圖以進行顯示和編輯。
	//@PathVariable 的作用就是從 URL 路徑中提取參數值，並將其自動傳遞到控制器方法的參數中，以便用於處理動態請求。
		RoomDto roomDto = roomService.getRoomById(roomId);
		model.addAttribute("roomDto", roomDto);
		return "room/room_update";
	}
	
	
	
	@PostMapping("/update/{roomId}")
	public String updateRoom(@PathVariable Integer roomId, @Valid @ModelAttribute RoomDto roomDto, BindingResult bindingResult, Model model) {
	//定義一個 POST 請求方法，用於更新會議室資料。當有驗證錯誤時，返回 room/room_update，否則更新資料並重導到 /rooms。
		
		if(bindingResult.hasErrors()) { // 若有錯誤發生
			model.addAttribute("roomDto", roomDto); // 將原本的 roomDto 回傳
			return "room/room_update"; // 會自動將錯誤訊息傳給 jsp
		}
		roomService.updateRoom(roomId, roomDto);
		return "redirect:/rooms"; // 重導到 /rooms 頁面
	}
	
	@ExceptionHandler({RoomException.class})
	public String handleRoomException(RoomException e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
		//處理 RoomException 類型的異常，將錯誤訊息傳遞至 error 頁面以顯示。
	}
	
	
}