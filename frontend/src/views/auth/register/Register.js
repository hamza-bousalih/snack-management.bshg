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
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import {cilLockLocked, cilPhone, cilUser} from '@coreui/icons'
import {handleInput} from "src/utils";
import {Link} from "react-router-dom";
import Services from "src/services";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router";

const Register = () => {
  const [registerObj, setRegisterObj] = useState({
    firstname: '',
    lastname: '',
    username: '',
    password: '',
    confirmPassword: '',
    email: '',
    phone: ''
  })

  const [registered, setRegistered] = useState(false)

  const dispatch = useDispatch()
  const storedAuth = useSelector((state) => state.auth)
  const navigate = useNavigate()

  const inputHandler = (event) => {
    handleInput(event, setRegisterObj)
  }

  const register = async () => {
    console.log(registerObj)
    Services.authService.register(registerObj)
      .then((res) => {
        setRegistered(true)
      })
      .catch(err => console.log(err))
  }

  return (
    <div className="bg-body-tertiary min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={9} lg={7} xl={6}>
            <CCard className="mx-4">
              <CCardBody className="p-4">
                {registered
                  ? 'Your Account is created successfully, now check your email to activate your account.'
                  : <CForm>
                    <h1>Register</h1>
                    <p className="text-body-secondary">Create your account</p>
                    <CRow>
                      <CCol>
                        <CInputGroup className="mb-3">
                          <CInputGroupText><CIcon icon={cilUser}/></CInputGroupText>
                          <CFormInput placeholder="First Name" name='firstname' value={registerObj.firstname}
                                      onChange={inputHandler}/>
                        </CInputGroup>
                      </CCol>
                      <CCol>
                        <CInputGroup className="mb-3">
                          <CInputGroupText><CIcon icon={cilUser}/></CInputGroupText>
                          <CFormInput placeholder="Last Name" name='lastname' value={registerObj.lastname}
                                      onChange={inputHandler}/>
                        </CInputGroup>
                      </CCol>
                    </CRow>
                    <CInputGroup className="mb-3">
                      <CInputGroupText><CIcon icon={cilUser}/></CInputGroupText>
                      <CFormInput placeholder="Username" name="username" value={registerObj.username}
                                  onChange={inputHandler}/>
                    </CInputGroup>
                    <CInputGroup className="mb-3">
                      <CInputGroupText>@</CInputGroupText>
                      <CFormInput placeholder="Email" name="email" value={registerObj.email} onChange={inputHandler}/>
                    </CInputGroup>
                    <CInputGroup className="mb-3">
                      <CInputGroupText><CIcon icon={cilPhone}/></CInputGroupText>
                      <CFormInput placeholder="Phone" name="phone" value={registerObj.phone} onChange={inputHandler}/>
                    </CInputGroup>
                    <CInputGroup className="mb-3">
                      <CInputGroupText><CIcon icon={cilLockLocked}/></CInputGroupText>
                      <CFormInput type="password" placeholder="Password" name="password" value={registerObj.password}
                                  onChange={inputHandler}/>
                    </CInputGroup>
                    <CInputGroup className="mb-4">
                      <CInputGroupText><CIcon icon={cilLockLocked}/></CInputGroupText>
                      <CFormInput type="password" placeholder="Repeat password" name="confirmPassword"
                                  value={registerObj.confirmPassword} onChange={inputHandler}/>
                    </CInputGroup>
                    <div className="d-grid">
                      <CButton color="success" onClick={register}>Create Account</CButton>
                    </div>
                    <Link to="/auth/login">
                      <CButton color="link" className="mt-3 text-center" active tabIndex={-1}>
                        Already Have Account?
                      </CButton>
                    </Link>
                  </CForm>}
              </CCardBody>
            </CCard>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Register
