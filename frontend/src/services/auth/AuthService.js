import BaseService from 'src/services/base/BaseService'
import env from 'src/env'
import tokenService from "src/services/auth/TokenService";

export default class AuthService extends BaseService {
  constructor() {
    super();
    this.apiUrl = env.auth
  }

  async login(body) {
    return this.POST(`/login`, body);
  }

  async register(body) {
    return this.POST(`/register`, body);
  }

  async activateAccount(code) {
    return this.POST(`/activate-account?token=${code}`, {})
  }

  async forgetPassword(email) {
    return this.POST(`/forget-password/send-token?email=${email}`, {})
  }

  async validateTheCode(code) {
    return this.POST(`/forget-password/validate-token?token=${code}`, {})
  }

  async changePassword(newPasswordObj) {
    return this.POST(`/forget-password/reset`, newPasswordObj)
  }

  async validateToke(currentAuth) {
    const token = tokenService.getToken()
    if (!token) {
      await this.logout()
      return undefined
    }
    if (currentAuth.email) return currentAuth
    return this.POST(`/validate-token`, {token});
  }

  logout() {
    tokenService.clearToken()
  }
}
