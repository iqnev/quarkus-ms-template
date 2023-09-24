package org.iqnev.boilerplate.services;

import io.quarkus.panache.common.Page;
import org.iqnev.boilerplate.commons.models.PageModel;
import org.iqnev.boilerplate.dtos.StateDto;

public interface StateService {

  long count();

  PageModel<StateDto> getAllByStateName(Page pagesInfo, String stateName);

  StateDto createState(StateDto state);

  void update(StateDto state);

  void delete(String publicId);
}
