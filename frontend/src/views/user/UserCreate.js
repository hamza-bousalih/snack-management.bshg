import React, {useState, useEffect} from "react";
import {
  CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormTextarea,
  CFormLabel, CFormSelect, CInputGroup, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle, CRow
} from "@coreui/react";
import {cilPlus} from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import Services from "src/services";

const dataInit = {
  fullname: '',
}


const UserCreateForm = ({ data, inputHandler }) => {



  return <>
    <CCard className="mb-3">
      <CCardHeader>User</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='fullname'>Fullname</CFormLabel>
              <CFormInput
                 type='text' name='fullname' placeholder='Fullname' value={data.fullname}
                 onChange={evn => inputHandler(evn, value => data.fullname = value)}/>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
  </>
}


const UserCreate = ({ visible, onClose, onCreate }) => {
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
    Services.userService.create(data)
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
        <CModalTitle>Create User</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <UserCreateForm data={data} inputHandler={inputHandler}/>
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

export default UserCreate