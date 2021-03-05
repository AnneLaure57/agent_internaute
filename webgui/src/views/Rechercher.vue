<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <!-- Recherche -->
    <div class="d-flex ma-5 justify-space-between" style="width: 80%">
      <v-card width="35%">
        <div class="d-flex align-center justify-space-between">
          <v-card-title>Rechercher un titre spécifique</v-card-title>
          <v-card-actions>
            <v-btn
              rounded
              xlarge
              color="primary"
              class="mx-3"
              :disabled="
                (!movies1 && !tv_shows1 && !musics1) ||
                  searchfield == null ||
                  searchfield.length == 0
              "
              @click="searchTitle()"
            >
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </v-card-actions>
        </div>

        <v-card-text class="d-flex flex-wrap py-2">
          <v-text-field
            class="mr-8"
            v-model="searchfield"
            placeholder="Saisissez un ou plusieurs mots-clés"
            clearable
          ></v-text-field>
          <div class="d-flex">
            <v-checkbox
              v-model="movies1"
              label="Films"
              class="mx-4"
            ></v-checkbox>
            <v-checkbox
              v-model="tv_shows1"
              label="Séries"
              class="mx-4"
            ></v-checkbox>
            <v-checkbox
              v-model="musics1"
              label="Musique"
              class="mx-4"
            ></v-checkbox>
          </div>
        </v-card-text>
      </v-card>

      <div class="d-flex align-center justify-center">
        <span class="title">ou</span>
      </div>

      <v-card width="55%">
        <div class="d-flex align-center justify-space-between">
          <v-card-title>Je sais pas trop, un truc comme ça ...</v-card-title>
          <v-card-actions>
            <v-btn
              rounded
              xlarge
              color="primary"
              class="mx-3"
              :disabled="!movies2 && !tv_shows2 && !musics2"
              @click="searchFilters()"
            >
              <v-icon>mdi-magnify</v-icon>
            </v-btn>
          </v-card-actions>
        </div>

        <v-card-text class="d-flex align-center justify-space-between mb-3">
          <div class="d-flex flex-column">
            <v-checkbox
              v-model="movies2"
              label="Films"
              class="mx-4 my-0"
              dense
            ></v-checkbox>
            <v-checkbox
              v-model="tv_shows2"
              label="Séries"
              class="mx-4 my-0"
              dense
            ></v-checkbox>
            <v-checkbox
              v-model="musics2"
              label="Musique"
              class="mx-4 my-0"
              dense
            ></v-checkbox>
          </div>

          <div class="d-flex flex-column flex-grow-1 ml-12 mr-3 mt-0 pt-0">
            <div class="d-flex align-baseline mt-0 mb-3" v-if="musics2">
              <span class="subtitle-1 mr-4" style="min-width: 100px"
                >Genres musicaux:</span
              >
              <v-autocomplete
                v-model="selected_music_genres"
                :items="music_genres"
                item-text="name"
                multiple
                hide-details
                return-object
                dense
                clearable
              >
              </v-autocomplete>
            </div>

            <div
              class="d-flex align-baseline mt-0 mb-3"
              v-if="movies2 || tv_shows2"
            >
              <span class="subtitle-1 mr-4" style="min-width: 100px"
                >Genres:</span
              >
              <v-autocomplete
                v-model="selected_video_genres"
                :items="video_genres"
                item-text="name"
                multiple
                hide-details
                return-object
                dense
                clearable
              >
              </v-autocomplete>
            </div>

            <div class="d-flex align-baseline  mt-0 mb-3" v-if="musics2">
              <span class="subtitle-1 mr-4" style="min-width: 100px"
                >Artistes:</span
              >
              <v-autocomplete
                v-model="selected_artists"
                :items="artists"
                item-text="name"
                multiple
                hide-details
                return-object
                dense
                clearable
              >
              </v-autocomplete>
            </div>

            <div
              class="d-flex align-baseline mt-0 mb-3"
              v-if="movies2 || tv_shows2"
            >
              <span class="subtitle-1 mr-4" style="min-width: 100px"
                >Avec:</span
              >
              <v-autocomplete
                v-model="selected_actors"
                :items="actors"
                item-text="name"
                multiple
                hide-details
                return-object
                dense
                clearable
              >
              </v-autocomplete>
            </div>

            <div class="d-flex align-baseline mt-0" v-if="movies2 || tv_shows2">
              <span class="subtitle-1 mr-4" style="min-width: 100px"
                >Réalisé par:</span
              >
              <v-autocomplete
                v-model="selected_directors"
                :items="directors"
                item-text="name"
                multiple
                hide-details
                return-object
                dense
                clearable
              >
              </v-autocomplete>
            </div>
          </div>
        </v-card-text>
      </v-card>
    </div>

    <!-- Abonnements -->
    <div style="width: 80%" class="d-flex ma-5">
      <h2 v-if="subscriptions.length > 0">Abonnements</h2>
    </div>

    <div style="width: 80%" class="d-flex justify-space-between">
      <v-card
        v-for="subscription in subscriptions"
        :key="subscription.id"
        style="width: 22%"
      >
        <v-card-title>{{ subscription.prix }} €</v-card-title>
        <v-card-subtitle
          >Engagement de {{ subscription.duree }} jours</v-card-subtitle
        >
        <v-card-text class="d-flex justify-space-between">{{
          subscription.distributeur
        }}</v-card-text>
        <v-card-actions class="d-flex justify-center ma-2">
          <v-btn color="primary" min-width="150px" @click="buy(result, true)"
            >S'abonner</v-btn
          >
        </v-card-actions>
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
          max-width="150px"
          width="150px"
          src="../assets/img/default.png"
        ></v-img>
        <div class="d-flex flex-column justify-space-between flex-grow-1 mx-4">
          <div class="d-flex flex-column">
            <span class="title"
              >{{ result.titre }} ({{ result.dateSortie }})</span
            >
          </div>
          <div class="d-flex flex-column">
            <div>
              <span class="subtitle-2">Acteurs: </span>
              <span v-for="(actor, index) in result.acteurs" :key="actor.id"
                >{{ actor.prenom }} {{ actor.nom
                }}<span v-if="index + 1 != result.acteurs.length"
                  >,
                </span></span
              >
            </div>
            <div>
              <span class="subtitle-2">Réalisateur: </span
              ><span
                v-for="(director, index) in result.realisateurs"
                :key="director.id"
                >{{ director.prenom }} {{ director.nom
                }}<span v-if="index + 1 != result.realisateurs.length"
                  >,
                </span></span
              >
            </div>
            <div>
              <span class="subtitle-2">Distributeur: </span
              ><span>{{ result.distributeur }}</span>
            </div>
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
              :value="result.note"
            ></v-rating>
          </div>
        </div>

        <div
          v-if="!purchases.some((data) => data == result.id)"
          class="d-flex flex-column justify-space-around ml-12 mr-4"
        >
          <v-btn
            outlined
            color="primary"
            @click="buy(result, false)"
            :disabled="!result.prix"
            min-width="150px"
            >{{
              result.prix != null
                ? "Acheter " + result.prix + "€"
                : "Pas d'achat"
            }}</v-btn
          >

          <v-chip color="primary" label>
            Disponible avec abonnement</v-chip
          >
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
      movies1: true,
      tv_shows1: false,
      musics1: false,
      movies2: true,
      tv_shows2: false,
      musics2: false,
      purchases: [],
      results: [],
      subscriptions: [],
      newPurchase: null,
      error: false,
      selected_video_genres: [],
      video_genres: [],
      selected_directors: [],
      directors: [],
      selected_actors: [],
      actors: [],
      selected_music_genres: [],
      music_genres: [],
      selected_artists: [],
      artists: [],
    };
  },

  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if (this.profile == null) this.$router.push({ name: "login" });
    this.getPurchases();
    this.getAllArtists();
    this.getAllDirectors();
    this.getAllActors();
    this.getAllMusicGenres();
    this.getAllVideoGenres();
  },

  methods: {
    searchTitle() {
      let newSearch = {
        searchField: this.searchfield,
        movies: this.movies1,
        tvShows: this.tv_shows1,
        musics: this.musics1,
        profileId: this.profile.id,
      };
      this.$axios.post("/search", newSearch).then((response) => {
        this.results = response.data.oeuvres;
        this.subscriptions = response.data.abonnements;
      });
    },

    searchFilters() {
      let newSearch = {
        movies: this.movies2,
        tvShows: this.tv_shows2,
        musics: this.musics2,
        selectedVideoGenres: this.selected_video_genres,
        selectedMusicGenres: this.selected_music_genres,
        selectedArtists: this.selected_artists,
        selectedActors: this.selected_actors,
        selectedDirectors: this.selected_directors,
        profileId: this.profile.id,
      };
      this.$axios.post("/search?filter=true", newSearch).then((response) => {
        this.results = response.data.oeuvres;
        this.subscriptions = response.data.abonnements;
      });
    },

    buy(result, subscribe) {
      console.log(subscribe);
      let newPurchase = {
        prix: result.prix,
        itemType: result.itemType,
        id: result.id,
        titre: result.titre,
        description: result.description,
        dateSortie: result.dateSortie,
        note: result.note,
        distributeur: result.distributeur,
        producteur: result.producteur,
        genres: result.genres,
        acteurs: result.acteurs,
        realisateurs: result.realisateurs,
        artistes: result.artistes,
        profileId: this.profile.id,
      };
      // Ajout dans le tableau des purchases pour cacher les boutons
      this.purchases.push(result.id);

      this.$axios.post("/purchases", newPurchase).then(
        () => {
          this.$axios.get("/profil/" + this.profile.id).then((response) => {
            this.$store.commit("setProfile", response.data);
          });
        },
        (error) => {
          console.log(error);
        }
      );
    },

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

    // Data from db
    getAllDirectors() {
      this.$axios.get("/db/directors").then((response) => {
        this.directors = response.data;
      });
    },

    getAllActors() {
      this.$axios.get("/db/actors").then((response) => {
        this.actors = response.data;
      });
    },

    getAllArtists() {
      this.$axios.get("/db/artists").then((response) => {
        this.artists = response.data;
        this.selected_artists = this.artists.filter((obj) =>
          this.profile.preferedMusicArtists.includes(obj.id)
        );
      });
    },

    getAllVideoGenres() {
      this.$axios.get("/db/video_genres").then((response) => {
        this.video_genres = response.data;
        this.video_genres.forEach(
          (genre) =>
            (genre["selected"] = this.profile.preferedVideoGenres.includes(
              genre.id
            ))
        );
      });
    },

    getAllMusicGenres() {
      this.$axios.get("/db/music_genres").then((response) => {
        this.music_genres = response.data;
        this.music_genres.forEach(
          (genre) =>
            (genre["selected"] = this.profile.preferedMusicGenres.includes(
              genre.id
            ))
        );
      });
    },
  },
};
</script>
