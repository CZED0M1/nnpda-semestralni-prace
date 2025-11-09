<template>
  <form @submit.prevent="submit">
    <input v-model="content" placeholder="Add comment" />
    <button type="submit">Add</button>
  </form>
</template>

<script setup lang="ts">
import { ref, defineEmits, defineProps } from 'vue'
import { createProjectComment } from '../api/comments'

const props = defineProps<{
  projectId: number
}>()

const content = ref('')
const emit = defineEmits(['submitted'])

async function submit() {
  if (!content.value.trim()) return

  await createProjectComment(props.projectId, {
    comment: content.value
  })

  content.value = ''
  emit('submitted')
}
</script>
