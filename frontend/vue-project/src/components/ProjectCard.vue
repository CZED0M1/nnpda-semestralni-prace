<template>
  <div class="project-card">
    <div class="project-info" @click="viewProject">
      <h3>{{ props.project.name }}</h3>
    </div>

    <div class="project-actions">
      <button @click.stop="$emit('edit', props.project)" class="edit-btn">Edit</button>
      <button @click.stop="$emit('delete', props.project.id)" class="delete-btn">Delete</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const props = defineProps<{ project: { id: number; name: string } }>()

const emit = defineEmits<{
  (e: 'edit', project: any): void
  (e: 'delete', id: number): void
}>()

const router = useRouter()

function viewProject() {
  router.push(`/projects/${props.project.id}`)
}
</script>

<style scoped>
.project-card {
  border: 1px solid #ccc;
  padding: 1rem;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.project-info {
  flex: 1;
  cursor: pointer;
}

.project-info h3 {
  margin: 0;
}

.project-actions button {
  margin-left: 0.5rem;
  padding: 0.3rem 0.6rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.edit-btn {
  background-color: #f0ad4e;
  color: white;
}
.edit-btn:hover { background-color: #ec971f; }

.delete-btn {
  background-color: #d9534f;
  color: white;
}
.delete-btn:hover { background-color: #c9302c; }
</style>
