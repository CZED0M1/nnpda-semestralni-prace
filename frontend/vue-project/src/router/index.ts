import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import RequestPasswordResetView from '../views/RequestPasswordResetView.vue'
import ResetPasswordView from '../views/ResetPasswordView.vue'
import ChangePasswordView from '../views/ChangePasswordView.vue'

import ProjectsListView from '../views/ProjectsListView.vue'
import ProjectDetailView from '../views/ProjectDetailView.vue'
import TicketDetailView from '../views/TicketDetailView.vue'
import AsignedTicketsView from "@/views/AsignedTicketsView.vue";

const routes: RouteRecordRaw[] = [
  { path: '/login', component: LoginView },
  { path: '/register', component: RegisterView },
  { path: '/password-reset', component: RequestPasswordResetView },
  { path: '/password-reset/:token', component: ResetPasswordView },
  { path: '/change-password', component: ChangePasswordView },

  { path: '/projects', component: ProjectsListView },
  { path: '/projects/:id', component: ProjectDetailView },


  { path: '/tickets', component: TicketDetailView },
  { path: '/tickets/:id', component: TicketDetailView },

  { path: '/projects/:projectId/tickets/:ticketId', component: TicketDetailView },

  { path: '/', redirect: '/login' },
  { path: '/tickets/assigned', component: AsignedTicketsView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
