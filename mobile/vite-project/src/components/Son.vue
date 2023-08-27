<template>
  <div>
    子组件的 count：{{ count }}
  </div>
</template>

<script setup>

// 获取 count.js 中抛出的 useCountStore 方法
import { useCountStore } from '../stores/count'
import { storeToRefs } from 'pinia'
const { count } = storeToRefs(useCountStore())
</script>