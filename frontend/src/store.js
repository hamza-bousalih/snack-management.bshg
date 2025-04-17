import {legacy_createStore as createStore} from 'redux'

const initialState = {
  sidebarShow: true,
  theme: 'light',
  auth: {
    id: undefined,
    email: undefined,
    token: undefined,
    username: undefined,
    roles: undefined
  }
}

const changeState = (state = initialState, { type, ... rest }) => {
  switch (type) {
    case 'set':
      return { ... state, ... rest }
    default:
      return state
  }
}

const store = createStore(changeState)

export default store
