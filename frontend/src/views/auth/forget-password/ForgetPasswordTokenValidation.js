import {useEffect, useState} from 'react'
import {useNavigate} from 'react-router'
import {CCol, CContainer, CRow, CSpinner} from '@coreui/react'
import {useParams} from "react-router-dom";
import Services from "src/services";

const ForgetPasswordTokenValidation = () => {
  const [errorMsg, setErrorMsg] = useState(undefined)
  const navigate = useNavigate()
  const params = useParams()

  useEffect(() => {
    Services.authService.validateTheCode(params.token)
      .then((res) => {
        navigate(`/auth/forget-password/reset/${res.data}`)
      })
      .catch(err => setErrorMsg(err.error.message))
  }, [])

  return (
    <div className="bg-body-tertiary min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={8} className="d-flex align-items-center justify-content-center gap-2">
            {!errorMsg
              ? <><CSpinner/> Verifying...</>
              : `Ooops: ${errorMsg}`
            }
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
};

export default ForgetPasswordTokenValidation
