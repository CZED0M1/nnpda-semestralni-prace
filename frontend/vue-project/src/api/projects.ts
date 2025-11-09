import api from './client'

export interface ProjectCreateRequest {
  name: string;
  description: string;
}

export const getProjects = () =>
  api.get('/projects')

export const getProject = (id: number) =>
  api.get(`/projects/${id}`)

export const createProject = (data: ProjectCreateRequest) =>
  api.post('/projects', data)

export const updateProject = (id: number, data: any) =>
  api.put(`/projects/${id}`, data)

export const deleteProject = (id: number) =>
  api.delete(`/projects/${id}`)
