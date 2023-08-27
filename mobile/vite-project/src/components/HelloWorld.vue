<!--父组件-->
<template>
  <!--子组件，传递一个count参数-->
  <Son :count="count" @onAdd="add"></Son>
</template>

<script setup>

import { ref } from 'vue'
// 引入子组件
import Son from './Son.vue'

const count = ref(0)
const add = () => {
  count.value += 1
}

</script>