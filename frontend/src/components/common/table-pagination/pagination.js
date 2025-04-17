export const paginationSizes = [5, 10, 20, 25]

export const paginationInit = {
  data: [],
  page: 0,
  size: 10,
  totalElements: 0,
  totalPages: 0,
  first: true,
  last: false,
}

export const generatePageNumbers = (pagination) => {
  const currentPage = pagination.page;
  const totalPages = pagination.totalPages;
  const visiblePages = 5;
  const pagesToShow = Math.min(visiblePages, totalPages);

  let startPage, endPage;
  if (totalPages <= visiblePages) {
    // Less than or equal to visible pages, show all pages
    startPage = 1;
    endPage = totalPages;
  } else {
    // More than visible pages, calculate start and end pages based on current page
    const halfVisiblePages = Math.floor(visiblePages / 2);
    if (currentPage <= halfVisiblePages) {
      startPage = 1;
      endPage = visiblePages;
    } else if (currentPage + halfVisiblePages >= totalPages) {
      startPage = totalPages - visiblePages + 1;
      endPage = totalPages;
    } else {
      startPage = currentPage - halfVisiblePages;
      endPage = currentPage + halfVisiblePages;
    }
  }

  return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
}

