<template>
  <ul>
    <li v-for="comment in comments" :key="comment.id">{{ comment.comment }}</li>
  </ul>
</template>

<script setup lang="ts">
defineProps<{ comments: any[] }>()
</script>
