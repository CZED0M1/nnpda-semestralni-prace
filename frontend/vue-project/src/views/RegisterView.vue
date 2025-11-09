<template>
  <div class="register-container">
    <h2>Register</h2>
    <form @submit.prevent="submit" class="register-form">
      <div class="form-group">
        <label for="username">Username</label>
        <input
          id="username"
          v-model="username"
          placeholder="Enter your username"
          required
        />
      </div>

      <div class="form-group">
        <label for="email">Email</label>
        <input
          id="email"
          type="email"
          v-model="email"
          placeholder="Enter your email"
          required
        />
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input
          id="password"
          type="password"
          v-model="password"
          placeholder="Enter your password"
          required
        />
      </div>

      <button type="submit" class="submit-btn">Register</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { registerUser } from '../api/auth'

const username = ref('')
const email = ref('')
const password = ref('')

const router = useRouter()

async function submit() {
  try {
    await registerUser({ username: username.value, email: email.value, password: password.value })
    alert('Registration successful!')
    router.push('/login')
  } catch (error: any) {
    alert(error.response?.data?.error || 'Something went wrong')
  }
}
</script>

<style scoped>
.register-container {
  max-width: 400px;
  margin: 3rem auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

.register-form .form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.register-form label {
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
}

.register-form input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.register-form input:focus {
  outline: none;
  border-color: #42b983;
  box-shadow: 0 0 2px #42b983;
}

.submit-btn {
  width: 100%;
  padding: 0.75rem;
  font-size: 1rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit-btn:hover {
  background-color: #369f6b;
}
</style>
