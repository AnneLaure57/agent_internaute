import Vue from 'vue'
import VueRouter from 'vue-router'
import Rechercher from '@/views/Rechercher.vue'
import Profil from '@/views/Profil.vue'

Vue.use(VueRouter)

const routes = [{
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
        path: "*",
        redirect: "/rechercher"
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router