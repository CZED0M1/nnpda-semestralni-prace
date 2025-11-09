<template>
  <div class="change-password-container">
    <h2>Change Password</h2>
    <form @submit.prevent="submit" class="change-form">
      <div class="form-group">
        <label for="currentPassword">Current Password</label>
        <input
          id="currentPassword"
          type="password"
          v-model="currentPassword"
          placeholder="Enter current password"
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

      <button type="submit" class="submit-btn">Change Password</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { changePassword } from '../api/auth'

const currentPassword = ref('')
const newPassword = ref('')

async function submit() {
  try {
    await changePassword({ oldPassword: currentPassword.value, newPassword: newPassword.value })
    alert('Password changed successfully!')
    currentPassword.value = ''
    newPassword.value = ''
  } catch (error: any) {
    alert(error.response?.data?.error || 'Something went wrong')
  }
}
</script>

<style scoped>
.change-password-container {
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

.change-form .form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.change-form label {
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
}

.change-form input {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.change-form input:focus {
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
