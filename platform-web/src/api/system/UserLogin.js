import request from '@/utils/request'

const BASE_URL = '/user/login'

export function login(data) {
  return request({
    url: `${BASE_URL}/in`,
    method: 'post',
    responseType: 'json',
    headers: { 'content-Type': 'application/json' },
    data: data
  })
}

export function logout() {
  return request({
    url: `${BASE_URL}/out`,
    method: 'get'
  })
}

