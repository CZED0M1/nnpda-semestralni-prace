import api from './client'

export const getTickets = (projectId: number) =>
  api.get(`/projects/${projectId}/tickets`)

export const getTicket = (projectId: number, ticketId: number) =>
  api.get(`/projects/${projectId}/tickets/${ticketId}`)

export const createTicket = (projectId: number, data: any) =>
  api.post(`/projects/${projectId}/tickets`, data)

export const updateTicket = (projectId: number, ticketId: number, data: any) =>
  api.put(`/projects/${projectId}/tickets/${ticketId}`, data)

export const deleteTicket = (projectId: number, ticketId: number) =>
  api.delete(`/projects/${projectId}/tickets/${ticketId}`)

export const getTicketChanges = (projectId: number, ticketId: number) =>
  api.get(`/projects/${projectId}/tickets/${ticketId}/changes`)
