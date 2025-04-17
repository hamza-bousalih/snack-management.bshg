import {useState} from 'react'
import {Link} from 'react-router-dom'
import {useNavigate} from 'react-router'
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import {cilLockLocked, cilUser} from '@coreui/icons'
import Services from "src/services";
import {useDispatch, useSelector} from "react-redux";
import {handleInput} from 'src/utils'
import TokenService from "src/services/auth/TokenService";

const Login = () => {
  const [loginObj, setLoginObj] = useState({
    email: 'contact@bshg.com',
    password: '123456'
  })

  const dispatch = useDispatch()
  const storedAuth = useSelector((state) => state.auth)
  const navigate = useNavigate()

  const inputHandler = (event) => {
    handleInput(event, setLoginObj)
  }

  const login = async () => {
    Services.authService.login(loginObj)
      .then((res) => {
        dispatch({ type: 'set', auth: res })
        TokenService.setToken(res.token)
        navigate("/")
      })
      .catch(err => console.log(err))
  }

  return (
    <div className="bg-body-tertiary min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol lg={10} xl={8}>
            <CCardGroup>
              <CCard className="p-4">
                <CCardBody>
                  <CForm>
                    <h1>Login</h1>
                    <p className="text-body-secondary">Sign In to your account</p>
                    <CInputGroup className="mb-3">
                      <CInputGroupText>
                        <CIcon icon={cilUser}/>
                      </CInputGroupText>
                      <CFormInput
                        placeholder="Email"
                        autoComplete="email"
                        name="email"
                        value={loginObj.email}
                        onChange={inputHandler}
                      />
                    </CInputGroup>
                    <CInputGroup className="mb-4">
                      <CInputGroupText>
                        <CIcon icon={cilLockLocked}/>
                      </CInputGroupText>
                      <CFormInput
                        type="password"
                        name="password"
                        placeholder="Password"
                        autoComplete="current-password"
                        value={loginObj.password}
                        onChange={inputHandler}
                      />
                    </CInputGroup>
                    <CRow>
                      <CCol xs={6}>
                        <CButton color="primary" className="px-4" onClick={login}>
                          Login
                        </CButton>
                      </CCol>
                      <CCol xs={6} className="text-right">
                        <Link to="/auth/forget-password">
                          <CButton color="link" className="px-0">
                            Forgot password?
                          </CButton>
                        </Link>
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
              <CCard className="text-white bg-primary py-5" style={{ minWidth: '44%' }}>
                <CCardBody className="text-center">
                  <div>
                    <h2>Sign up</h2>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                      tempor incididunt ut labore et dolore magna aliqua.
                    </p>
                    <Link to="/auth/register">
                      <CButton color="primary" className="mt-3" active tabIndex={-1}>
                        Register Now!
                      </CButton>
                    </Link>
                  </div>
                </CCardBody>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Login
