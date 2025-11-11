<template>
  <div class="ticket-generator">
    <button @click="toggle">
      {{ enabled ? 'Stop Auto-Generate' : 'Start Auto-Generate' }}
    </button>
    <button @click="generateTicket" :disabled="loading">
      Generate Now
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { createTicket } from '../api/tickets'

const props = defineProps<{ projectId: number }>()
const emit = defineEmits<{
  (e: 'ticket-created', ticket: any): void
}>()

const enabled = ref(false)
const loading = ref(false)
let timer: number | null = null

async function generateTicket() {
  loading.value = true
  try {
    const newTicket = await createTicket(props.projectId, {
      title: `Auto ticket ${Date.now()}`,
      priority: 'LOW',
      type: 'TASK',
      status: 'OPEN',
      assignedUserId: null,
    })
    emit('ticket-created', newTicket.data)
  } finally {
    loading.value = false
  }
}

function start() {
  if (timer) return
  timer = window.setInterval(generateTicket, 20000) // každých 20s
}

function stop() {
  if (!timer) return
  clearInterval(timer)
  timer = null
}

function toggle() {
  enabled.value = !enabled.value
  if (enabled.value) start()
  else stop()
}

onUnmounted(stop)
</script>

<style scoped>
.ticket-generator {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 1rem;
}
button {
  padding: 0.3rem 0.7rem;
  border-radius: 4px;
  border: 1px solid #ccc;
  cursor: pointer;
}
</style>
