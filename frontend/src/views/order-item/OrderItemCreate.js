import React, {useState, useEffect} from "react";
import {
  CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormTextarea,
  CFormLabel, CFormSelect, CInputGroup, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle, CRow
} from "@coreui/react";
import {cilPlus} from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import Services from "src/services";
import OrderCreate from "src/views/order/OrderCreate";
import MenuItemCreate from "src/views/menu-item/MenuItemCreate";

const dataInit = {
  quantity: '',
  order: {},
  menuItem: {},
}


const OrderItemCreateForm = ({ data, inputHandler }) => {
  const [orderList, setOrderList] = useState([])
  const fetchOrder = () => {
    Services.orderService.findAllOptimized()
    .then(res => setOrderList(res))
    .catch(err => console.log(err))
  }
  const [menuItemList, setMenuItemList] = useState([])
  const fetchMenuItem = () => {
    Services.menuItemService.findAllOptimized()
    .then(res => setMenuItemList(res))
    .catch(err => console.log(err))
  }

  const [createOrderModal, setCreateOrderModal] = useState(false)
  const toggleCreateOrderModal = () => setCreateOrderModal(true)
  const [createMenuItemModal, setCreateMenuItemModal] = useState(false)
  const toggleCreateMenuItemModal = () => setCreateMenuItemModal(true)

  useEffect(() => {
    fetchOrder()
    fetchMenuItem()
  }, []);

  return <>
    <CCard className="mb-3">
      <CCardHeader>OrderItem</CCardHeader>
      <CCardBody>
        <CForm>
          <CRow className='g-3'>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='quantity'>Quantity</CFormLabel>
              <CFormInput
                 type='number' name='quantity' placeholder='Quantity' value={data.quantity}
                 onChange={evn => inputHandler(evn, value => data.quantity = value)}/>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='order'>Order</CFormLabel>
              <CInputGroup>
                <CFormSelect
                   name='order' id='order' placeholder='Order' value={data.order.id}
                   onChange={evn => inputHandler(evn, value => data.order.id = value)}>
                  <option>Select Order</option>
                  {orderList.map((it, index) => <option key={index} value={it.id}>{ it.id }</option>)}
                </CFormSelect>
                <CButton color="secondary" type="button" variant="outline" onClick={toggleCreateOrderModal}>
                  <CIcon icon={cilPlus}/>
                </CButton>
              </CInputGroup>
            </CCol>
            <CCol sm={6} md={4}>
              <CFormLabel htmlFor='menuItem'>MenuItem</CFormLabel>
              <CInputGroup>
                <CFormSelect
                   name='menuItem' id='menuItem' placeholder='MenuItem' value={data.menuItem.id}
                   onChange={evn => inputHandler(evn, value => data.menuItem.id = value)}>
                  <option>Select MenuItem</option>
                  {menuItemList.map((it, index) => <option key={index} value={it.id}>{ it.name }</option>)}
                </CFormSelect>
                <CButton color="secondary" type="button" variant="outline" onClick={toggleCreateMenuItemModal}>
                  <CIcon icon={cilPlus}/>
                </CButton>
              </CInputGroup>
            </CCol>
          </CRow>
        </CForm>
      </CCardBody>
    </CCard>
    {createOrderModal &&
      <OrderCreate
        visible={true}
        onCreate={created => {
          inputHandler(created, value => data.order = value, false)
          setOrderList(prev => {
            prev.push(created)
            return prev
          })
          setCreateOrderModal(false)
        }}
        onClose={() => setCreateOrderModal(false)}
      />
    }
    {createMenuItemModal &&
      <MenuItemCreate
        visible={true}
        onCreate={created => {
          inputHandler(created, value => data.menuItem = value, false)
          setMenuItemList(prev => {
            prev.push(created)
            return prev
          })
          setCreateMenuItemModal(false)
        }}
        onClose={() => setCreateMenuItemModal(false)}
      />
    }
  </>
}


const OrderItemCreate = ({ visible, onClose, onCreate }) => {
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
    Services.orderItemService.create(data)
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
        <CModalTitle>Create OrderItem</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <OrderItemCreateForm data={data} inputHandler={inputHandler}/>
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

export default OrderItemCreate