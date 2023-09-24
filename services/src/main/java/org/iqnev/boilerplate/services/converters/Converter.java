package org.iqnev.boilerplate.services.converters;

public interface Converter<T, Y> {

  T convertToDto(final Y entity);

  Y convertToEntity(final T dto);
}
