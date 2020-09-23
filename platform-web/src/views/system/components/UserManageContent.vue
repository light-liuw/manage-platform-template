<template>
  <div class="user-manage-content-container" :style="{height: realHeight}">

    <!-- 搜索 区域 -->
    <div class="filter-container" style="float:left;">
      <el-input
        v-model="queryData.name"
        placeholder="请输入用户名"
        size="mini"
        style="width: 200px;"
        class="filter-item"
        prefix-icon="el-icon-search"
        clearable
        @keyup.enter.native="userSearch"
      />
      <el-button size="mini" class="filter-item" type="primary" icon="el-icon-search" @click="userSearch">搜索</el-button>
    </div>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="tableData"
      :height="tableHeight"
      ele-nment-loading-text="给我一点时间"
      border
      fit
      highlight-current-row
    >

      <el-table-column align="center" min-width="100px" label="操作" fixed="left">
        <template slot-scope="scope">
          <el-button type="success" @click="userSelectRole(scope.row.id)">修改角色</el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="70px" label="序号" show-overflow-tooltip>
        <template slot-scope="scope">
          <span>{{ (queryData.page - 1) * queryData.pageSize + scope.$index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="用户名">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="身份证号">
        <template slot-scope="scope">
          <span>{{ scope.row.idCard }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="性别">
        <template slot-scope="scope">
          <span>{{ scope.row.sex | userSex }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="状态">
        <template slot-scope="scope">
          <span>{{ scope.row.status | userStatus }}</span>
        </template>
      </el-table-column>

      <!--<el-table-column align="center" min-width="100px" label="所属机构名称">
        <template slot-scope="scope">
          <span>{{ scope.row.orgName }}</span>
        </template>
      </el-table-column>-->

      <!--<el-table-column align="center" min-width="100px" label="角色">
        <template slot-scope="scope">
          <span>{{ scope.row.roleName }}</span>
        </template>
      </el-table-column>-->

    </el-table>

    <!-- 分页 -->
    <div v-show="!listLoading" class="pagination-container" style="margin-top: 8px;">
      <el-pagination
        :current-page.sync="queryData.page"
        :page-size="queryData.pageSize"
        :total="total"
        :page-sizes="[10,20,30,50]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!--    <change-role-->
    <!--      :dialog-form-visible="dialogFormVisible"-->
    <!--      :user-code-data="codeData"-->
    <!--      @toChangeRole="fromChangeRole"-->
    <!--    />-->

  </div>
</template>

<script>
import { fetchGetUserByPage } from '../../../api/system/UserManage'
// import ChangeRole from './UserChangeRole'
export default {
  name: 'UserManageContent',
  components: {
    // ChangeRole
  },
  props: {
    height: {
      type: Number,
      default: 400
    },
    orgCode: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dialogFormVisible: false,
      codeData: '',
      queryData: {
        orgCode: '',
        name: '',
        page: 1,
        pageSize: 10,
        orderColumn: '',
        orderDir: ''
      },
      listLoading: false, // 加载动画
      tableData: [],
      total: 0
    }
  },
  computed: {
    realHeight: function() {
      return this.height - 100 + 'px'
    },
    tableHeight: function() {
      return this.height - 100
    }
  },
  watch: {
    orgCode() {
      this.orgCodeChange()
    }
  },
  created() {
    this.getUserByPage()
  },
  methods: {
    // 分页查询用户
    getUserByPage() {
      this.listLoading = true
      this.tableData = []
      fetchGetUserByPage(this.queryData).then(response => {
        if (response.code !== '200') {
          this.$notify({
            title: '提示',
            message: response.message,
            type: 'error',
            duration: 2000
          })
        } else if (response.data.list.length >= 0) {
          this.tableData = response.data.list
          this.total = response.data.total
        }
        this.listLoading = false
      })
    },
    orgCodeChange() {
      this.queryData.orgCode = this.orgCode
      this.getUserByPage()
    },
    userSearch() {
      this.getUserByPage()
    },
    // 修改用户角色
    userSelectRole(value) {
      this.codeData = value
      this.dialogFormVisible = true
    },
    handleSizeChange(val) {
      this.queryData.pageSize = val
      this.queryData.page = 1
      this.getUserByPage()
    },
    handleCurrentChange(val) {
      this.queryData.page = val
      this.getUserByPage()
    },
    fromChangeRole(value) {
      if (value === 'submit') {
        this.getUserByPage()
      }
      this.dialogFormVisible = false
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .user-manage-content-container {}
</style>

