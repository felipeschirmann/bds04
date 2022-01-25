package com.devsuperior.bds04.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.exceptions.EntityNotFoundException;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new EventDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("entity not found " + id);
		}
	}

	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setDate(dto.getDate());
		entity.setName(dto.getName());
		entity.setUrl(dto.getUrl());
		entity.setCity(cityRepository.getOne(dto.getCityId()));
	}
}
