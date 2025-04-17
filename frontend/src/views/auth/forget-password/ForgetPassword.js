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

const ForgetPassword = () => {
  const [email, setEmail] = useState('contact@bshg.com')

  const [resultMessage, setResultMessage] = useState(undefined)

  const validate = async () => {
    Services.authService.forgetPassword(email)
      .then(() => {
        setResultMessage("Check your email to reset your password.")
      })
      .catch(err => {
        setResultMessage(err?.error?.message)
      })
  }

  return (
    <div className="bg-body-tertiary min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol lg={10} xl={8}>
            {resultMessage
              ? <p className="text-center">{resultMessage}</p>
              : <CCard className="p-4">
              <CCardBody>
                <CForm>
                  <h1>You Forgot Your Password?</h1>
                  <p className="text-body-secondary">Enter your email to receive the code to verify your account.</p>
                  <CInputGroup className="mb-3">
                    <CInputGroupText>@</CInputGroupText>
                    <CFormInput
                      placeholder="Email"
                      autoComplete="email"
                      name="email"
                      value={email}
                      onChange={event => setEmail(event.target.value)}
                    />
                  </CInputGroup>
                  <CRow>
                    <CButton color="primary" className="px-4" onClick={validate}>
                      Validate
                    </CButton>
                  </CRow>
                </CForm>
              </CCardBody>
            </CCard>}
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default ForgetPassword
