<template>
    <a :href="to" @click="navigate">
      <slot></slot>
    </a>
  </template>
  
  <script>
  import { useRouter } from 'vue-router';
  
  export default {
    props: {
      to: {
        type: String,
        required: true
      }
    },
    setup(props) {
      const router = useRouter();
  
      function navigate(event) {
        event.preventDefault();
        router.push(props.to);
      }
  
      return {
        navigate
      };
    }
  }
  </script>