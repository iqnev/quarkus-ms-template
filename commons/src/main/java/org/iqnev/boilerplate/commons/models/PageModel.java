package org.iqnev.boilerplate.commons.models;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PageModel<T> {

  private int totalPages;

  private long totalElements;

  /** Page number starts from 1 */
  private int page;

  private int size;

  private List<T> content;

  private boolean first;

  private boolean last;

  private boolean hasPrevious;

  private boolean hasNext;

  public static <T> PageModel<T> emptyPageModel() {
    final PageModel<T> result = new PageModel<>();

    result.content = List.of();

    return result;
  }

  public static <T> PageModel<T> createPageModelForSingleRecord(final T content) {

    final PageModel<T> result = new PageModel<>();

    result.totalPages = 1;

    result.totalElements = 1;

    result.page = 1;

    result.size = 1;

    result.content = List.of(content);

    result.first = true;

    result.last = true;

    result.hasPrevious = false;

    result.hasNext = false;

    return result;
  }

  public static <T, S> PageModel<T> createPageModelFromContentAndMetaData(
      final List<T> content, final PageModel<S> source) {

    final PageModel<T> result = new PageModel<>();

    result.totalPages = source.totalPages;

    result.totalElements = source.totalElements;

    result.page = source.page;

    result.size = source.size;

    result.content = content;

    result.first = source.first;

    result.last = source.last;

    result.hasPrevious = source.hasPrevious;

    result.hasNext = source.hasNext;

    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("totalPages", totalPages)
        .append("totalElements", totalElements)
        .append("page", page)
        .append("size", size)
        .append("content.size", content.size())
        .append("first", first)
        .append("last", last)
        .append("hasPrevious", hasPrevious)
        .append("hasNext", hasNext)
        .toString();
  }
}
