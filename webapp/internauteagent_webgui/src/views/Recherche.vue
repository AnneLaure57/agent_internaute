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
    name: 'Recherche',

  data() {
   return {
      searchfield: "",
      results: [],
    };
  },

  mounted() {
  },

  methods: {
    search() {
      this.$http.get("/search").then(response => {
        this.results = response.body;
      });
    },
  },
    
  }
</script>
