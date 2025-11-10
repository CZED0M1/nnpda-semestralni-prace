<template>
  <div>
    <h2 class="mb-4 text-2xl font-bold">Assigned Tickets</h2>

    <div v-if="tickets.length === 0" class="text-gray-500">
      No tickets assigned to you.
    </div>

    <div v-else class="grid gap-4 grid-cols-1 sm:grid-cols-2 md:grid-cols-3">
      <TicketCard
        v-for="ticket in tickets"
        :key="ticket.id"
        :ticket="ticket"
        @view="viewTicket"
        @edit="editTicket"
        @delete="deleteTicket"
      />
    </div>

    <TicketForm
      v-if="showForm && editingTicket"
      :project-id="editingTicket.projectId"
    :ticket="editingTicket"
    :is-edit="true"
    @submitted="refreshTickets"
    @cancel="cancelEdit"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getAssignedTickets, deleteTicket as apiDeleteTicket } from '../api/tickets'
import TicketCard from '../components/TicketCard.vue'
import TicketForm from '../components/TicketForm.vue'

const tickets = ref<any[]>([])
const showForm = ref(false)
const editingTicket = ref<any | null>(null)
const router = useRouter()

async function fetchAssigned() {
  const res = await getAssignedTickets()
  tickets.value = Array.isArray(res.data) ? res.data : []
}

function viewTicket(ticket: any) {
  router.push(`/projects/${ticket.projectId}/tickets/${ticket.id}`)
}

function editTicket(ticket: any) {
  editingTicket.value = { ...ticket } // kopie ticketu pro edit
  showForm.value = true
}

function cancelEdit() {
  editingTicket.value = null
  showForm.value = false
}

async function deleteTicket(ticket: any) {
  if (!confirm('Are you sure you want to delete this ticket?')) return
  try {
    await apiDeleteTicket(ticket.projectId, ticket.id)
    tickets.value = tickets.value.filter(t => t.id !== ticket.id)
  } catch (err) {
    console.error('Failed to delete ticket:', err)
    alert('Failed to delete ticket')
  }
}

async function refreshTickets() {
  await fetchAssigned()
  cancelEdit()
}

onMounted(() => {
  fetchAssigned()
})
</script>

<style scoped>
div[class*="ticket-card"]:hover {
  transform: translateY(-2px);
  transition: 0.2s;
  box-shadow: 0 8px 16px rgba(0,0,0,0.1);
}
</style>
