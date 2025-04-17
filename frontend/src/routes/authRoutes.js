import React from 'react'

const Login = React.lazy(() => import('src/views/auth/login/Login'))
const Register = React.lazy(() => import('src/views/auth/register/Register'))
const ActivateAccount = React.lazy(() => import('src/views/auth/activate-account/ActivateAccount'))
const ForgetPassword = React.lazy(() => import('src/views/auth/forget-password/ForgetPassword'))
const ForgetPasswordTokenValidation = React.lazy(() => import('src/views/auth/forget-password/ForgetPasswordTokenValidation'))
const ResetPassword = React.lazy(() => import('src/views/auth/forget-password/ResetPassword'))

export default [
  { path: '/login', name: 'Login Page', element: <Login/> },
  { path: '/register', name: 'Register Page', element: <Register/> },
  { path: '/activate-account/:token', name: 'Activate Account', element: <ActivateAccount/> },
  { path: '/forget-password', name: 'Forget Password', element: <ForgetPassword/> },
  { path: '/forget-password/code/:token', name: 'Validate ForgetPassword Token', element: <ForgetPasswordTokenValidation/> },
  { path: '/forget-password/reset/:token', name: 'Reset Password Token', element: <ResetPassword/> },
].map(it => {
  return {...it, path: `/auth${it.path}`}
})
