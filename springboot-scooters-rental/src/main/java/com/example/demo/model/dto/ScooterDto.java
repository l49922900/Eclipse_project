package com.example.demo.model.dto;

import java.time.LocalDate;
import com.example.demo.model.entity.Scooter.Status;
import com.example.demo.validation.AdvancedValidation;
import com.example.demo.validation.BasicValidation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScooterDto {

	private Integer scooterId;

	@NotBlank(groups = BasicValidation.class,message = "{scooterDto.licensePlate.notNull}")
	@Size(min = 1, max = 20,groups = AdvancedValidation.class, message = "{scooterDto.licensePlate.size}")
	//groups = AdvancedValidation.class: 使用分組標記介面，為了優先驗證ScooterDto @NotNull
	private String licensePlate;

	@NotBlank(groups = BasicValidation.class,message = "{scooterDto.model.notNull}")
	@Size(min = 1, max = 50,groups = AdvancedValidation.class, message = "{scooterDto.model.size}")
	//groups = AdvancedValidation.class: 使用分組標記介面，為了優先驗證ScooterDto @NotNull
	private String model;

//	@NotNull(message = "{scooterDto.cc.notNull}")
//	@Positive(message = "{scooterDto.cc.positive}")
	@Min(value = 1, message = "{scooterDto.cc.positive}")
	private Integer cc;

	private String type;

	private Status status;

//	@Positive(message = "{scooterDto.dailyRate.positive}")
	@Min(value = 1, message = "{scooterDto.dailyRate.positive}")
	private double dailyRate;

	private String conditionNote;

	private LocalDate lastMaintenanceDate;
}