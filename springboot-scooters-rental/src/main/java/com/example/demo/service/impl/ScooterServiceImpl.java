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
    

    
// // 檢查特定機車是否在日期範圍內可用
//    private boolean isScooterAvailableInDateRange(Long scooterId, LocalDate startDate, LocalDate endDate) {
//        return ScooterRepositoryJdbcImpl.findConflictingRentals(scooterId, startDate, endDate).isEmpty();
//    }
    
    

    @Override
    public List<ScooterDto> getAllScooters() {
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

        Scooter scooter = scooterMapper.toEntity(scooterDto);
        Scooter scooterSave = scooterRepository.save(scooter);
        if(Objects.isNull(scooterSave)) {
        	throw new ScooterException("無法新增機車");
        }
        return scooterSave;
    }
    
//    @Override
//    public Scooter addScooter(ScooterDto scooterDto) {
//
//        Scooter scooter = scooterMapper.toEntity(scooterDto);
//        Scooter sccooterSave = scooterRepository.save(scooter);
//        
//        return sccooterSave;
//    }
    

    @Override
    public void addScooter(Integer scooterId, String licensePlate, String model, Integer cc, String type, Status status,
                           Double dailyRate, String conditionNote, LocalDate lastMaintenanceDate,String imagePath) {
        ScooterDto scooterDto = new ScooterDto(scooterId, licensePlate, model, cc, type, status, dailyRate, conditionNote, lastMaintenanceDate,imagePath);
        scooterDto.setScooterId(null);
        addScooter(scooterDto);
    }
    
    @Override
    public void updateScooter(Integer scooterId, ScooterDto scooterDto) {
        Optional<Scooter> optScooter = scooterRepository.findById(scooterId);
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
        return scooterRepository.findAll().stream()
                .filter(scooter -> type == null || type.isEmpty() || scooter.getType().equals(type))
/*
如果 type 是 null 或空字串，則表示「不使用這個條件進行篩選」，通過這個檢查的機車將被保留。
如果 type 有值，則篩選出 type 與機車的 type 相符的結果。     
 */
       
                .filter(scooter -> cc == null || cc.isEmpty() || scooter.getCc().equals(Integer.parseInt(cc)))
//Integer.parseInt(cc) 是將字串直接轉換成基本型別 int。由於 equals() 方法是物件的方法，Java 會自動將 parseInt() 產生的 int 透過自動裝箱(autoboxing)轉換成 Integer 物件，以便能夠調用 equals() 方法。
                .filter(scooter -> status == null || status.isEmpty() || scooter.getStatus().name().equals(status))
                .filter(scooter -> dailyRate == null || dailyRate.isEmpty() || 
                                   scooter.getDailyRate() == (Double.parseDouble(dailyRate)))
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScooterDto> filterScooters(String type, String cc, String dailyRate) {
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
        List<ScooterPart> parts = List.of(
                new ScooterPart(null, scooter, "輪胎", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null,null),
                new ScooterPart(null, scooter, "引擎", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null,null),
                new ScooterPart(null, scooter, "電瓶", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null,null),
                new ScooterPart(null, scooter, "大燈", ScooterPart.PartStatus.normal, LocalDate.now(), new ArrayList<>(),null,null)
        );

        scooterPartRepository.saveAll(parts);
    }
}
