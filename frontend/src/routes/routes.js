import React from 'react'

const Dashboard = React.lazy(() => import('src/views/dashboard/Dashboard'))

const UserList = React.lazy(() =>  import('src/views/user/UserList'))
const TableList = React.lazy(() =>  import('src/views/table/TableList'))
const OrderItemList = React.lazy(() =>  import('src/views/order-item/OrderItemList'))
const OrderList = React.lazy(() =>  import('src/views/order/OrderList'))
const BillList = React.lazy(() =>  import('src/views/bill/BillList'))
const MenuItemList = React.lazy(() =>  import('src/views/menu-item/MenuItemList'))

export default [
  { path: '/', exact: true, name: 'Home' },
  { path: '/dashboard', name: 'Dashboard', element: Dashboard },
  { path: '/user', name: 'User', element: UserList },
  { path: '/table', name: 'Table', element: TableList },
  { path: '/orderItem', name: 'OrderItem', element: OrderItemList },
  { path: '/order', name: 'Order', element: OrderList },
  { path: '/bill', name: 'Bill', element: BillList },
  { path: '/menuItem', name: 'MenuItem', element: MenuItemList },
]
