import React, {useState} from 'react'
import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow
} from '@coreui/react'
import Services from "src/services";
import {handleInput} from "src/utils";
import {useNavigate} from "react-router";
import CIcon from "@coreui/icons-react";
import {cilLockLocked} from "@coreui/icons";
import {Link, useParams} from "react-router-dom";

const ForgetPassword = () => {
  const [passwordObj, setPasswordObj] = useState({
    password: '',
    confirmPassword: '',
  })

  const navigate = useNavigate()
  const params = useParams()

  const inputHandler = (event) => {
    handleInput(event, setPasswordObj)
  }

  const changePassword = async () => {
    Services.authService.changePassword({
      ...passwordObj,
      token: params.token
      })
      .then((res) => {
        console.log(res)
        navigate("/auth/login")
      })
      .catch(err => console.log(err))
  }

  return (
    <div className="bg-body-tertiary min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol lg={10} xl={8}>
            <CCard>
              <CCardBody>
                <CForm>
                  <h1>Change Password</h1>
                  <p className="text-body-secondary">Enter your new password.</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupText><CIcon icon={cilLockLocked}/></CInputGroupText>
                    <CFormInput type="password" placeholder="Password" name="password"
                                value={passwordObj.password} onChange={inputHandler}/>
                  </CInputGroup>
                  <CInputGroup className="mb-4">
                    <CInputGroupText><CIcon icon={cilLockLocked}/></CInputGroupText>
                    <CFormInput type="password" placeholder="Repeat password" name="confirmPassword"
                                value={passwordObj.confirmPassword} onChange={inputHandler}/>
                  </CInputGroup>
                  <div className="d-grid">
                    <CButton color="primary" className="px-4" onClick={changePassword}>Change Password</CButton>
                  </div>
                  <Link to="/auth/login">
                    <CButton color="link" className="text-center" style={{ width: '100%' }} active tabIndex={-1}>
                      Return To Login Page?
                    </CButton>
                  </Link>
                </CForm>
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default ForgetPassword
