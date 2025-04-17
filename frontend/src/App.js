import React, {Suspense, useEffect} from 'react'
import {HashRouter, Route, Routes, useNavigation} from 'react-router-dom'
import {useDispatch, useSelector} from 'react-redux'
import {authRoutes} from "src/routes";

import {CSpinner, useColorModes} from '@coreui/react'
import './scss/style.scss'
import Services from "src/services";

// Containers
const DefaultLayout = React.lazy(() => import('./layout/DefaultLayout'))

// Pages
const Page404 = React.lazy(() => import('./views/pages/page404/Page404'))
const Page500 = React.lazy(() => import('./views/pages/page500/Page500'))

const App = () => {
  const { isColorModeSet, setColorMode } = useColorModes('coreui-free-react-admin-template-theme')
  const storedTheme = useSelector((state) => state.theme)
  const storedAuth = useSelector((state) => state.auth)

  const dispatch = useDispatch()
  // const navigate = useNavigation()

  const logout = () => {
    console.log("logout")
    Services.authService.logout()
    // navigate('/auth/login')
  }

  const authValidation = (currentRoute) => {
    currentRoute = currentRoute.split('#')[1]
    if (currentRoute === '/' || currentRoute.startsWith("/auth")) return

    Services.authService.validateToke(storedAuth)
      .then(value => {
        if (value == null) {
          logout();
          return
        }
        dispatch({ type: 'set', auth: value })
      })
      .catch(err => {
        console.log(err)
        logout()
      })
  }

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.href.split('?')[1])
    const theme = urlParams.get('theme') && urlParams.get('theme').match(/^[A-Za-z0-9\s]+/)[0]
    if (theme) {
      setColorMode(theme)
    }

    if (!isColorModeSet()) setColorMode(storedTheme)

    console.log(window.location.href)
    authValidation(window.location.href)
  }, [])

  return (
    <HashRouter>
      <Suspense
        fallback={
          <div className="pt-3 text-center">
            <CSpinner color="primary" variant="grow"/>
          </div>
        }
      >
        <Routes>
          {authRoutes.map((it, index) => <Route key={index} exact path={it.path} name={it.name} element={it.element}/>)}
          <Route exact path="/404" name="Page 404" element={<Page404/>}/>
          <Route exact path="/500" name="Page 500" element={<Page500/>}/>
          <Route path="*" name="Home" element={<DefaultLayout/>}/>
        </Routes>
      </Suspense>
    </HashRouter>
  )
}

export default App
