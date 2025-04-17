export default {
  getToken: () => localStorage.getItem("token"),

  setToken: (token) => localStorage.setItem("token", token),

  clearToken: () => localStorage.removeItem("token"),

  isLoggedIn: () => !!this.getToken()
}
