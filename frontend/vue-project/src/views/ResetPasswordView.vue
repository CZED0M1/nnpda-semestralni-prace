<template>
  <div class="reset-password-container">
    <h2>Reset Password</h2>
    <form @submit.prevent="submit" class="reset-form">
      <div class="form-group">
        <label for="token">Token</label>
        <input
          id="token"
          v-model="token"
          placeholder="Enter your token"
          required
        />
      </div>

      <div class="form-group">
        <label for="newPassword">New Password</label>
        <input
          id="newPassword"
          type="password"
          v-model="newPassword"
          placeholder="Enter new password"
          required
        />
      </div>

      <button type="submit" class="submit-btn">Reset Password</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { resetPassword } from '../api/auth'

const token = ref('')
const newPassword = ref('')
const router = useRouter()

async function submit() {
  try {
    await resetPassword({ token: token.value, newPassword: newPassword.value }) // správný klíč
    alert('Password changed successfully!')
    router.push('/login') // přesměrování na login po úspěchu
  } catch (error: any) {
    alert(error.response?.data?.error || 'Something went wrong')
  }
}
</script>

<style scoped>
.reset-password-container {
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

.reset-form .form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.reset-form label {
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
}

.reset-form input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.reset-form input:focus {
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
