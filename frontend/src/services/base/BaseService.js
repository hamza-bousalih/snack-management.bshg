import env from 'src/env'
import TokenService from "src/services/auth/TokenService";

class ResponseError extends Error {
  error
  constructor(error) {
    super();
    this.error = error
  }
}

const doFetch = async ({ url, method, body = null }) => {
  const requestOptions = {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${TokenService.getToken()}`
    },
    method
  };

  if (body) requestOptions.body = JSON.stringify(body);

  const res = await fetch(url, requestOptions).catch(err => console.error(err));

  if (!res) throw new Error("RESPONSE UNDEFINED!!!");
  if (!res.ok) throw new ResponseError(await res.json());

  return await res.json();
};

class BaseService {
  apiUrl = env.apiUrl

  constructor(resource) {
    this.apiUrl += resource
  }

  async GET(suffix, body = undefined, options = {}) {
    return await doFetch({
        url: this.apiUrl + suffix,
        method: "GET",
        body
      }
    )
  }

  async POST(suffix, body, options = {}) {
    return await doFetch({
      url: this.apiUrl + suffix,
      method: "POST",
      body
    })
  }

  async PUT(suffix, body, options = {}) {
    return await doFetch({
      url: this.apiUrl + suffix,
      method: "PUT",
      body
    })
  }

  async PATCH(suffix, body, options = {}) {
    return await doFetch({
      url: this.apiUrl + suffix,
      method: "PATCH",
      body
    })
  }

  async DELETE(suffix, body = undefined, options = {}) {
    return await doFetch({
      url: this.apiUrl + suffix,
      method: "DELETE",
      body
    })
  }
}

export default BaseService
