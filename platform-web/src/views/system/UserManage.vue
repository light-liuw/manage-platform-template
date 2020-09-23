<template>
  <div id="user-manage" class="user-manage-container">

    <el-row>
      <el-col :span="6">
        <div :style="{height:contentHeight}" class="left-tree">
          <org-tree :height="tableHeight" @toOrgTree="fromOrgTree" />
        </div>
      </el-col>

      <el-col :span="18">
        <div :style="{height:contentHeight}" class="right-content">
          <user-manage-content :height="tableHeight" :org-code="orgCode" />
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import OrgTree from './components/OrgTree.vue'
import UserManageContent from './components/UserManageContent.vue'

export default {
  components: {
    UserManageContent,
    OrgTree
  },
  data() {
    return {
      // 基本配置
      tableHeight: 0,
      contentHeight: '',
      // 组织机构id
      orgCode: ''
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.tableHeight = document.getElementById('app-main').clientHeight - document.getElementById('user-manage').offsetTop - 60
      this.contentHeight = this.tableHeight + 'px'
    })
  },
  methods: {
    // 监听左侧目录树
    fromOrgTree(value) {
      this.orgCode = value
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .user-manage-container {
    height: calc(100vh - 50px);
    padding: 30px;
    background-color: rgb(240, 242, 245);

    .chart-wrapper {
      background: #fff;
      padding: 15px 15px 0;
    }

    .left-tree {
      border-right: 1px #e6e6e6 solid;
      background-color: white;
    }

    .right-content {
      padding: 10px;
      background-color: white;
    }
  }
</style>
