import React from 'react'
import CIcon from '@coreui/icons-react'
import { cilBolt, cilSpeedometer } from '@coreui/icons'
import { CNavGroup, CNavItem } from '@coreui/react'

const _nav = [
  {
    component: CNavItem,
    name: 'Dashboard',
    to: '/dashboard',
    icon: <CIcon icon={cilSpeedometer} customClassName="nav-icon" />,
    badge: { color: 'info', text: 'NEW' },
  },
  {
    component: CNavGroup,
    name: '',
    icon: <CIcon icon={cilBolt} customClassName="nav-icon" />,
    to: '/',
    items: [
      {
        component: CNavItem,
        name: ' User',
        to: '/user',
        icon: <CIcon icon={cilBolt} customClassName="nav-icon" />,
      },
      {
        component: CNavItem,
        name: ' Table',
        to: '/table',
        icon: <CIcon icon={cilBolt} customClassName="nav-icon" />,
      },
      {
        component: CNavItem,
        name: ' Order',
        to: '/order',
        icon: <CIcon icon={cilBolt} customClassName="nav-icon" />,
      },
      {
        component: CNavItem,
        name: ' Bill',
        to: '/bill',
        icon: <CIcon icon={cilBolt} customClassName="nav-icon" />,
      },
      {
        component: CNavItem,
        name: ' Menu Item',
        to: '/menu-item',
        icon: <CIcon icon={cilBolt} customClassName="nav-icon" />,
      },
    ],
  },
]

export default _nav
