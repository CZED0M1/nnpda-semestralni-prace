<template>
  <div>
    <h2>{{ project.name }}</h2>
    <p>{{ project.description }}</p>

    <div class="tickets-header">
      <h3>Tickets</h3>
      <button @click="showForm = !showForm">
        {{ showForm ? 'Cancel' : 'Add Ticket' }}
      </button>
    </div>

    <TicketForm
      v-if="showForm"
      :project-id="project.id"
      :ticket="editingTicket"
      :is-edit="!!editingTicket"
      @submitted="refreshTickets"
      @cancel="cancelEdit"
    />

    <TicketList
      v-if="tickets.length > 0"
      :tickets="tickets"
      :project-id="project.id"
      @edit="editTicket"
      @delete="deleteTicket"
      @view="viewTicket"
    />

    <h3>Comments</h3>
    <div v-if="comments.length === 0">No comments yet.</div>
    <CommentList v-else :comments="comments" />
    <CommentForm :project-id="project.id" @submitted="fetchComments" />

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getProject } from '../api/projects'
import { getTickets, deleteTicket as apiDeleteTicket } from '../api/tickets'
import { getProjectComments } from '../api/comments'

import TicketList from '../components/TicketList.vue'
import TicketForm from '../components/TicketForm.vue'
import CommentList from '../components/CommentList.vue'
import CommentForm from '../components/CommentForm.vue'

const route = useRoute()
const router = useRouter()

const project = ref<any>({})
const tickets = ref<any[]>([])
const comments = ref<any[]>([])
const showForm = ref(false)
const editingTicket = ref<any | null>(null)

async function fetchProject() {
  const res = await getProject(Number(route.params.id))
  project.value = res.data
}

async function fetchTickets() {
  if (!project.value.id) return
  const res = await getTickets(project.value.id)
  tickets.value = res.data || []
}

async function fetchComments() {
  if (!project.value.id) return
  const res = await getProjectComments(project.value.id)
  console.log('Fetched comments:', res.data) // <-- check data
  comments.value = Array.isArray(res.data) ? res.data : []
}

function editTicket(ticket: any) {
  editingTicket.value = { ...ticket } // kopie ticketu
  showForm.value = true
}

function cancelEdit() {
  editingTicket.value = null
  showForm.value = false
}

async function deleteTicket(ticketId: number) {
  if (!project.value.id) return
  if (!confirm('Are you sure you want to delete this ticket?')) return
  await apiDeleteTicket(project.value.id, ticketId)
  await refreshTickets()
}

function viewTicket(ticket: any) {
  router.push(`/projects/${project.value.id}/tickets/${ticket.id}`)
}

async function refreshTickets() {
  await fetchTickets()
  cancelEdit()
}

onMounted(async () => {
  await fetchProject()
  await fetchTickets()
  await fetchComments()
})
</script>

<style scoped>
.tickets-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.tickets-header button {
  padding: 0.4rem 0.8rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.tickets-header button:hover {
  background-color: #369f6b;
}
</style>
