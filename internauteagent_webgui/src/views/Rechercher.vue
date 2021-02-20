<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <v-card width="80%" class="ma-10">
      <v-card-title>Rechercher</v-card-title>
      <v-card-text class="d-flex">
        <v-text-field
          class="mr-12"
          v-model="searchfield"
          placeholder="Saisissez un ou plusieurs mots-clés"
          append-outer-icon="mdi-magnify"
          @click:append-outer="search"
        ></v-text-field>
        <v-checkbox v-model="all" label="Tout" class="mx-6"></v-checkbox>
        <v-checkbox v-model="movies" label="Films" class="mx-6"></v-checkbox>
        <v-checkbox v-model="tv_shows" label="Séries" class="mx-6"></v-checkbox>
        <v-checkbox v-model="musics" label="Musique" class="mx-6"></v-checkbox>
      </v-card-text>
      <v-card-actions></v-card-actions>
    </v-card>

    <div style="width: 80%" class="d-flex ma-5">
      <h2 v-if="results.length > 0">Résultats</h2>
    </div>

    <v-card v-for="result in results" :key="result.title" style="width: 80%" class="ma-5">
      <v-card-text class="d-flex justify-space-between">
        <v-img contain max-height="200px" max-width="200px" width="200px" src="../assets/img/default.png"></v-img>
        <div class="d-flex flex-column flex-grow-1 mx-4">
          <span class="title">{{ result.title }}</span>
          <span>{{ result.year }}</span>
        </div>
        <div>
          <v-rating
            empty-icon="mdi-star-outline"
            full-icon="mdi-star"
            half-icon="mdi-star-half"
            length="5"
            size="24"
            color="yellow darken-3"
            background-color="grey darken-1"
            :value="result.rating"
          ></v-rating>
        </div>
        <div class="d-flex flex-column justify-space-around ml-12 mr-4">
          <v-btn color="primary">Acheter</v-btn>
          <v-btn color="primary">S'abonner</v-btn>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "Rechercher",

  data() {
    return {
      searchfield: "",
      all: true,
      movies: false,
      tv_shows: false,
      musics: false,
      results: [
        { title: "Le parrain", year: 1972, rating: 4.5 },
        { title: "Le parrain 2", year: 1974, rating: 4.5 },
        { title: "Les bronzés", year: 1978, rating: 3.7 },
      ],
    };
  },

  mounted() {},

  methods: {
    search() {
      this.$http.get("/search").then((response) => {
        this.results = response.body;
      });
    },
  },
};
</script>
