package org.iqnev.boilerplate.repositories;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iqnev.boilerplate.commons.models.PageModel;
import org.iqnev.boilerplate.commons.page.PageModelBuilder;
import org.iqnev.boilerplate.dtos.CityDto;
import org.iqnev.boilerplate.entities.CityEntity;
import org.iqnev.boilerplate.entities.StateEntity;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class CityRepository implements PanacheRepository<CityEntity> {

  final StateRepository stateRepository;

  final PageModelBuilder pageModelBuilder;

  public PageModel<CityEntity> getAllByCityName(final Page pagesInfo, final String cityName) {

    final PanacheQuery<CityEntity> pagedQuery =
        find("name =?1", cityName).page(Page.ofSize(pagesInfo.size));

    if (pagedQuery.count() == 0) {
      log.warn("Name not found!");
    }

    return pageModelBuilder.buildPageModelFromPagedQuery(pagesInfo, pagedQuery);
  }

  public PageModel<CityEntity> getAllByStatePublicId(final Page pagesInfo, final String publicId) {
    final PanacheQuery<CityEntity> pagedQuery =
        find("state.publicId =?1", publicId).page(Page.ofSize(pagesInfo.size));

    if (pagedQuery.count() == 0) {
      log.warn("State not found!");
    }

    return pageModelBuilder.buildPageModelFromPagedQuery(pagesInfo, pagedQuery);
  }

  public PageModel<CityEntity> getAllByCities(final Page pagesInfo) {
    final PanacheQuery<CityEntity> pagedQuery = findAll().page(Page.ofSize(pagesInfo.size));

    if (pagedQuery.count() == 0) {
      log.warn("State not found!");
    }

    return pageModelBuilder.buildPageModelFromPagedQuery(pagesInfo, pagedQuery);
  }

  public CityEntity create(final CityEntity city) {
    final StateEntity state = stateRepository.findByPublicId(city.getState().getPublicId());

    if (isEmpty(state)) {
      throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);
    }

    city.setState(state);

    persist(city);

    return city;
  }

  public void update(final CityDto city) {
    final CityEntity updateCity = findByPublicId(city.id());

    if (isEmpty(updateCity)) {
      throw new WebApplicationException(
          String.format("City not found by id: %d", city.id()), Response.Status.NOT_FOUND);
    }

    final StateEntity state = stateRepository.findByPublicId(city.state().id());

    if (isEmpty(updateCity)) {
      throw new WebApplicationException(
          String.format("State not found by id: %d", city.state().id()), Response.Status.NOT_FOUND);
    }

    updateCity.setName(city.name());
    updateCity.setState(state);
  }

  public CityEntity findByPublicId(final String publicId) {
    return find("publicId =?1", publicId).firstResult();
  }

  public boolean deleteStateByPublicId(final String publicId) {
    return delete("publicId =?1", publicId) == 1;
  }
}
