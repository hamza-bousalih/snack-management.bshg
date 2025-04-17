import {
  CButton,
  CDropdown,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
  CPagination,
  CPaginationItem
} from "@coreui/react";
import {generatePageNumbers, paginationSizes} from "src/components/common/table-pagination/pagination";
import CIcon from "@coreui/icons-react";
import {cilChevronDoubleLeft, cilChevronDoubleRight, cilChevronLeft, cilChevronRight} from "@coreui/icons";

export const TablePagination = ({ pagination, setPagination, disable, onPaginate }) => {

  const changePage = (page) => {
    if (disable) return;
    onPaginate(page, pagination.size)
  }

  const changeSize = (size) => {
    onPaginate(pagination.page, size)
  }

  return <div className="mt-2 hstack gap">
    <CButton disabled color="secondary" variant="outline" style={{width: 'fit-content'}}>
      {pagination.data.length} / {pagination.totalElements}
    </CButton>
    <CDropdown className="p-2 ms-auto" style={{ width: 'fit-content' }}>
      <CDropdownToggle color="secondary" variant="outline">{pagination.size}</CDropdownToggle>
      <CDropdownMenu>
        {paginationSizes.map((size, index) =>
          <CDropdownItem
            key={index}
            onClick={() => changeSize(size)}>{size}
          </CDropdownItem>
        )}
      </CDropdownMenu>
    </CDropdown>
    {pagination?.totalPages > 1 && <>
      <CPagination className="p-2 m-0" align="end" style={{width: 'fit-content'}}>
        {!pagination.first && <>
          <CPaginationItem disabled={disable} onClick={() => changePage(0)}>
            <CIcon icon={cilChevronDoubleLeft}/>
          </CPaginationItem>
          <CPaginationItem disabled={disable} onClick={() => changePage(pagination.page - 1)}>
            <CIcon icon={cilChevronLeft}/>
          </CPaginationItem>
        </>}
        {generatePageNumbers(pagination).map((page, index) =>
          <CPaginationItem
            key={index} active={page === pagination.page + 1}
            onClick={() => (page === (pagination.page + 1)) || changePage(page - 1)}>
            {page}
          </CPaginationItem>
        )}
        {!pagination.last && <>
          <CPaginationItem disabled={disable} onClick={() => changePage(pagination.page + 1)}>
            <CIcon icon={cilChevronRight}/>
          </CPaginationItem>
          <CPaginationItem disabled={disable} onClick={() => changePage(pagination.totalPages - 1)}>
            <CIcon icon={cilChevronDoubleRight}/>
          </CPaginationItem>
        </>}
      </CPagination>
    </>}
  </div>
}
