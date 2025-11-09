<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getTicket, getTicketChanges, deleteTicket as apiDeleteTicket } from '../api/tickets'
import { getTicketComments } from '../api/comments'
import CommentList from '../components/CommentList.vue'
import CommentForm from '../components/CommentForm.vue'

const route = useRoute()
const router = useRouter()
const projectId = Number(route.params.projectId)
const ticketId = Number(route.params.ticketId)

const ticket = ref<any>({})
const changes = ref<any[]>([])
const comments = ref<any[]>([])

async function fetchTicket() {
  const res = await getTicket(projectId, ticketId)
  ticket.value = res.data
}

async function fetchChanges() {
  const res = await getTicketChanges(projectId, ticketId)
  changes.value = res.data
}

async function fetchComments() {
  const res = await getTicketComments(projectId, ticketId)
  comments.value = res.data
}

function editTicket(ticket: any) {
  // Přesměrování na ticket list s query pro editaci, nebo modal:
  router.push({ name: 'TicketList', query: { editTicketId: ticket.id } })
}

async function deleteTicket() {
  if (!confirm('Are you sure you want to delete this ticket?')) return
  await apiDeleteTicket(projectId, ticketId)
  router.push(`/projects/${projectId}/tickets`)
}

onMounted(async () => {
  await fetchTicket()
  await fetchChanges()
  await fetchComments()
})
</script>
