<script setup lang="ts">
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useAuthStore } from './stores/auth'

const auth = useAuthStore()
const router = useRouter()

function logout() {
  auth.logout()
  router.push('/login')
}
</script>

<template>
  <header class="app-header">

    <nav>
      <RouterLink to="/projects">Projects</RouterLink>
      <RouterLink to="/tickets">Tickets</RouterLink>

      <button v-if="auth.token" @click="logout" class="logout-btn">Logout</button>
    </nav>
  </header>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
.app-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #42b983;
  color: white;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

nav {
  display: flex;
  align-items: center;
}

nav a {
  margin-left: 1rem;
  text-decoration: none;
  color: white;
  font-weight: 500;
}

nav a.router-link-active {
  font-weight: bold;
  text-decoration: underline;
}

.logout-btn {
  margin-left: 1rem;
  background: white;
  color: #42b983;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background 0.2s;
}

.logout-btn:hover {
  background: #f0f0f0;
}

main {
  padding: 2rem;
}
</style>
