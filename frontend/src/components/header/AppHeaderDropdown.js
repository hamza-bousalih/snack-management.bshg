import React from 'react'
import {
  CAvatar,
  CBadge,
  CDropdown,
  CDropdownDivider,
  CDropdownHeader,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
} from '@coreui/react'
import {
  cilAccountLogout,
  cilBell,
  cilCommentSquare,
  cilCreditCard,
  cilEnvelopeOpen,
  cilFile,
  cilLockLocked,
  cilSettings,
  cilTask,
  cilUser,
} from '@coreui/icons'
import CIcon from '@coreui/icons-react'
import {useSelector} from "react-redux";
import Services from "src/services";
import {useNavigate} from "react-router";

const AppHeaderDropdown = () => {
  const storedAuth = useSelector((state) => state.auth)
  const navigate = useNavigate()

  const avatar = () => {
    const username = storedAuth?.username;
    if (!username) return ""
    return username.split(' ')
      .map(it => it[0])
      .splice(0, 2)
      .reduce((prev = "", cur) => prev + cur)
      .toUpperCase()
  }

  const menu = [
    {
      type: 'group',
      title: 'Account',
      items: [
        { label: 'Updates', icon: cilBell, href: '#', badge: { label: 42, color: 'info' }, },
        { label: 'Messages', icon: cilEnvelopeOpen, href: '#', badge: { label: 42, color: 'success' }, },
        { label: 'Tasks', icon: cilTask, href: '#', badge: { label: 42, color: 'danger' }, },
        { label: 'Comments', icon: cilCommentSquare, href: '#', badge: { label: 42, color: 'warning' }, },
      ]
    },
    {
      type: 'group',
      title: 'Settings',
      items: [
        { label: 'Profile', icon: cilUser, href: '#' },
        { label: 'Settings', icon: cilSettings, href: '#' },
        { label: 'Payments', icon: cilCreditCard, href: '#', badge: { label: 42, color: 'secondary' }, },
        { label: 'Projects', icon: cilFile, href: '#', badge: { label: 42, color: 'primary' }, },
      ]
    },
    { type: 'divider' },
    {
      type: 'group',
      items: [
        { label: 'Lock Account', icon: cilLockLocked, href: '#' },
        {
          label: 'Log Out',
          icon: cilAccountLogout,
          href: '/#/auth/login',
          click: () => Services.authService.logout()
        },
      ]
    },
  ].map((element, index) => {
    return {
      ... element,
      items: element.items && element.items.map((item, itemIndex) => {
        return {
          ... item,
          key: `${index}:${itemIndex}`
        }
      }),
      key: `${index}`
    }
  })

  return (
    <CDropdown variant="nav-item">
      <CDropdownToggle placement="bottom-end" className="py-0 pe-0" caret={false}>
        <CAvatar size="md" status='success' textColor="white" color="primary">{avatar()}</CAvatar>
      </CDropdownToggle>
      <CDropdownMenu className="pt-0" placement="bottom-end">
        {menu.map(element => element.type === 'group'
          ? <div key={element.key}>
            {element.title &&
              <CDropdownHeader className="bg-body-secondary fw-semibold mb-2">{element.title}</CDropdownHeader>}
            {element.items && element.items.map(item =>
              <CDropdownItem key={item.key} href={item.href} onClick={item.click}>
                <CIcon icon={item.icon} className="me-2"/>
                {item.label}
                {item.badge && <CBadge color={item.badge.color} className="ms-2">{item.badge.label}</CBadge>}
              </CDropdownItem>
            )}
          </div>
          : element.type === 'divider'
            ? <CDropdownDivider key={element.key}/>
            : <></>
        )}
      </CDropdownMenu>
    </CDropdown>
  )
}

export default AppHeaderDropdown
