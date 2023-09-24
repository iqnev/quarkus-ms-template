package org.iqnev.boilerplate.services.converters.impl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.iqnev.boilerplate.dtos.CityDto;
import org.iqnev.boilerplate.entities.CityEntity;
import org.iqnev.boilerplate.services.converters.Converter;

@ApplicationScoped
@RequiredArgsConstructor
public class CityConverter implements Converter<CityDto, CityEntity> {

  final StateConverter stateConverter;

  @Override
  public CityDto convertToDto(final CityEntity entity) {

    return CityDto.builder()
        .id(entity.getPublicId())
        .name(entity.getName())
        .state(stateConverter.convertToDto(entity.getState()))
        .build();
  }

  @Override
  public CityEntity convertToEntity(final CityDto dto) {

    return CityEntity.builder()
        .publicId(dto.id())
        .name(dto.name())
        .state(stateConverter.convertToEntity(dto.state()))
        .build();
  }
}
