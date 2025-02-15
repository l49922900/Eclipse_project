package com.example.demo.service.impl;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ScooterException;
import com.example.demo.exception.ScooterNotFoundException;
import com.example.demo.mapper.ScooterMapper;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Scooter;
import com.example.demo.model.entity.Scooter.Status;
import com.example.demo.model.entity.ScooterPart;
import com.example.demo.repository.ScooterPartRepository;
import com.example.demo.repository.ScooterRepository;
import com.example.demo.repository.ScooterRepositoryJdbc;
import com.example.demo.service.ScooterService;




@Service
public class ScooterServiceImpl implements ScooterService {

    @Autowired
    private ScooterRepository scooterRepository;
    
    @Autowired
    private ScooterRepositoryJdbc scooterRepositoryJdbc;

    @Autowired
    private ScooterMapper scooterMapper;
    
    @Autowired   
    private ScooterPartRepository scooterPartRepository;
    
  

    @Override
    public List<ScooterDto> getAllScooters() {
    	/*
    	功能：從 scooterRepository 取得所有 Scooter 實體，並將其轉換成 ScooterDto 後，以 List<ScooterDto> 形式返回。
		用途：用於取得所有機車的資料列表。
    	 */	
        return scooterRepository.findAll()
                .stream()
                .map(scooterMapper::toDto)
                .collect(toList());
    }

