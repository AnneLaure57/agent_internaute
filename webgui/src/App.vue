<template>
  <v-app>
    <!-- App bar -->
    <v-app-bar app flat clipped-left dark color="black">
      <v-toolbar-title class="link ml-6" @click="goToHome()">
        <span>Agent internaute</span>
      </v-toolbar-title>

      <v-icon dark class="link ml-6" @click="goToHome()">mdi-magnify</v-icon>

      <v-spacer></v-spacer>
      <div class="d-flex align-center mx-12" v-if="profile">
        <v-icon color="primary" class="mx-4">mdi-timer</v-icon>
        <span>{{ profile.currentConsumptionTime }} / {{ profile.averageConsumptionTime }} h</span>
        </div>

      <div class="d-flex align-centermx-12" v-if="profile">
        <v-icon color="primary" class="mx-4">mdi-currency-eur</v-icon>
        <span>{{ profile.currentExpenses }} / {{ profile.maxBudget }} €</span>
      </div>
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

          <v-list-item class="link" :to="{ name: 'satisfactions' }">
            <v-list-item-icon>
              <v-icon>mdi-heart-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Satisfactions</v-list-item-title>
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
    if (this.profile == null) this.$router.push({ name: "login" });
  },

  methods: {
    goToHome() {
      this.$router.push({ name: "rechercher" });
    },

    logout() {
      this.$axios.delete("/profil/" + this.profile.id); // Only try to kill agent
      this.$store.commit("setProfile", null);
      this.$router.push({ name: "login" });
    },
  },
};
</script>
