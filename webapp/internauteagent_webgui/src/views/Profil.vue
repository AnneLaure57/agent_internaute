<template>
  <v-container>
      <v-text-field
            v-model="searchfield"
            label="Rechercher"
            append-outer-icon="mdi-magnify"
            @click:append-outer="search"
          ></v-text-field>
  </v-container>
</template>

<script>
  export default {
    name: 'Profil',

  data() {
   return {
      //
    };
  },

  mounted() {
  },

  methods: {
    search() {
      this.$http.get("/profil").then(response => {
        this.results = response.body;
      });
    },
  },
    
  }
</script>
