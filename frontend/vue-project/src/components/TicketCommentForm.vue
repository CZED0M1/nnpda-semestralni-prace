<template>
  <form @submit.prevent="submit">
    <input v-model="content" placeholder="Add comment" />
    <button type="submit">Add</button>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { createTicketComment } from '../api/comments'

const props = defineProps<{ projectId:number, ticketId:number }>()
const emit = defineEmits(['submitted'])
const content = ref('')

async function submit() {
  await createTicketComment(props.projectId, props.ticketId, {
    comment: content.value
  })
  emit('submitted')
  content.value = ''
}

</script>
