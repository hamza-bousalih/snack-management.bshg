import React, {useState, useEffect} from "react";
import {
  CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormTextarea,
  CFormLabel, CFormSelect, CInputGroup, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle, CRow
} from "@coreui/react";
import {cilPlus} from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import Services from "src/services";


const MenuItemCreateForm = ({ data, inputHandler }) => {



  return <>
    <CCard className="mb-3">
      <CCardHeader>MenuItem</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='name'>Name</CFormLabel>
              <CFormInput
                 type='text' name='name' placeholder='Name' value={data.name}
                 onChange={evn => inputHandler(evn, value => data.name = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='price'>Price</CFormLabel>
              <CFormInput
                 type='number' name='price' placeholder='Price' value={data.price}
                 onChange={evn => inputHandler(evn, value => data.price = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='catrgory'>Catrgory</CFormLabel>
              <CFormInput
                 type='text' name='catrgory' placeholder='Catrgory' value={data.catrgory}
                 onChange={evn => inputHandler(evn, value => data.catrgory = value)}/>
            </CCol>
            <CCol sm={12}>
              <CFormLabel htmlFor='description'>Description</CFormLabel>
              <CFormTextarea
                rows={3} value={data.description} id="description"
                name='description' placeholder='Description'
                onChange={evn => inputHandler(evn, value => data.description = value)}/>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
  </>
}


const MenuItemUpdate = ({ visible, onClose, onUpdate, id }) => {
  const [loading, setLoading] = useState(true)
  const [sending, setSending] = useState(false)
  const [data, setData] = useState({})

  const inputHandler = (evn, setter, input=true) => {
    setData(prev => {
      setter(input? evn.target.value: evn)
      return { ...prev }
    })
  }

  const fetchById = () => {
    Services.menuItemService.findById(id)
      .then(res => {
        setData(res)
        setLoading(false)
      })
      .catch(err => {
        console.log(err)
        onClose()
      })
  }

  const update = () => {
    setSending(true)
    Services.menuItemService.update(data)
      .then(res => {
        onUpdate(res)
        onClose()
      })
      .catch(err => console.log(err))
      .finally(() => setSending(false))
  }

  const reset = () => fetchById()

  useEffect(() => {
    fetchById()
  }, [])

  return loading ? <></> : <>
    <CModal alignment="center" backdrop="static" size="xl" scrollable={true} visible={visible} onClose={onClose}>
      <CModalHeader>
        <CModalTitle>Update MenuItem</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <MenuItemCreateForm data={data} inputHandler={inputHandler}/>
      </CModalBody>
      <CModalFooter>
        <CButton color="secondary" variant="outline" onClick={onClose}>Cancel</CButton>
        <CButton color="secondary" variant="outline" onClick={reset}>Reset</CButton>
        <SpinnerButton
          color='primary'
          icon={<CIcon icon={cilPlus} size='sm'/>}
          when={sending}
          onClick={update}
          label='Update'
        />
      </CModalFooter>
    </CModal>
  </>
}

export default MenuItemUpdate