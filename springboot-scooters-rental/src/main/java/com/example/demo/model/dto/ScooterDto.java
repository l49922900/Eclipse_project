package com.example.demo.model.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Range;

import com.example.demo.model.entity.Scooter.Status;
import com.example.demo.service.validation.AdvancedValidation;
import com.example.demo.service.validation.BasicValidation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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


	@Range(min = 1, max = 999999999, message = "{scooterDto.cc.range}",groups = AdvancedValidation.class)
	@NotNull(message = "{scooterDto.cc.notNull}",groups = BasicValidation.class)
	private Integer cc;

	private String type;

	private Status status;

	@Range(min = 1, max = 999999999, message = "{scooterDto.dailyRate.range}",groups = AdvancedValidation.class)
	@NotNull(message = "{scooterDto.dailyRate.notNull}",groups = BasicValidation.class)
	private double dailyRate;

	private String conditionNote;

	private LocalDate lastMaintenanceDate;

    private String imagePath;
}
