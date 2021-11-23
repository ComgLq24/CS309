<template>
  <div>
    <dragTreeTable
        ref="table"
        :data="{columns: this.colprop,lists: this.transData}"
        @drag="onTreeDataChange"
        resize
        fixed
        :isdraggable="true">
      <template #selection="{row}">
        {{ row.name }}
      </template>
      <template #id="{row}">
        {{ row.id }}
      </template>
      <template #priority="{row}">
        {{ row.priority }}
      </template>
      <template #status="{row}">
        {{ row.status }}
      </template>
      <template #action="{row}">
        <a class="action-item" @click.stop.prevent="emitTaskId(row)">查看任务详细信息</a>
      </template>
    </dragTreeTable>
  </div>
</template>

<script>
import dragTreeTable from "drag-tree-table";
import axios from 'axios'
export default {
  name: "DragTaskTree",
  props:['taskData'],
  components:{
    dragTreeTable
  },
  watch:{
    'taskData':function() {
      this.transData = this.transferData(this.taskData)
    }
  },
  data() {
    return {
      colprop:[
        {
          type: "selection",
          title: "<a>任务名称</a>",
          field: "name",
          width: 200,
          align: "left",
          titleAlign: "left",
        },
        {
          title: "任务ID",
          type: "id",
          width: 100,
          align: "center"
        },
        {
          title:"任务优先级",
          field:'priority',
          width:100,
          align: 'center'
        },
        {
          title:"任务状态",
          field:"status",
          width:100,
          align:'center'
        },
        {
          title: "Action",
          type: "action",
          flex: 1,
          align: "center",
        },
      ],
      transData:this.transferData(this.taskData)
    }
  },
  methods:{
    transferData(task){
      let transferList = []
      for(let i in task){
        let tmp = {
          id: task[i].taskId,
          parent_id: task[i].fatherTask,
          order: 0,
          name: task[i].taskName,
          priority:task[i].privilege,
          status:task[i].status,
          lists: this.transferData(task[i].subTasks),
        }
        transferList.push(tmp)
      }
      return transferList
    },
    onTreeDataChange(list) {
      let newEdges = this.findNewEdge(this.transData,list)
      const that = this
      for (let i in newEdges) {
        let newEdge = newEdges[i]
        let fatherId = parseInt(newEdge.split('-')[0])
        let childId = parseInt(newEdge.split('-')[1])
        axios.post(
            'task/addsubtask',
            {
              fatherTask:fatherId,
              subTask:childId
            },
            {
              headers:{
                Authorization:window.sessionStorage.getItem('token')
              }
            }
        ).then(
            function(response) {
              if(response.data.code === 200) {
                that.$message({
                  message:'添加子任务成功',
                  type:'success'
                })
                // 重新请求任务数据
              } else {
                that.$message.error('添加子任务失败')
              }
            },
            function(err) {
              that.$message.error('响应失败，添加子任务失败')
            }
        )
      }
      that.$emit('postTaskDataAgain',{})
    },
    getEdgeSet(father) {
      if(father.lists.length === 0) {
        return []
      }
      let edgeSet = []
      for(let i in father.lists) {
        let child = father.lists[i]
        edgeSet.push(father.id + '-' + child.id)
        let edges = this.getEdgeSet(child)
        if(!(edges.length === 0)) {
          edgeSet.push.apply(edgeSet, edges)
        }
      }
      return edgeSet
    },
    getForestEdgeSet(list) {
      let edgeSet = []
      for (let i in list) {
        edgeSet.push.apply(edgeSet, this.getEdgeSet(list[i]))
      }
      return edgeSet
    },
    findNewEdge(oldList, newList) {
      let edges1 = this.getForestEdgeSet(oldList)
      let edges2 = this.getForestEdgeSet(newList)
      for(let i in edges1) {
        let oldEdge = edges1[i]
        let index = edges2.indexOf(oldEdge)
        if(index > -1) {
          edges2.splice(index, 1)
        }
      }
      return edges2
    },
    emitTaskId(row) {
      this.$emit('taskIdChanged', this.getIdByRow(row.id))
    },
    getIdByRow(rowId) {
      for(let i in this.taskData) {
        let task = this.taskData[i]
        let path = this.dfs(task, rowId, i.toString())
        if(!(path === '*')) return path
      }
      return '*'
    },
    dfs(u, target, path) {
      if(u.taskId === target) {
        return path
      }
      for(let i in u.subTasks) {
        let subTask = u.subTasks[i]
        let getPath = this.dfs(subTask, target, path + i.toString())
        if(!(getPath === '*')) return path + i.toString()
      }
      return '*'
    },
    mounted() {
      this.colprop = [
        {
          type: "selection",
          title: "<a>任务名称</a>",
          field: "name",
          width: 200,
          align: "left",
          titleAlign: "left",
        },
        {
          title: "任务ID",
          type: "id",
          width: 100,
          align: "center"
        },
        {
          title:"任务优先级",
          field:'priority',
          width:100,
          align: 'center'
        },
        {
          title:"任务状态",
          field:"status",
          width:100,
          align:'center'
        },
        {
          title: "Action",
          type: "action",
          flex: 1,
          align: "center",
        },
      ];
  },
  }
}
</script>

<style scoped>

</style>