    @Override
    public ScooterDto getScooterById(Integer scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new ScooterNotFoundException("找不到機車: scooterId: " + scooterId));
        return scooterMapper.toDto(scooter);
    }

   
    @Override
    public Scooter addScooter(ScooterDto scooterDto) {
    /*
    功能：將傳入的 ScooterDto 轉換成 Scooter 實體，設定機車狀態為 Status.available，並保存到資料庫中。若保存失敗，則拋出 ScooterException。
	用途：用於新增機車。
     */   	  	
        Scooter scooter = scooterMapper.toEntity(scooterDto);
        scooter.setStatus(Status.available);
        Scooter scooterSave = scooterRepository.save(scooter);
        if(Objects.isNull(scooterSave)) {
        	throw new ScooterException("無法新增機車");
        }
        return scooterSave;
    }
    
    

    @Override
    public void addScooter(Integer scooterId, String licensePlate, String model, Integer cc, String type, Status status,
                           Double dailyRate, String conditionNote, LocalDate lastMaintenanceDate,String imagePath) {
     /*
     功能：根據傳入的各項機車屬性參數，建立 ScooterDto，並呼叫 addScooter(ScooterDto scooterDto) 方法新增機車。
		   同時將 scooterId 設為 null，確保資料庫會自動生成新的 id。
	  用途：用於新增機車的多參數版本。   
      */
    	ScooterDto scooterDto = new ScooterDto(scooterId, licensePlate, model, cc, type, status, dailyRate, conditionNote, lastMaintenanceDate,imagePath);
        scooterDto.setScooterId(null);
        addScooter(scooterDto);
    }
    
    @Override
    public void updateScooter(Integer scooterId, ScooterDto scooterDto) {
        
    	Optional<Scooter> optScooter = scooterRepository.findById(scooterId);
        //先用findById確認要修改資訊的機車存在
        if (optScooter.isEmpty()) {
            throw new ScooterNotFoundException("修改失敗: 機車 " + scooterId + " 不存在");
        }
        
        // 若沒有新上傳圖片且 DTO 中的圖片路徑為空，才使用原圖片路徑
        if (scooterDto.getImagePath() == null || scooterDto.getImagePath().trim().isEmpty()) {
            scooterDto.setImagePath(optScooter.get().getImagePath());
        }
        
        scooterDto.setScooterId(scooterId);
        Scooter scooter = scooterMapper.toEntity(scooterDto);
        int rowcount = scooterRepositoryJdbc.update(scooter);
        if (rowcount == 0) {
            throw new ScooterException("無任何機車記錄被修改");
        }
    }

    @Override
    public void updateScooter(Integer scooterId, String licensePlate, String model, Integer cc, String type, Status status,
            Double dailyRate, String conditionNote, LocalDate lastMaintenanceDate,String imagePath) {
        ScooterDto scooterDto = new ScooterDto(scooterId, licensePlate, model, cc, type, status, dailyRate, conditionNote, lastMaintenanceDate,imagePath);
        updateScooter(scooterId, scooterDto);
    }

    @Override
    public void deleteScooter(Integer scooterId) {
        Optional<Scooter> optScooter = scooterRepository.findById(scooterId);
        if (optScooter.isEmpty()) {
            throw new ScooterNotFoundException("刪除失敗: 機車 " + scooterId + " 不存在");
        }
        try {
        	scooterRepository.deleteById(scooterId);
		} catch (ScooterException e) {
			throw new ScooterException("刪除機車時發生錯誤: scooterId = " + scooterId);
		}
        
    }
    



    @Override
    public List<ScooterDto> findScootersByCc(Integer cc) {
        return scooterRepository.findBycc(cc)
                .stream()
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ScooterDto> filterScooters(String type, String cc, String status, String dailyRate) {
/*
功能：從 scooterRepository 取得所有 Scooter 實體後，依照傳入的 type, cc, status, dailyRate 進行多條件篩選。
用途：用於多條件篩選機車。
*/   	 	
        return scooterRepository.findAll().stream()
        //取得所有 Scooter 資料(List<Scooter>)，並將Scooter 資料(List<Scooter>)轉換成 Stream 以進行後續的串流操作。
                .filter(scooter -> type == null || type.isEmpty() || scooter.getType().equals(type))
/*
filter():根據條件過濾資料流中的元素。當條件成立 (true) 時，該元素會被保留；否則會被丟棄。
          	每一個 .filter() 都只會保留符合條件的 Scooter，並將不符合條件的元素過濾掉。
這一行的作用:如果 type 是 null 或空字串，則表示「不使用這個條件進行篩選」，通過這個檢查的機車將被保留。
			如果 type 有值，則篩選出 type 與機車的 type 相符的結果。     
 */             
       
                .filter(scooter -> cc == null || cc.isEmpty() || scooter.getCc().equals(Integer.parseInt(cc)))
//Integer.parseInt(cc) 是將字串直接轉換成基本型別 int。由於 equals() 方法是物件的方法，
//Java 會自動將 parseInt() 產生的 int 透過自動裝箱(autoboxing)轉換成 Integer 物件，以便能夠調用 equals() 方法。
                .filter(scooter -> status == null || status.isEmpty() || scooter.getStatus().name().equals(status))
                .filter(scooter -> dailyRate == null || dailyRate.isEmpty() || 
                                   scooter.getDailyRate() == (Double.parseDouble(dailyRate)))
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScooterDto> filterScooters(String type, String cc, String dailyRate) {
/*
功能：
與上一個方法類似，但沒有 status 篩選條件，且額外篩選出 status 為 "available" 的機車。
將篩選結果轉換為 ScooterDto 後，返回 List<ScooterDto>。
用途：
用於多條件篩選且只顯示可租借（available）狀態的機車。    	
 */
        return scooterRepository.findAll().stream()
                .filter(scooter -> type == null || type.isEmpty() || scooter.getType().equals(type))
                .filter(scooter -> cc == null || cc.isEmpty() || scooter.getCc().equals(Integer.parseInt(cc)))
//Integer.parseInt(cc) 是將字串直接轉換成基本型別 int。由於 equals() 方法是物件的方法，Java 會自動將 parseInt() 產生的 int 透過自動裝箱(autoboxing)轉換成 Integer 物件，以便能夠調用 equals() 方法。
                .filter(scooter -> dailyRate == null || dailyRate.isEmpty() || 
                                   scooter.getDailyRate() == (Double.parseDouble(dailyRate)))
                .filter(scooter -> scooter.getStatus().name().equals("available") )
                //.filter(scooter -> isScooterAvailableInDateRange(scooter.getId(), startDate, endDate)) // 新增日期範圍檢查
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }
   
    public void initializationAddPart(Scooter scooter) {
/*
功能：
初始化並建立四個 ScooterPart：輪胎、引擎、電瓶、大燈，每個部分的狀態為 PartStatus.normal。
將這些 ScooterPart 與對應的 Scooter 綁定後，使用 scooterPartRepository.saveAll(parts) 一次性保存到資料庫中。
用途：
用於初始化新增機車時，自動附加四個預設零件。        
 */
    	
    	List<ScooterPart> parts = List.of(
                new ScooterPart(null, scooter, "輪胎", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null),
                new ScooterPart(null, scooter, "引擎", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null),
                new ScooterPart(null, scooter, "電瓶", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null),
                new ScooterPart(null, scooter, "大燈", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null)
        );

        scooterPartRepository.saveAll(parts);
    }
}
