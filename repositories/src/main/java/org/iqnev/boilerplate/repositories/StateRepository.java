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
import org.iqnev.boilerplate.dtos.StateDto;
import org.iqnev.boilerplate.entities.StateEntity;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class StateRepository implements PanacheRepository<StateEntity> {

  final PageModelBuilder pageModelBuilder;

  public PageModel<StateEntity> getAllByStateName(final Page pagesInfo, final String name) {
    final PanacheQuery<StateEntity> pagedQuery =
        find("name =?1", name).page(Page.ofSize(pagesInfo.size));

    if (pagedQuery.count() == 0) {
      log.warn("Not found by name: {}", name);
    }

    return pageModelBuilder.buildPageModelFromPagedQuery(pagesInfo, pagedQuery);
  }

  public StateEntity create(final StateEntity state) {

    persist(state);

    return state;
  }

  public void update(final StateDto state) {
    final StateEntity updateState = findByPublicId(state.id());

    if (isEmpty(updateState)) {
      throw new WebApplicationException(
          String.format("State not found by publicId: %d", state.id()), Response.Status.NOT_FOUND);
    }

    updateState.setName(state.name());
    updateState.setRegion(state.region());
  }

  public StateEntity findByPublicId(final String publicId) {
    return find("publicId =?1", publicId).firstResult();
  }

  public boolean deleteStateByPublicId(final String publicId) {
    return delete("publicId =?1", publicId) == 1;
  }
}
