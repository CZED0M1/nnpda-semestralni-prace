import axios from 'axios'

export interface User {
  id: number
  username: string
}

export function getUsers() {
  return axios.get<User[]>('/users')
}
