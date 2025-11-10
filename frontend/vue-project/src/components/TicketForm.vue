<template>
  <div class="ticket-form-container">
    <h3>{{ isEdit ? 'Edit Ticket' : 'Create Ticket' }}</h3>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label>Title</label>
        <input v-model="localTicket.title" type="text" required />
      </div>

      <div class="form-group">
        <label>Assignee User ID</label>
        <input v-model.number="localTicket.assignedUserId" type="number" placeholder="Enter user ID" />
      </div>

      <div class="form-group">
        <label>Status</label>
        <select v-model="localTicket.status">
          <option>OPEN</option>
          <option>IN_PROGRESS</option>
          <option>DONE</option>
        </select>
      </div>

      <div class="form-group">
        <label>Priority</label>
        <select v-model="localTicket.priority">
          <option>LOW</option>
          <option>MEDIUM</option>
          <option>HIGH</option>
        </select>
      </div>

      <div class="form-group">
        <label>Type</label>
        <select v-model="localTicket.type">
          <option>BUG</option>
          <option>FEATURE</option>
          <option>TASK</option>
        </select>
      </div>

      <button type="submit">{{ isEdit ? 'Save Changes' : 'Create Ticket' }}</button>
      <button type="button" @click="$emit('cancel')">Cancel</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { createTicket, updateTicket } from '../api/tickets'

interface Ticket {
  id?: number
  title: string
  assignedUserId?: number | null
  status?: string
  priority?: string
  type?: string
}

const props = defineProps<{
  ticket?: Ticket
  projectId: number
  isEdit?: boolean
}>()

const emit = defineEmits<{ (e: 'submitted'): void; (e: 'cancel'): void }>()

const localTicket = ref<Ticket>({
  title: '',
  assignedUserId: null,
  status: 'OPEN',
  priority: 'LOW',
  type: 'TASK',
  ...props.ticket
})

async function submitForm() {
  console.log('Submitting ticket:', localTicket.value)
  if (props.isEdit && props.ticket?.id) {
    await updateTicket(props.projectId, props.ticket.id, localTicket.value)
  } else {
    await createTicket(props.projectId, localTicket.value)
  }
  emit('submitted')
}

watch(() => props.ticket, (newTicket) => {
  if (newTicket) localTicket.value = { ...newTicket }
})
</script>

<style scoped>
.ticket-form-container {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1.5rem;
  background: #f9f9f9;
  margin-bottom: 2rem;
}

.ticket-form-container .form-group { margin-bottom: 1rem; }
.ticket-form-container input, select {
  width: 100%;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
}
.ticket-form-container button {
  margin-right: 0.5rem;
  padding: 0.4rem 0.8rem;
  border: none;
  border-radius: 4px;
  color: white;
  font-weight: bold;
  cursor: pointer;
}
.ticket-form-container button[type="submit"] { background-color: #42b983; }
.ticket-form-container button[type="submit"]:hover { background-color: #369f6b; }
.ticket-form-container button[type="button"] { background-color: #d9534f; }
.ticket-form-container button[type="button"]:hover { background-color: #c9302c; }
</style>
