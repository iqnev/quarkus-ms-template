package org.iqnev.boilerplate.commons.page;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.iqnev.boilerplate.commons.models.PageModel;

@ApplicationScoped
public class PageModelBuilder {

  private static final int DEFAULT_PAGE_SIZE = 20;

  public <T> PageModel<T> buildPageModelFromPagedQuery(
      final Page pagesInfo, final PanacheQuery<T> pagedQuery) {

    final int pageIndex = pagesInfo.index + 1;

    return new PageModel<>(
        pagedQuery.pageCount(),
        pagedQuery.count(),
        pageIndex,
        pagesInfo.size,
        pagedQuery.page(pagesInfo).list(),
        pageIndex == 1,
        pageIndex == pagedQuery.pageCount(),
        pagedQuery.hasPreviousPage(),
        pagedQuery.hasNextPage());
  }

  public Page toPageable(final int page, final int size) {
    int pageNumber = page - 1;
    int pageSize = size;

    if (pageNumber < 0) {
      pageNumber = 0;
    }

    if (pageSize <= 0) {
      pageSize = DEFAULT_PAGE_SIZE;
    }

    return Page.of(pageNumber, pageSize);
  }
}
