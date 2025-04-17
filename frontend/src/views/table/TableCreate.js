import React, {useState, useEffect} from "react";
import {
  CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormTextarea,
  CFormLabel, CFormSelect, CInputGroup, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle, CRow
} from "@coreui/react";
import {cilPlus} from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import Services from "src/services";
import {TableStatusEnumValues} from 'src/enums';

const dataInit = {
  tableNumber: '',
  status: '',
}


const TableCreateForm = ({ data, inputHandler }) => {



  return <>
    <CCard className="mb-3">
      <CCardHeader>Table</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='tableNumber'>TableNumber</CFormLabel>
              <CFormInput
                 type='number' name='tableNumber' placeholder='TableNumber' value={data.tableNumber}
                 onChange={evn => inputHandler(evn, value => data.tableNumber = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='status'>Status</CFormLabel>
              <CFormSelect
                name='status' id='status' placeholder='Status' value={data.status}
                onChange={evn => inputHandler(evn, value => data.status = value)}>
                <option>Select Status</option>
              {TableStatusEnumValues.map((it, index) => <option key={index} value={it}>{ it }</option>)}
              </CFormSelect>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
  </>
}


const TableCreate = ({ visible, onClose, onCreate }) => {
  const [sending, setSending] = useState(false)
  const [data, setData] = useState({ ...dataInit })

  const inputHandler = (evn, setter, input=true) => {
    setData(prev => {
      setter(input? evn.target.value: evn)
      return { ...prev }
    })
  }

  const create = () => {
    setSending(true)
    Services.tableService.create(data)
      .then(res => {
        onCreate(res)
        onClose()
      })
      .catch(err => console.log(err))
      .finally(() => setSending(false))
  }

  const reset = () => setData({ ...dataInit })

  return <>
    <CModal alignment="center" backdrop="static" size="xl" scrollable={true} visible={visible} onClose={onClose}>
      <CModalHeader>
        <CModalTitle>Create Table</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <TableCreateForm data={data} inputHandler={inputHandler}/>
      </CModalBody>
      <CModalFooter>
        <CButton color="secondary" variant="outline" onClick={onClose}>Cancel</CButton>
        <CButton color="secondary" variant="outline" onClick={reset}>Reset</CButton>
        <SpinnerButton
          color='primary'
          icon={<CIcon icon={cilPlus} size='sm'/>}
          when={sending}
          onClick={create}
          label='Create'
        />
      </CModalFooter>
    </CModal>
  </>
}

export default TableCreate