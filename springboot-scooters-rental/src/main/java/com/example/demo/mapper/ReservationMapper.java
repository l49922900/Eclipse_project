package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.ReservationDto;
import com.example.demo.model.entity.Reservation;

@Component
public class ReservationMapper {

	private final ModelMapper modelMapper;

	public ReservationMapper(ModelMapper modelMapper){
		this.modelMapper = modelMapper;
	}



	public Reservation toEntity(ReservationDto reservationDto) {
		return modelMapper.map(reservationDto, Reservation.class);
		
	}
	
	public ReservationDto toDto(Reservation reservation) {
		return modelMapper.map(reservation,ReservationDto.class);
	}
}
