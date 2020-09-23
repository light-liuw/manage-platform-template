<template>
  <div class="org-tree-container">
    <div class="filter-container">
      <el-input
        v-model="filterText"
        placeholder="输入关键字进行过滤"
        size="mini"
        prefix-icon="el-icon-search"
        clearable
      />
    </div>

    <div :style="{height: realHeight, 'overflow-y': 'scroll', 'margin-top': '5px'}">
      <el-tree
        ref="tree"
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        :default-expand-all="true"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        :highlight-current="true"
        @node-click="handleNodeClick"
      />
    </div>

  </div>
</template>

<script>
import { getOrgTree } from '@/api/system/OrgManage'

export default {
  name: 'OrgTree',
  props: {
    height: {
      type: Number,
      default: 400
    }
  },
  data() {
    return {
      // 树相关配置
      data: [], // 树相关内容
      filterText: '', // 模糊搜索
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  computed: {
    realHeight: function() {
      return this.height - 70 + 'px'
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    // 获取树数据
    this.getOrgTree()
  },
  methods: {
    // 获取树数据
    getOrgTree() {
      getOrgTree().then(response => {
        if (response.code !== '200') {
          this.$notify({
            title: '提示',
            message: response.message,
            type: 'error',
            duration: 2000
          })
        } else if (response.data.length >= 0) {
          this.data = response.data
        }
      })
    },
    // 过滤输入框
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    // 节点点击事件
    handleNodeClick(data) {
      console.log(data)
      this.$emit('toOrgTree', data.code)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .org-tree-container {
    padding: 10px;

    .custom-tree-node {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 14px;
      padding-right: 8px;
    }

    .category-name-not-active {
      color: #d5d5d5;
    }

    .el-button + .el-button {
      margin-left: 0px;
    }

  }
</style>
