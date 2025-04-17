import React, {useEffect, useState} from 'react'
import {
  CButton, CCard, CCardBody, CCol, CNav, CNavItem, CRow, CTable,
  CTableBody, CTableDataCell, CTableHead, CTableHeaderCell, CTableRow
} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {cilPen, cilReload, cilTrash} from "@coreui/icons";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import {TablePagination} from "src/components/common/table-pagination/table.pagination";
import DeleteDialog from "src/components/common/delete-dialog/DeleteDialog";
import {paginationInit} from "src/components/common/table-pagination/pagination";
import Services from "src/services";
import {modes} from 'src/utils';
import OrderCreate from "src/views/order/OrderCreate";
import OrderUpdate from "src/views/order/OrderUpdate";

const OrderList = () => {
  const [paginating, setPaginating] = useState(false)
  const [loading, setLoading] = useState(false)
  const [currentId, setCurrentId] = useState(undefined)
  const [currentIndex, setCurrentIndex] = useState(-1)
  const [mode, setMode] = useState(modes.non)

  const [pagination, setPagination] = useState(paginationInit)

  const paginate = (page, size) => {
    setPaginating(true)
    Services.orderService.findPaginated(page, size)
      .then(value => setPagination(value))
      .catch(error => console.log(error))
      .finally(() => {
        setPaginating(false)
        setLoading(false)
      })
  }

  const openCreateModal = () => {
    setMode(modes.create)
  };

  const openUpdateModal = (id, i) => {
    setMode(modes.update)
    setCurrentId(id)
    setCurrentIndex(i)
  };

  const openDeleteModal = (id, i) => {
    setMode(modes.delete)
    setCurrentId(id)
    setCurrentIndex(i)
  };

  const deleteHandler = () => {
    Services.orderService.deleteById(currentId)
      .then(() => {
        setPagination(prev => {
          prev.data.splice(currentIndex, 1)
          return prev
        })
      })
      .catch(err => console.log(err))
      .finally(() => setMode(modes.non))
  }

  const closeModal = () => {
    setMode(modes.non)
    setCurrentId(undefined)
    setCurrentIndex(-1)
  }

  const switchModes = () => {
    switch (mode) {
      case modes.delete:
        return <DeleteDialog
          visible={mode === modes.delete}
          itemId={currentId}
          onDelete={deleteHandler}
          onClose={closeModal}
        />
      case modes.create:
        return <OrderCreate
          visible={mode === modes.create}
          onCreate={val => {
            setPagination(prev => {
              prev.data.unshift(val)
              return prev
            })
          }}
          onClose={closeModal}
        />
      case modes.update:
        return <OrderUpdate
          visible={mode === modes.update}
          id={currentId}
          onUpdate={val => {
            setPagination(prev => {
              prev.data[currentIndex] = val
              return prev
            })
          }}
          onClose={closeModal}
        />
      default:
        return <></>
    }
  }

  useEffect(() => {
    paginate()
  }, []);

  return <>
    <CRow className="mb-2">
      <CNav className='hstack gap'>
        <CNavItem className='p-2 ms-auto'>
          <SpinnerButton
            icon={<CIcon icon={cilReload}/>}
            label='ReFresh' color='secondary' when={loading}
            onClick={() => paginate(pagination.page, pagination.size)}/>
        </CNavItem>
        <CNavItem className="p-2">
          <CButton color="primary" onClick={openCreateModal}>Add New</CButton>
        </CNavItem>
      </CNav>
    </CRow>

    {loading || <>
      <CRow>
        <CCol xs>
          <CCard className='mb-4'>
            <CCardBody>
              <CTable hover={true} responsive={true} striped={true} align="middle" className="mb-0 border">
                <CTableHead className='text-nowrap text-truncate'>
                  <CTableRow>
                    <CTableHeaderCell className="bg-body-tertiary">No.</CTableHeaderCell>
                    <CTableHeaderCell className="bg-body-tertiary">CreatedAt</CTableHeaderCell>
                    <CTableHeaderCell className="bg-body-tertiary">Status</CTableHeaderCell>
                    <CTableHeaderCell className="bg-body-tertiary">User</CTableHeaderCell>
                    <CTableHeaderCell className="bg-body-tertiary">Table</CTableHeaderCell>
                    <CTableHeaderCell className="bg-body-tertiary" style={{ display: 'flex', justifyContent: 'end' }}>
                      Actions
                    </CTableHeaderCell>
                  </CTableRow>
                </CTableHead>
                <CTableBody>
                  {pagination.data.map((it, i) =>
                    <CTableRow key={i}>
                      <CTableDataCell>{it.id}</CTableDataCell>
                      <CTableDataCell>{ it.createdAt }</CTableDataCell>
                      <CTableDataCell>{ it.status }</CTableDataCell>
                      <CTableDataCell>{ it.creator ? it.creator.id: "null" }</CTableDataCell>
                      <CTableDataCell>{ it.table ? it.table.id: "null" }</CTableDataCell>
                      <CTableDataCell style={{ display: 'flex', justifyContent: 'end' }}>
                        <CButton onClick={() => openUpdateModal(it.id, i)}>
                          <CIcon icon={cilPen} size="lg"/>
                        </CButton>
                        <CButton onClick={() => openDeleteModal(it.id, i)}>
                          <CIcon icon={cilTrash} size="lg"/>
                        </CButton>
                      </CTableDataCell>
                    </CTableRow>
                  )}
                </CTableBody>
              </CTable>
              <TablePagination
                setPagination={(page, size) => paginate(page, size)}
                pagination={pagination}
                onPaginate={paginate}
                disable={loading || paginating}/>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>}
    {switchModes()}
  </>
}

export default React.memo(OrderList);
