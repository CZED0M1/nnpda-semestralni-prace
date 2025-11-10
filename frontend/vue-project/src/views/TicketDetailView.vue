<template>
  <div>
    <h2>Ticket: {{ ticket.title }}</h2>
    <p>Status: {{ ticket.status }}</p>
    <p>Priority: {{ ticket.priority }}</p>
    <p>Assignee: {{ ticket.assignedUser?.username || 'Unassigned' }}</p>

    <h3>History</h3>
    <div v-if="changes.length === 0">No changes yet.</div>
    <ul v-else>
      <li v-for="change in changes" :key="change.id">
        {{ formatChange(change) }}
      </li>
    </ul>

    <h3>Comments</h3>
    <div v-if="ticketComments.length === 0">No comments yet.</div>
    <TicketCommentList :comments="ticketComments" />
    <TicketCommentForm
      :project-id="projectId"
      :ticket-id="ticketId"
      @submitted="fetchTicketComments"
    />

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getTicket, getTicketChanges } from '../api/tickets'
import { getTicketComments } from '../api/comments'

import TicketCommentList from '../components/TicketCommentList.vue'
import TicketCommentForm from '../components/TicketCommentForm.vue'

// --- Typy ---
interface Ticket {
  id: number
  title: string
  status: string
  priority: string
  assignedUser?: { id: number; username: string } | null
}

interface TicketChange {
  id: number
  status: string
  priority: string
  updated: string
  username: string
}

interface TicketComment {
  id: number
  comment: string
  user: { username: string }
}

// --- Data ---
const route = useRoute()
const projectId = Number(route.params.projectId)
const ticketId = Number(route.params.ticketId)

const ticket = ref<Ticket>({} as Ticket)
const changes = ref<TicketChange[]>([])
const ticketComments = ref<TicketComment[]>([])

// --- API volání ---
async function fetchTicket() {
  const res = await getTicket(projectId, ticketId)
  ticket.value = res.data
}

async function fetchChanges() {
  const res = await getTicketChanges(projectId, ticketId)
  changes.value = res.data || []
}

async function fetchTicketComments() {
  const res = await getTicketComments(projectId, ticketId)
  ticketComments.value = res.data || []
}

// --- Pomocné funkce ---
function formatChange(change: TicketChange) {
  const date = new Date(change.updated).toLocaleString()
  return `${date} - Status: ${change.status}, Priority: ${change.priority}, Changed by: ${change.username}`
}

// --- Lifecycle ---
onMounted(async () => {
  await fetchTicket()
  await fetchChanges()
  await fetchTicketComments()
})
</script>
