<template>
  <div id="app">

    <v-app-bar
      color="indigo darken-2"
      dense
      dark
    >
      <v-toolbar-title>Agent internaute</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        
      </v-btn>

      <v-tooltip bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn icon
          v-bind="attrs"
          v-on="on"
        >
          <v-icon>mdi-account</v-icon>
        </v-btn>
      </template>
      <span :to="{ name: 'profil' }">Profil</span>
    </v-tooltip>

    </v-app-bar>

    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
