package org.iqnev.boilerplate.services;

import io.quarkus.panache.common.Page;
import org.iqnev.boilerplate.commons.models.PageModel;
import org.iqnev.boilerplate.dtos.CityDto;

public interface CityService {

  long count();

  PageModel<CityDto> getAllByCityName(Page pagesInfo, String cityName);

  PageModel<CityDto> getAllCities(Page pagesInfo);

  PageModel<CityDto> getAllByStateId(Page pagesInfo, String publicId);

  CityDto createCity(CityDto city);

  void update(CityDto city);

  void delete(String publicId);
}
