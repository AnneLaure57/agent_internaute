<template>
  <v-app>
    <!-- App bar -->
    <v-app-bar app flat clipped-left dark color="black">
      <v-toolbar-title class="link ml-6" @click="goToHome()">
        <span>Agent internaute</span>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-menu offset-y bottom v-if="profile">
        <template v-slot:activator="{ on }">
          <div v-on="on" class="link mr-12">
            <span>
              {{ profile && profile.name ? profile.name : "Anonymous" }}
            </span>
            <v-avatar class color="primary" size="30px">
              <v-icon color="white">mdi-account</v-icon>
            </v-avatar>
          </div>
        </template>

        <v-list>
          <v-list-item class="link" :to="{ name: 'profil' }">
            <v-list-item-icon>
              <v-icon>mdi-account-settings</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Préférences</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item class="link" :to="{ name: 'historique' }">
            <v-list-item-icon>
              <v-icon>mdi-history</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Historique</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item @click="logout">
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Déconnexion</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>

    <!-- Contenu -->
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<style>
.link {
  cursor: pointer;
}
</style>

<script>
import { mapState } from "vuex";
/* eslint-disable no-console */
export default {
  name: "App",

  data() {
    return {
      //
    };
  },

  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if(this.profile == null) this.$router.push({ name: "login" });
  },

  methods: {
    goToHome() {
      this.$router.push({ name: "rechercher" });
    },

    logout() {
      this.$store.commit("setProfile", null);
      this.$router.push({ name: "login" });
    },
  },
};
</script>
