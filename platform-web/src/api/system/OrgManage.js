import request from '@/utils/request'

const BASE_URL = '/org'

export function getOrgTree() {
  return request({
    url: `${BASE_URL}/tree`,
    method: 'get'
  })
}
