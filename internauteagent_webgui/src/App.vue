<template>
  <v-app>
    <!-- App bar -->
    <v-app-bar app flat clipped-left dark color="black">
      <v-toolbar-title class="link ml-6" :to="{ name: 'rechercher' }">
        <span>Agent internaute</span>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-menu offset-y bottom>
        <template v-slot:activator="{ on }">
          <div v-on="on" class="link mr-12">
            <span>
              {{ login ? login : "Anonymous" }}
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
/* eslint-disable no-console */
export default {
  name: "App",

  data() {
    return {
      login: null,
    };
  },

  mounted() {
    ///
  },

  methods: {
    logout() {
      localStorage.clear();
    },
  },
};
</script>
