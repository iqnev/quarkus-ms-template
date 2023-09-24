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
import org.iqnev.boilerplate.dtos.StateDto;
import org.iqnev.boilerplate.entities.StateEntity;
import org.iqnev.boilerplate.repositories.StateRepository;
import org.iqnev.boilerplate.services.StateService;
import org.iqnev.boilerplate.services.converters.impl.StateConverter;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class StateServiceImpl implements StateService {

  final StateRepository stateRepository;

  final StateConverter stateConverter;

  @Override
  public long count() {

    final long stateCount = stateRepository.count();

    if (stateCount == 0) {
      log.warn("States not found!");
    }

    return stateCount;
  }

  @Override
  public PageModel<StateDto> getAllByStateName(final Page pagesInfo, final String name) {

    final List<StateDto> resultingStates = new ArrayList<>();

    final PageModel<StateEntity> states = stateRepository.getAllByStateName(pagesInfo, name);

    states.getContent().forEach(state -> resultingStates.add(stateConverter.convertToDto(state)));

    return PageModel.createPageModelFromContentAndMetaData(resultingStates, states);
  }

  @Override
  public StateDto createState(final StateDto state) {

    return QuarkusTransaction.requiringNew()
        .call(
            () -> {
              final StateEntity stateEntity = stateConverter.convertToEntity(state);

              stateEntity.setPublicId(UuidUtils.uuidToBase32Random());

              stateRepository.create(stateEntity);

              return stateConverter.convertToDto(stateEntity);
            });
  }

  @Override
  public void update(final StateDto state) {

    QuarkusTransaction.requiringNew().run(() -> stateRepository.update(state));
  }

  @Override
  public void delete(final String publicId) {

    QuarkusTransaction.requiringNew()
        .run(
            () -> {
              if (!stateRepository.deleteStateByPublicId(publicId)) {
                throw new WebApplicationException("State not found!", Response.Status.NOT_FOUND);
              }
            });
  }
}
