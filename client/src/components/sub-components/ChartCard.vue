<template>
  <div class="chart-div">
    <div class="chart-card-header">
      <div class="meta">
        <span class="chart-card-title">{{ title }}</span>
        <span class="chart-card-action">
          <slot name="action"></slot>
        </span>
      </div>
      <div class="total"><span>{{ total }}</span></div>
    </div>
    <div class="chart-card-content">
      <div class="content-fix">
        <slot></slot>
      </div>
    </div>
    <div class="chart-card-footer">
      <div class="field">
        <slot name="footer"></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChartCard',
  props: {
    title: {
      type: String,
      default: ''
    },
    total: {
      type: String,
      default: ''
    },
  }
}
</script>

<style lang="less" scoped>

.chart-div{
  height: 22vh;
  border: 2px solid #a1a1a1;;
  border-radius: 10px;
  background: #c0c0d0;
}

.chart-card-header {
  position: relative;
  overflow: hidden;
  width: 100%;
  text-align: left;
  margin: 10px;

  .meta {
    position: relative;
    overflow: hidden;
    width: 100%;
    color: rgba(0, 0, 0, .45);
    font-size: 14px;
    line-height: 22px;
  }
}

.chart-card-action {
  cursor: pointer;
  position: absolute;
  top: 0;
  right: 0;
}

.chart-card-footer {
  border-top: 1px solid #e8e8e8;
  padding-top: 9px;
  margin-top: 8px;

  > * {
    position: relative;
  }

  .field {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin: 0;
  }
}

.chart-card-content {
  margin-bottom: 12px;
  position: relative;
  height: 70px;
  width: 100%;

  .content-fix {
    position: absolute;
    left: 0;
    bottom: 0;
    height: 60px;
    width: 100%;
  }
}

.total {
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
  white-space: nowrap;
  color: #000;
  margin-top: 4px;
  margin-bottom: 0;
  font-size: 30px;
  line-height: 38px;
  height: 38px;
}
</style>
