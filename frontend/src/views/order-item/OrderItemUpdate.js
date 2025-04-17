import React, {useState, useEffect} from "react";
import {
  CButton, CCard, CCardBody, CCardHeader, CCol, CForm, CFormInput, CFormTextarea,
  CFormLabel, CFormSelect, CInputGroup, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle, CRow
} from "@coreui/react";
import {cilPlus} from "@coreui/icons";
import CIcon from "@coreui/icons-react";
import SpinnerButton from "src/components/common/spinner-button/spinner-button.component";
import Services from "src/services";
import OrderUpdate from "src/views/order/OrderUpdate";
import MenuItemUpdate from "src/views/menu-item/MenuItemUpdate";


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
                   name='order' id='order' placeholder='Order' value={data.order?.id}
                   onChange={evn => inputHandler(evn, value => data.order.id = value)}>
                  <option>Select Order</option>
                  {orderList.map((it, index) => <option key={index} value={it?.id}>{ it.id }</option>)}
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
                   name='menuItem' id='menuItem' placeholder='MenuItem' value={data.menuItem?.id}
                   onChange={evn => inputHandler(evn, value => data.menuItem.id = value)}>
                  <option>Select MenuItem</option>
                  {menuItemList.map((it, index) => <option key={index} value={it?.id}>{ it.name }</option>)}
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
      <OrderUpdate
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
      <MenuItemUpdate
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


const OrderItemUpdate = ({ visible, onClose, onUpdate, id }) => {
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
    Services.orderItemService.findById(id)
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
    Services.orderItemService.update(data)
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
        <CModalTitle>Update OrderItem</CModalTitle>
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
          onClick={update}
          label='Update'
        />
      </CModalFooter>
    </CModal>
  </>
}

export default OrderItemUpdate