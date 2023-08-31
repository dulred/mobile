<template>
    <button :class="computedClasses">
      <slot></slot>
    </button>
  </template>
  
  <script>
  export default {
    props: {
      type: {
        type: String,
        default: "default" // 默认样式
      }
    },
    computed: {
      computedClasses() {
        // 根据 type 属性生成对应的 CSS 类
        return `custom-button custom-button--${this.type}`;
      }
    }
  };
  </script>
  
  <style scoped>
  /* 样式示例，根据需要自定义 */
  .custom-button {
    padding: 10px 20px;
    border: 1px solid #ccc;
    background-color: #fff;
    color: #333;
    cursor: pointer;
  }
  
  .custom-button--primary {
    background-color: #007bff;
    color: #fff;
  }
  
  .custom-button--danger {
    background-color: #dc3545;
    color: #fff;
  }
  </style>
  