package org.iqnev.boilerplate.services.impl;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iqnev.boilerplate.commons.UuidUtils;
import org.iqnev.boilerplate.commons.models.PageModel;
import org.iqnev.boilerplate.dtos.CityDto;
import org.iqnev.boilerplate.entities.CityEntity;
import org.iqnev.boilerplate.repositories.CityRepository;
import org.iqnev.boilerplate.repositories.StateRepository;
import org.iqnev.boilerplate.services.CityService;
import org.iqnev.boilerplate.services.converters.impl.CityConverter;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

  final CityRepository cityRepository;

  final StateRepository stateRepository;

  final CityConverter cityConverter;

  @Override
  public long count() {
    final long cityCount = cityRepository.count();

    if (cityCount == 0) {
      log.warn("Cities not found!");
    }

    return cityCount;
  }

  @Override
  public PageModel<CityDto> getAllByCityName(final Page pagesInfo, final String cityName) {

    final List<CityDto> resultingCities = new ArrayList<>();

    final PageModel<CityEntity> cities = cityRepository.getAllByCityName(pagesInfo, cityName);

    cities.getContent().forEach(city -> resultingCities.add(cityConverter.convertToDto(city)));

    return PageModel.createPageModelFromContentAndMetaData(resultingCities, cities);
  }

  @Override
  public PageModel<CityDto> getAllCities(final Page pagesInfo) {

    final List<CityDto> resultingCities = new ArrayList<>();

    final PageModel<CityEntity> cities = cityRepository.getAllByCities(pagesInfo);

    cities.getContent().forEach(city -> resultingCities.add(cityConverter.convertToDto(city)));

    return PageModel.createPageModelFromContentAndMetaData(resultingCities, cities);
  }

  @Override
  public PageModel<CityDto> getAllByStateId(final Page pagesInfo, final String publicId) {

    final List<CityDto> resultingCities = new ArrayList<>();

    final PageModel<CityEntity> cities = cityRepository.getAllByStatePublicId(pagesInfo, publicId);

    cities.getContent().forEach(city -> resultingCities.add(cityConverter.convertToDto(city)));

    return PageModel.createPageModelFromContentAndMetaData(resultingCities, cities);
  }

  @Override
  public CityDto createCity(final CityDto city) {

    return QuarkusTransaction.requiringNew()
        .call(
            () -> {
              final CityEntity cityEntity = cityConverter.convertToEntity(city);

              cityEntity.setPublicId(UuidUtils.uuidToBase32Random());

              cityRepository.create(cityEntity);

              return cityConverter.convertToDto(cityEntity);
            });
  }

  @Override
  public void update(final CityDto city) {

    QuarkusTransaction.requiringNew().run(() -> cityRepository.update(city));
  }

  @Override
  public void delete(final String publicId) {

    QuarkusTransaction.requiringNew()
        .run(
            () -> {
              if (!cityRepository.deleteStateByPublicId(publicId)) {
                throw new WebApplicationException("City not found!", Response.Status.NOT_FOUND);
              }
            });
  }
}
