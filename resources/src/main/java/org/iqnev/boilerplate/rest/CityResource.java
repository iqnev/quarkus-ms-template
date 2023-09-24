package org.iqnev.boilerplate.rest;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
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
import org.iqnev.boilerplate.dtos.CityDto;
import org.iqnev.boilerplate.services.CityService;

@Path("/cities")
@RequiredArgsConstructor
@Tag(name = "City Resource", description = "City APIs")
public class CityResource {

  final CityService cityService;

  final PageModelBuilder pageModelBuilder;

  @GET
  @Path("/count")
  @Operation(
      summary = "Get the total number of cities",
      description = "Returns the total count of cities in the system.")
  @APIResponse(
      responseCode = "200",
      description = "Successful response",
      content =
          @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Long.class)))
  public long count() {
    return cityService.count();
  }

  @GET
  @Operation(
      summary = "Get cities by name or state",
      description = "Returns a pageable list of cities filtered by name or state.")
  @APIResponse(
      responseCode = "200",
      description = "Successful response",
      content =
          @Content(
              mediaType = APPLICATION_JSON,
              schema = @Schema(implementation = PageModel.class)))
  public PageModel<CityDto> getAllByCityName(
      @QueryParam("name") final String cityName,
      @QueryParam("stateId") final String statePublicId,
      @QueryParam("page") final int page,
      @QueryParam("size") final int size) {

    final Page pageInfo = pageModelBuilder.toPageable(page, size);

    if (isNotEmpty(cityName)) {
      return cityService.getAllByCityName(pageInfo, cityName);
    } else if (isNotEmpty(statePublicId)) {
      return cityService.getAllByStateId(pageInfo, statePublicId);
    }

    return cityService.getAllCities(pageInfo);
  }

  @POST
  @Operation(
      summary = "Create a new city",
      description = "Creates a new city and returns the created city details.")
  @APIResponse(
      responseCode = "200",
      description = "City successfully created",
      content =
          @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = CityDto.class)))
  public CityDto createCity(final CityDto city) {
    return cityService.createCity(city);
  }

  @PUT
  @Operation(
      summary = "Update an existing city",
      description = "Updates an existing city's details.")
  @APIResponse(responseCode = "204", description = "City successfully updated")
  @APIResponse(responseCode = "404", description = "City not found")
  public void updateCity(@PathParam("id") String publicId, final CityDto city) {

    publicId = trimToEmpty(publicId);

    if (!publicId.equals(city.id())) {
      throw new WebApplicationException(
          String.format(
              "City public Id path variable is different from city public Id in payload. Path variable: %s, city: %s",
              publicId, city.id()),
          Response.Status.NOT_FOUND);
    }

    cityService.update(city);
  }

  @DELETE
  @Operation(summary = "Delete a city by ID", description = "Deletes a city by its unique ID.")
  @APIResponse(responseCode = "204", description = "City successfully deleted")
  @APIResponse(responseCode = "404", description = "City not found")
  @Path("/{id}")
  public void deleteCity(@PathParam("id") final String publicId) {
    cityService.delete(publicId);
  }
}
