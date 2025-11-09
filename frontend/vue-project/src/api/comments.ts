import api from './client'

export const getProjectComments = (projectId: number) =>
  api.get(`/projects/${projectId}/comments`)

export const createProjectComment = (projectId: number, data: any) =>
  api.post(`/projects/${projectId}/comments`, data)

export const updateProjectComment = (
  projectId: number,
  commentId: number,
  data: any
) => api.put(`/projects/${projectId}/comments/${commentId}`, data)

export const deleteProjectComment = (projectId: number, commentId: number) =>
  api.delete(`/projects/${projectId}/comments/${commentId}`)


// TICKET COMMENTS
export const getTicketComments = (projectId: number, ticketId: number) =>
  api.get(`/projects/${projectId}/tickets/${ticketId}/comments`)

export const createTicketComment = (
  projectId: number,
  ticketId: number,
  data: any
) => api.post(`/projects/${projectId}/tickets/${ticketId}/comments`, data)

export const updateTicketComment = (
  projectId: number,
  ticketId: number,
  commentId: number,
  data: any
) => api.put(`/projects/${projectId}/tickets/${ticketId}/comments/${commentId}`, data)

export const deleteTicketComment = (
  projectId: number,
  ticketId: number,
  commentId: number
) => api.delete(`/projects/${projectId}/tickets/${ticketId}/comments/${commentId}`)
