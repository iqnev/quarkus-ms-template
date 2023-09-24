package org.iqnev.boilerplate.rest;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import io.quarkus.panache.common.Page;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.iqnev.boilerplate.commons.models.PageModel;
import org.iqnev.boilerplate.commons.page.PageModelBuilder;
import org.iqnev.boilerplate.dtos.StateDto;
import org.iqnev.boilerplate.services.StateService;

@Path("/states")
@RequiredArgsConstructor
@Tag(name = "State Resource", description = "State APIs")
public class StateResource {

  final StateService stateService;

  final PageModelBuilder pageModelBuilder;

  @GET
  @Path("/count")
  @Operation(
      summary = "Get the count of states",
      description = "Retrieve the total number of states.")
  @APIResponse(
      responseCode = "200",
      description = "Successful response",
      content =
          @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Long.class)))
  public long count() {
    return stateService.count();
  }

  @GET
  @Operation(
      summary = "Get states by name",
      description = "Retrieve a list of states by name with pagination.")
  @APIResponse(
      responseCode = "200",
      description = "Successful response",
      content =
          @Content(
              mediaType = APPLICATION_JSON,
              schema = @Schema(implementation = PageModel.class)))
  public PageModel<StateDto> getAllByStateName(
      @QueryParam("name") final String name,
      @QueryParam("page") final int page,
      @QueryParam("size") final int size) {

    final Page pageInfo = pageModelBuilder.toPageable(page, size);

    return stateService.getAllByStateName(pageInfo, name);
  }

  @POST
  @Operation(
      summary = "Create a new state",
      description = "Create a new state with the provided information.")
  @APIResponse(
      responseCode = "201",
      description = "State created successfully",
      content =
          @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = StateDto.class)))
  public StateDto createState(final StateDto state) {
    return stateService.createState(state);
  }

  @PUT
  @Operation(
      summary = "Update an existing state",
      description = "Update an existing state with the provided information.")
  @APIResponse(responseCode = "204", description = "State updated successfully")
  @APIResponse(responseCode = "404", description = "State not found")
  public void updateState(@PathParam("id") String publicId, final StateDto state) {

    publicId = trimToEmpty(publicId);

    if (!publicId.equals(state.id())) {
      throw new WebApplicationException(
          String.format(
              "State public Id path variable is different from state public Id in payload. Path variable: %s, state: %s",
              publicId, state.id()),
          Response.Status.NOT_FOUND);
    }

    stateService.update(state);
  }

  @DELETE
  @Path("/{id}")
  @Operation(
      summary = "Delete a state by ID",
      description = "Delete a state by its unique identifier.")
  @APIResponse(responseCode = "204", description = "State deleted successfully")
  @APIResponse(responseCode = "404", description = "State not found")
  public void deleteState(@PathParam("id") final String publicId) {
    stateService.delete(publicId);
  }
}
