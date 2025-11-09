<template>
  <div class="reset-request-container">
    <h2>Request Password Reset</h2>
    <form @submit.prevent="submit" class="reset-request-form">
      <div class="form-group">
        <label for="username">Username</label>
        <input
          id="username"
          type="text"
          v-model="username"
          placeholder="Enter your username"
          required
        />
      </div>

      <button type="submit" class="submit-btn">Send Reset Link</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { requestReset } from '../api/auth'
import {useRouter} from "vue-router";
const router = useRouter()

const username = ref('')

async function submit() {
  try {
    const res = await requestReset({ username: username.value })
    router.push("password-reset/"+res.data)
    alert(`Reset token: ${res.data}`)
  } catch (error: any) {
    alert(error.response?.data?.error || 'Something went wrong')
  }
}
</script>

<style scoped>
.reset-request-container {
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

.reset-request-form .form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.reset-request-form label {
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
}

.reset-request-form input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.reset-request-form input:focus {
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
