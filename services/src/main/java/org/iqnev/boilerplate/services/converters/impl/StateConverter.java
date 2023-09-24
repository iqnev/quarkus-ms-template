package org.iqnev.boilerplate.services.converters.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.iqnev.boilerplate.dtos.StateDto;
import org.iqnev.boilerplate.entities.StateEntity;
import org.iqnev.boilerplate.services.converters.Converter;

@ApplicationScoped
public class StateConverter implements Converter<StateDto, StateEntity> {

  @Override
  public StateDto convertToDto(final StateEntity entity) {

    return StateDto.builder()
        .id(entity.getPublicId())
        .name(entity.getName())
        .region(entity.getRegion())
        .build();
  }

  @Override
  public StateEntity convertToEntity(final StateDto dto) {
    return StateEntity.builder().publicId(dto.id()).name(dto.name()).region(dto.region()).build();
  }
}
