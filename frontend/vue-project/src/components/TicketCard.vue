<template>
  <div class="ticket-card">
    <div class="ticket-info" @click="$emit('view', ticket)">
      <h3>{{ ticket.title }}</h3>
      <p>Status: <span :class="'status ' + ticket.status.toLowerCase()">{{ ticket.status }}</span></p>
      <p>Priority: <span :class="'priority ' + ticket.priority.toLowerCase()">{{ ticket.priority }}</span></p>
      <p>Type: <span :class="'type ' + ticket.type.toLowerCase()">{{ ticket.type }}</span></p>
      <p>Assignee: {{ ticket.assignedUserId}}</p>
    </div>

    <div class="ticket-actions">
      <button @click.prevent.stop="$emit('edit', ticket)" class="edit-btn">Edit</button>
      <button @click.prevent.stop="$emit('delete', ticket.id)" class="delete-btn">Delete</button>
    </div>
  </div>
</template>

<script setup lang="ts">

defineProps<{ ticket: any }>()
defineEmits<{
  (e: 'edit', ticket: any): void
  (e: 'delete', id: number): void
  (e: 'view', ticket: any): void
}>()
</script>

<style scoped>
.ticket-card {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  border: 1px solid #ddd;
  border-radius: 6px;
  padding: 1rem;
  margin-bottom: 1rem;
  background: white;
  box-shadow: 0 1px 4px rgba(0,0,0,0.1);
  transition: transform 0.2s;
}

.ticket-card:hover {
  transform: translateY(-2px);
}

.ticket-info {
  flex: 1;
  cursor: pointer;
}

.ticket-info h3 { margin: 0 0 0.5rem 0; color: #333; }
.ticket-info p { margin: 0.2rem 0; color: #555; font-size: 0.9rem; }

.status, .priority, .type {
  font-weight: bold;
  padding: 0.1rem 0.3rem;
  border-radius: 3px;
  color: white;
  font-size: 0.8rem;
}

.status.open { background-color: #42b983; }
.status.closed { background-color: #d9534f; }
.status.inprogress { background-color: #f0ad4e; }

.priority.low { background-color: #5bc0de; }
.priority.medium { background-color: #f0ad4e; }
.priority.high { background-color: #d9534f; }

.type.bug { background-color: #d9534f; }
.type.feature { background-color: #5bc0de; }
.type.task { background-color: #42b983; }

.ticket-actions {
  display: flex;
  flex-direction: column;
  margin-left: 1rem;
}

.ticket-actions button {
  margin-bottom: 0.3rem;
  padding: 0.4rem 0.8rem;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  color: white;
  transition: background 0.2s;
}

.edit-btn { background-color: #f0ad4e; }
.edit-btn:hover { background-color: #ec971f; }

.delete-btn { background-color: #d9534f; }
.delete-btn:hover { background-color: #c9302c; }
</style>
