import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Rechercher from '@/views/Rechercher.vue'
import Profil from '@/views/Profil.vue'
import Historique from '@/views/Historique.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: "/login",
        component: Login,
        name: "login"
    },
    {
        path: "/register",
        component: Register,
        name: "register"
    },
    {
        path: "/rechercher",
        component: Rechercher,
        name: "rechercher"
    },
    {
        path: "/profil",
        component: Profil,
        name: "profil"
    },
    {
        path: "/historique",
        component: Historique,
        name: "historique"
    },
    {
        path: "*",
        redirect: "/login"
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router