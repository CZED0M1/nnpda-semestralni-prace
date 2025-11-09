import api from './client'

export interface LoginRequest {
  username: string;
  password: string;
}

export const login = (data: LoginRequest) =>
  api.post<string>('/auth/login', data)

export const registerUser = (data: any) =>
  api.post('/auth/register', data)

export const requestReset = (data: any) =>
  api.post('/auth/request-password-reset', data)

export const resetPassword = (data: any) =>
  api.post('/auth/reset-password', data)

export const changePassword = (data: any) =>
  api.post('/auth/change-password', data)
