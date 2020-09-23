<template>
    <div>
      <el-dialog :visible.sync="dialogFormVisible" title="修改用户角色" width="40%" :before-close="handleCancelForm">
        <div style="margin-left: 10%">
          <el-form ref="roleData" :model="roleData" label-width="100px" label-position="left">
            <el-row>
              <el-col :span="20">
                <el-form-item label="姓名"  >
                  <el-input v-model="roleData.userName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="身份证号" >
                  <el-input v-model="roleData.userCode" :disabled="true"></el-input>

                </el-form-item>
              </el-col>
              <el-col :span="20">
                <el-form-item label="职务">
                  <el-input v-model="roleData.userJobDesc" :disabled="true"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="20">
                <el-form-item label="所属机构名称">
                  <el-input v-model="roleData.orgName" :disabled="true"></el-input>
                </el-form-item>
              </el-col>

              <el-col>
                <el-form-item label="角色">
                  <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                  <div style="margin: 15px 0;"></div>
                  <el-checkbox-group v-model="checkedRoles" @change="handleCheckedRolesChange">
                    <el-checkbox v-for="(item,index) in roles" :label="item" :key="index"></el-checkbox>
                  </el-checkbox-group>
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button @click="handleCancelForm">取消</el-button>
          <el-button type="primary" @click="handleForm" >确认</el-button>

        </div>
      </el-dialog>
    </div>
</template>

<script>
  import { fetchgetRoleByUserCode, fecthUpdateRole } from '../../../api/system/shgauser'
  import { fetchgetRoleName } from '../../../api/system/roles'
  export default {
    name: 'ChangeRole',
    props: {
      dialogFormVisible: {
        type: Boolean,
        default: false
      },
      userCodeData: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        roleData: {
          userName: '',
          userCode: '',
          rolesList: []
        },
        // 用户角色全选
        checkAll: false,
        // 选中的角色
        checkedRoles: [],
        // 全部角色名称
        roles: [],
        // 全部角色数据
        rolesData: [],
        // 全选样式
        isIndeterminate: false

      }
    },
    watch: {
      dialogFormVisible(val) {
        if (val === true) {
          this.getSelectRole()
        }
      }
    },
    created() {
      this.getRoleName()
    },
    methods: {
      resetData() {
        this.checkAll = false
        this.checkedRoles = []
        this.isIndeterminate = false
      },
      // 获取全部角色数据
      getRoleName() {
        fetchgetRoleName().then(response => {
          const meta = response.data.meta
          if (meta.status !== 'success') {
            this.$notify({
              title: '提示',
              message: '操作失败',
              type: 'error',
              duration: 2000
            })
          } else {
            // this.roles
            const roles = response.data.body.data
            roles.forEach(element => {
              // set 全部角色名称
              this.roles.push(element.roleName)
              // set 全部角色数据
              const role = {}
              role.id = element.id
              role.roleNo = element.roleNo
              role.roleName = element.roleName
              this.rolesData.push(role)
            })
          }
        })
      },
      // 获取用户角色信息
      getSelectRole() {
        fetchgetRoleByUserCode(this.userCodeData).then(response => {
          console.log(response)
          const meta = response.data.meta
          if (meta.status !== 'success') {
            this.$notify({
              title: '提示',
              message: '操作失败',
              type: 'error',
              duration: 2000
            })
          } else {
            // 用户信息
            this.roleData = response.data.body.data
            // 初始化用户角色信息
            if (this.roleData.roleId !== undefined && this.roleData.roleId !== null && this.roleData.roleId !== '') {
              this.checkedRoles = this.roleData.roleName.split(',')
              const checkedCount = this.checkedRoles.length
              this.checkAll = checkedCount === this.roles.length
              this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length
            }
          }
        })
      },
      // 全选
      handleCheckAllChange(val) {
        this.checkedRoles = val ? this.roles : []
        this.isIndeterminate = false
        this.roleData.rolesList = this.checkedRoles
      },
      // 选择
      handleCheckedRolesChange(value) {
        var checkedCount = value.length
        this.checkAll = checkedCount === this.roles.length
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roles.length
        this.roleData.rolesList = value
      },
      // 取消 弹框
      handleCancelForm() {
        this.resetData()
        this.$emit('toChangeRole', 'cancel')
      },
      handleForm() {
        // 处理选中角色
        this.roleData.rolesList = []
        this.rolesData.forEach(element => {
          this.checkedRoles.forEach(elementName => {
            if (elementName === element.roleName) {
              this.roleData.rolesList.push(element)
            }
          })
        })
        fecthUpdateRole(this.roleData).then(response => {
          const meta = response.data.meta
          if (meta.status !== 'success') {
            this.$notify({
              title: '提示',
              message: '操作失败',
              type: 'error',
              duration: 2000
            })
          } else {
            this.$notify({
              title: '提示',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
            this.$emit('toChangeRole', 'submit')
            this.resetData()
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
