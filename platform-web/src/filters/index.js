import Vue from 'vue'

// 用户性别格式化
Vue.filter('userSex', function(data) {
  if (data !== null && undefined !== data) {
    data = data === '0' ? '女' : data === '1' ? '男' : ''
  }
  return data
})

// 用户状态格式化
Vue.filter('userStatus', function(data) {
  if (data !== null && undefined !== data) {
    data = data === '0' ? '停用' : data === '1' ? '启用' : ''
  }
  return data
})
