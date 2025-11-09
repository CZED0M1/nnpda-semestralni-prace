<template>
  <div class="login-container">
    <h2>Login</h2>
    <form @submit.prevent="submit" class="login-form">
      <input v-model="username" placeholder="Username" />
      <input v-model="password" type="password" placeholder="Password" />
      <button type="submit">Login</button>
    </form>

    <div class="login-links">
      <RouterLink to="/register">Don't have an account? Register</RouterLink>
      <RouterLink to="/password-reset">Forgot password?</RouterLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { login, type LoginRequest } from '../api/auth'

const username = ref('')
const password = ref('')

const router = useRouter()
const auth = useAuthStore()

async function submit() {
  try {
    const data: LoginRequest = { username: username.value, password: password.value }
    const res = await login(data)
    auth.setToken(res.data) // předpokládám, že backend vrací token přímo
    router.push('/projects')
  } catch (err) {
    alert('Login failed. Check your credentials.')
  }
}
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 5rem auto;
  padding: 2rem;
  border: 1px solid #ccc;
  border-radius: 12px;
  background-color: #fefefe;
  box-shadow: 0 4px 10px rgba(0,0,0,0.1);
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

.login-form {
  display: flex;
  flex-direction: column;
}

.login-form input {
  padding: 0.8rem;
  margin-bottom: 1rem;
  border-radius: 6px;
  border: 1px solid #ccc;
  font-size: 1rem;
}

.login-form button {
  padding: 0.8rem;
  border: none;
  border-radius: 6px;
  background-color: #42b883;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s;
}

.login-form button:hover {
  background-color: #369d6e;
}

.login-links {
  margin-top: 1rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-links a {
  margin-top: 0.5rem;
  color: #42b883;
  text-decoration: none;
}

.login-links a:hover {
  text-decoration: underline;
}
</style>
