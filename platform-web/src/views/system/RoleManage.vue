<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      :span-method="objectSpanMethod"
    >
      <el-table-column align="center" label="序号" width="0">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="id" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="name" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="bId" min-width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.bId }}
        </template>
      </el-table-column>
      <el-table-column label="class" min-width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.class }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-button @click="fetchData(1)"> 第一页 </el-button>
    <el-button @click="fetchData(2)"> 第二页 </el-button>
  </div>
</template>

<script>
import { getList } from '@/api/table'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      listLoading: true,
      rowIndex: '-1',
      orderIndexArr: [],
      hoverOrderArr: []
    }
  },
  created() {
    this.fetchData(1)
  },
  methods: {
    fetchData(page) {
      if (page === 1) {
        this.list = [
          { id: 1, name: 'A', bId: 1, class: '语文' },
          { id: 2, name: 'A', bId: 2, class: '语文' },
          { id: 3, name: 'A', bId: 3, class: '语文' },
          { id: 4, name: 'A', bId: 4, class: '语文' },
          { id: 5, name: 'A', bId: 5, class: '语文' },
          { id: 6, name: 'A', bId: 6, class: '语文' },
          { id: 7, name: 'A', bId: 7, class: '语文' },
          { id: 8, name: 'A', bId: 8, class: '语文' },
          { id: 9, name: 'A', bId: 9, class: '语文' },
          { id: 10, name: 'A', bId: 10, class: '语文' }
        ]
      } else {
        this.list = [
          { id: 11, name: 'A', bId: 11, class: '语文' },
          { id: 12, name: 'A', bId: 12, class: '语文' },
          { id: 13, name: 'B', bId: 13, class: '语文' },
          { id: 14, name: 'B', bId: 14, class: '语文' },
          { id: 15, name: 'C', bId: 17, class: '语文' }
        ]
      }
      this.rowIndex = '-1'
      this.orderIndexArr = []
      this.hoverOrderArr = []
      this.getOrderNumber()
      this.listLoading = false
    },
    // 获取相同编号的数组
    getOrderNumber() {
      const orderObj = {}
      this.list.forEach((item, index) => {
        item.rowIndex = index + 1
        if (orderObj[item.name]) {
          orderObj[item.name].push(index)
        } else {
          orderObj[item.name] = []
          orderObj[item.name].push(index)
        }
      })
      // 将数组长度大于1的值 存储到this.orderIndexArr（也就是需要合并的项）
      Object.keys(orderObj).forEach((key) => {
        if (orderObj[key].length > 1) {
          this.orderIndexArr.push(orderObj[key])
        }
      })
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 2) {
        for (let i = 0; i < this.orderIndexArr.length; i += 1) {
          const element = this.orderIndexArr[i]
          for (let j = 0; j < element.length; j += 1) {
            const item = element[j]
            if (rowIndex === item) {
              if (j === 0) {
                return {
                  rowspan: element.length,
                  colspan: 1
                }
              }
              if (j !== 0) {
                return {
                  rowspan: 0,
                  colspan: 0
                }
              }
            }
          }
        }
      }
    }
  }
}
</script>
