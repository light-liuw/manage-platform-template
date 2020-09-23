import request from '@/utils/request'

const BASE_URL = '/user'

export function getUserInfoByUsername(token) {
  return request({
    url: `${BASE_URL}/login/name/` + token,
    method: 'get',
    responseType: 'json',
    headers: { 'content-Type': 'application/json' }
  })
}

export function fetchGetUserByPage(data) {
  return request({
    url: `${BASE_URL}/page`,
    method: 'post',
    responseType: 'json',
    headers: { 'content-Type': 'application/json' },
    data: data
  })
}
