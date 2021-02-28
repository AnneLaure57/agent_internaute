<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <div class="d-flex ma-5 justify-space-between" style="width: 80%">
      <v-card width="45%">
        <v-card-title>Rechercher un titre spécifique</v-card-title>
        <v-card-text class="d-flex flex-wrap">
          <v-text-field
            class="mr-8"
            v-model="searchfield"
            placeholder="Saisissez un ou plusieurs mots-clés"
            append-outer-icon="mdi-magnify"
            @click:append-outer="search(searchfield, movies, tv_shows, musics)"
          ></v-text-field>
          <v-checkbox v-model="movies" label="Films" class="mx-4"></v-checkbox>
          <v-checkbox
            v-model="tv_shows"
            label="Séries"
            class="mx-4"
          ></v-checkbox>
          <v-checkbox
            v-model="musics"
            label="Musique"
            class="mx-4"
          ></v-checkbox>
        </v-card-text>
        <v-card-actions></v-card-actions>
      </v-card>

      <div class="d-flex align-center justify-center">
        <span class="title">ou</span>
      </div>

      <v-card width="45%">
        <v-card-title>Rechercher par filtre</v-card-title>
        <v-card-text class="d-flex">
          <!-- toto -->
        </v-card-text>
        <v-card-actions></v-card-actions>
      </v-card>
    </div>

    <div style="width: 80%" class="d-flex ma-5">
      <h2 v-if="results.length > 0">Résultats</h2>
    </div>

    <v-card
      v-for="result in results"
      :key="result.title"
      style="width: 80%"
      class="ma-5"
    >
      <v-card-text class="d-flex justify-space-between">
        <v-img
          contain
          max-height="200px"
          max-width="200px"
          width="200px"
          src="../assets/img/default.png"
        ></v-img>
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
        <div v-if="!(purchases.some(data => data == result.id))" class="d-flex flex-column justify-space-around ml-12 mr-4" >
          <v-btn color="primary" @click="buy(result)">Acheter</v-btn>
          <v-btn color="primary" @click="subscribe(result)">S'abonner</v-btn>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Rechercher",

  data() {
    return {
      searchfield: "",
      movies: true,
      tv_shows: false,
      musics: false,
      purchases: [],
      results: [
        {
          id: 1234,
          title: "Le parrain",
          year: 1972,
          rating: 4.5,
          distributorId: 1,
          productorId: 1,
          actorsIds: [1, 2],
          directorsIds: [1],
        },
        {
          id: 3214,
          title: "Le parrain 2",
          year: 1974,
          rating: 4.5,
          distributorId: 1,
          productorId: 2,
          actorsIds: [1, 2],
          directorsIds: [1],
        },
        {
          id: 3234,
          title: "Les bronzés",
          year: 1978,
          rating: 3.7,
          distributorId: 2,
          productorId: 2,
          actorsIds: [1, 2],
          directorsIds: [2],
        },
      ],
      newPurchase: null,
    };
  },

  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if (this.profile == null) this.$router.push({ name: "login" });
    this.getPurchases();
  },

  methods: {
    search() {
      let newSearch = {
        searchField: this.searchfield,
        movies: this.movies,
        tvShows: this.tv_shows,
        musics: this.musics,
        profileId: this.profile.id,
      };

      this.$axios.post("/search", newSearch).then((response) => {
        this.results = response.data;
      });
    },

    buy(result) {
      let newPurchase = {
        itemId: result.id,
        itemTitle: result.title,
        profileId: this.profile.id,
        distributorId: result.distributorId,
        productorId: result.productorId,
        actorsIds: result.actorsIds,
        directorsIds: result.directorsIds,
      };
      // Ajout dans le tableau des purchases pour cacher les boutons
      this.purchases.push(result.id);

      console.log(newPurchase);
      this.$axios.post("/purchases", newPurchase).then(
        (response) => {
          console.log(response.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    subscribe() {},

    getPurchases() {
      this.$axios.get("/purchases/" + this.profile.id).then(
        (response) => {
          
          response.data.forEach(element => {
            this.purchases.push(element.itemId);
          });

        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>
