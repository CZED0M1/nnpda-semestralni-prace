<template>
  <div class="projects-container">
    <div class="projects-header">
      <h2>Projects</h2>
      <button @click="showForm = !showForm" class="create-btn">
        {{ showForm ? 'Cancel' : 'Create Project' }}
      </button>
    </div>

    <!-- Create / Edit Form -->
    <form v-if="showForm" @submit.prevent="submitProject" class="project-form">
      <input v-model="projectName" placeholder="Project Name" required />
      <button type="submit">{{ editingProject ? 'Save Changes' : 'Add Project' }}</button>
    </form>

    <div class="projects-list">
      <ProjectCard
        v-for="project in projects"
        :key="project.id"
        :project="project"
        @edit="editProject"
        @delete="deleteProject"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { getProjects, createProject, updateProject, deleteProject as apiDeleteProject } from '../api/projects'
import ProjectCard from '../components/ProjectCard.vue'

const projects = ref<any[]>([])
const showForm = ref(false)
const projectName = ref('')
const editingProject = ref<any>(null)

async function fetchProjects() {
  const res = await getProjects()
  projects.value = res.data
}

onMounted(fetchProjects)

async function submitProject() {
  if (editingProject.value) {
    // Edit project
    const res = await updateProject(editingProject.value.id, { name: projectName.value })
    const index = projects.value.findIndex(p => p.id === editingProject.value.id)
    projects.value[index] = res.data
    editingProject.value = null
  } else {
    // Create project
    const res = await createProject({ name: projectName.value })
    projects.value.push(res.data)
  }
  projectName.value = ''
  showForm.value = false
}

function editProject(project: any) {
  editingProject.value = project
  projectName.value = project.name
  showForm.value = true
}

async function deleteProject(id: number) {
  if (!confirm('Are you sure you want to delete this project?')) return
  await apiDeleteProject(id)
  projects.value = projects.value.filter(p => p.id !== id)
}
</script>

<style scoped>
.projects-container { max-width: 800px; margin: 0 auto; }
.projects-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; }
.create-btn { background-color: #42b983; color: white; border: none; padding: 0.5rem 1rem; border-radius: 4px; cursor: pointer; font-weight: bold; }
.create-btn:hover { background-color: #369f6b; }
.project-form { display: flex; gap: 0.5rem; margin-bottom: 2rem; }
.project-form input { flex: 1; padding: 0.5rem; border: 1px solid #ccc; border-radius: 4px; }
.project-form button { background-color: #42b983; color: white; border: none; padding: 0.5rem 1rem; border-radius: 4px; cursor: pointer; }
.project-form button:hover { background-color: #369f6b; }
.projects-list { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 1rem; }
</style>
