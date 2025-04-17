import React, {useState, useEffect} from "react";
import {
  CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormTextarea,
  CFormLabel, CFormSelect, CInputGroup, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle, CRow
} from "@coreui/react";
import {cilPlus} from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import Services from "src/services";


const BillCreateForm = ({ data, inputHandler }) => {



  return <>
    <CCard className="mb-3">
      <CCardHeader>Bill</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='totalAmount'>TotalAmount</CFormLabel>
              <CFormInput
                 type='number' name='totalAmount' placeholder='TotalAmount' value={data.totalAmount}
                 onChange={evn => inputHandler(evn, value => data.totalAmount = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='discount'>Discount</CFormLabel>
              <CFormInput
                 type='number' name='discount' placeholder='Discount' value={data.discount}
                 onChange={evn => inputHandler(evn, value => data.discount = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='tax'>Tax</CFormLabel>
              <CFormInput
                 type='number' name='tax' placeholder='Tax' value={data.tax}
                 onChange={evn => inputHandler(evn, value => data.tax = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='finalAmmount'>FinalAmmount</CFormLabel>
              <CFormInput
                 type='number' name='finalAmmount' placeholder='FinalAmmount' value={data.finalAmmount}
                 onChange={evn => inputHandler(evn, value => data.finalAmmount = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='createdAt'>CreatedAt</CFormLabel>
              <CFormInput
                 type='datetime-local' name='createdAt' placeholder='CreatedAt' value={data.createdAt}
                 onChange={evn => inputHandler(evn, value => data.createdAt = value)}/>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
  </>
}

const OrderCreateForm = ({ data, inputHandler }) => {
  const [tableList, setTableList] = useState([])
  const fetchTable = () => {
    Services.tableService.findAllOptimized()
    .then(res => setTableList(res))
    .catch(err => console.log(err))
  }

  const [createTableModal, setCreateTableModal] = useState(false)
  const toggleCreateTableModal = () => setCreateTableModal(true)


  return <>
    <CCard className="mb-3">
      <CCardHeader>Order</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='createdAt'>CreatedAt</CFormLabel>
              <CFormInput
                 type='datetime-local' name='createdAt' placeholder='CreatedAt' value={data.order.createdAt}
                 onChange={evn => inputHandler(evn, value => data.order.createdAt = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='status'>Status</CFormLabel>
              <CFormSelect
                name='status' id='status' placeholder='Status' value={data.order.status}
                onChange={evn => inputHandler(evn, value => data.order.status = value)}>
                <option>Select Status</option>
              {OrderStatusEnumValues.map((it, index) => <option key={index} value={it}>{ it }</option>)}
              </CFormSelect>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='table'>Table</CFormLabel>
              <CInputGroup>
                <CFormSelect
                   name='table' id='table' placeholder='Table' value={data.order.table?.id}
                   onChange={evn => inputHandler(evn, value => data.order.table.id = value)}>
                  <option>Select Table</option>
                  {tableList.map((it, index) => <option key={index} value={it?.id}>{ it.id }</option>)}
                </CFormSelect>
                <CButton color="secondary" type="button" variant="outline" onClick={toggleCreateTableModal}>
                  <CIcon icon={cilPlus}/>
                </CButton>
              </CInputGroup>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
    {createTableModal &&
      <TableUpdate
        visible={true}
        onCreate={created => {
          inputHandler(created, value => data.order.table = value, false)
          setTableList(prev => {
            prev.push(created)
            return prev
          })
          setCreateTableModal(false)
        }}
        onClose={() => setCreateTableModal(false)}
      />
    }
  </>
}
const IssuerCreateForm = ({ data, inputHandler }) => {



  return <>
    <CCard className="mb-3">
      <CCardHeader>User</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='fullname'>Fullname</CFormLabel>
              <CFormInput
                 type='text' name='fullname' placeholder='Fullname' value={data.issuer.fullname}
                 onChange={evn => inputHandler(evn, value => data.issuer.fullname = value)}/>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
  </>
}

const BillUpdate = ({ visible, onClose, onUpdate, id }) => {
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
    Services.billService.findById(id)
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
    Services.billService.update(data)
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
        <CModalTitle>Update Bill</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <BillCreateForm data={data} inputHandler={inputHandler}/>
        <OrderCreateForm data={data} inputHandler={inputHandler}/>
        <IssuerCreateForm data={data} inputHandler={inputHandler}/>
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

export default BillUpdate