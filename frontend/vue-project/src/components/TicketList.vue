<template>
  <div>
    <TicketCard
      v-for="ticket in tickets"
      :key="ticket.id"
      :ticket="ticket"
      @edit="$emit('edit', $event)"
      @delete="$emit('delete', $event)"
      @view="$emit('view', $event)"
    />

    <TicketForm
      v-if="showForm"
      :ticket="editingTicket"
      :project-id="projectId"
      :is-edit="!!editingTicket"
      @submitted="refreshTickets"
      @cancel="cancelEdit"
    />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import TicketCard from './TicketCard.vue'
import TicketForm from './TicketForm.vue'

const props = defineProps<{ tickets: any[], projectId: number }>()
const emit = defineEmits<{
  (e: 'edit', ticket: any): void
  (e: 'delete', id: number): void
  (e: 'view', ticket: any): void
  (e: 'cancel'): void
}>()

const showForm = ref(false)
const editingTicket = ref<any | null>(null)

function refreshTickets() {
  showForm.value = false
  editingTicket.value = null
}

function cancelEdit() {
  showForm.value = false
  editingTicket.value = null
}
</script>

<style scoped>
</style>
