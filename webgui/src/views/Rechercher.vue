<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <!-- Recherche -->
    <div class="d-flex ma-5 justify-space-between" style="width: 80%">
      <v-card width="45%">
        <v-card-title>Rechercher un titre spécifique</v-card-title>
        <v-card-text class="d-flex flex-wrap py-2">
          <v-text-field
            class="mr-8"
            v-model="searchfield"
            placeholder="Saisissez un ou plusieurs mots-clés"
            append-outer-icon="mdi-magnify"
            @click:append-outer="search(searchfield, movies, tv_shows, musics)"
          ></v-text-field>
          <div class="d-flex">
            <v-checkbox
              v-model="movies"
              label="Films"
              class="mx-4"
            ></v-checkbox>
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
          </div>
        </v-card-text>
        <v-card-actions v-if="error">
          <span class="subtitle-2 red--text mx-2 pa-0">
            Vous devez choisir au moins un type de media
          </span>
        </v-card-actions>
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

    <!-- Results -->
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
          width="150px"
          src="../assets/img/default.png"
        ></v-img>
        <div class="d-flex flex-column justify-space-between flex-grow-1 mx-4">
          <div class="d-flex flex-column">
            <span class="title">{{ result.titre }} ({{ result.dateSortie }})</span>
          </div>
          <div class="d-flex flex-column">
            <div><span class="subtitle-2">Acteurs: </span>
              <span v-for="(actor, index) in result.acteurs" :key="actor.id">{{ actor.prenom }} {{ actor.nom }}<span v-if="(index+1) != result.acteurs.length">, </span></span>
            </div>
            <div><span class="subtitle-2">Réalisateur: </span><span v-for="(director, index) in result.realisateurs" :key="director.id">{{ director.prenom }} {{ director.nom }}<span v-if="(index+1) != result.realisateurs.length">, </span></span></div>
            <div><span class="subtitle-2">Distributeur: </span><span v-for="distributor in result.distributor" :key="distributor.id">{{ distributor.nom }}</span></div>
          </div>
          <div>
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              readonly
              color="yellow darken-3"
              background-color="grey darken-1"
              :value="result.rating"
            ></v-rating>
          </div>
        </div>

        <div
          v-if="!purchases.some((data) => data == result.id)"
          class="d-flex flex-column justify-space-around ml-12 mr-4"
        >
          <v-btn outlined color="primary" @click="buy(result)" v-if="result.price">Acheter</v-btn>
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
      results: [],
      newPurchase: null,
      error: false,
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
      if (!this.movies && !this.tv_shows && !this.musics) {
        this.error = true;
      } else {
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
      }
    },

    buy(result) {
      let newPurchase = {
        itemId: result.id,
        itemType: result.type,
        itemTitle: result.title,
        profileId: this.profile.id,
        distributorId: result.distributorId,
        productorId: result.productorId,
        artistsIds: result.artistsIds ? result.artistsIds : [],
        actorsIds: result.actorsIds ? result.actorsIds : [],
        directorsIds: result.directorsIds ? result.directorsIds : [],
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
          response.data.forEach((element) => {
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
