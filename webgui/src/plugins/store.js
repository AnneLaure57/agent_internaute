import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const store = new Vuex.Store({

    plugins: [createPersistedState()],
    
    state: {
        profile: null,
    },

    mutations: {
        setProfile(state, profile) {
            state.profile = profile;
        },
    },

    actions: {
        setProfile(context, profile) {
            context.commit('setProfile', profile);
        },
    }
})

export default store