<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <div class="d-flex align-baseline ma-6">
      <h1 class="mr-12">Profil de l'utilisateur</h1>
      <v-btn
        text
        color="primary"
        class="ma-0 pa-0"
        :to="{ name: 'rechercher' }"
      >
        Retour à l'accueil
      </v-btn>
    </div>

    <!-- Général -->
    <v-card width="80%" class="ma-6 pa-3">
      <v-card-title>Réglages</v-card-title>
      <v-card-text class="d-flex justify-space-between flex-wrap">
        <div style="max-width: 25%">
          <h4>Age</h4>
          <v-text-field
            v-model="profile.age"
            suffix="ans"
            style="max-width: 100px"
          ></v-text-field>
        </div>

        <div style="max-width: 25%">
          <h4>Sexe</h4>
          <v-radio-group v-model="profile.sex" row>
            <v-radio label="Femme" value="woman"></v-radio>
            <v-radio label="Homme" value="man"></v-radio>
            <v-radio label="Indéterminé" value="whoknows"></v-radio>
          </v-radio-group>
        </div>

        <div style="max-width: 25%">
          <h4>Budget mensuel maximum</h4>
          <v-text-field
            v-model="profile.maxBudget"
            suffix="€"
            style="max-width: 100px"
          ></v-text-field>
        </div>

        <div style="max-width: 25%">
          <h4>Temps consommation moyen mensuel</h4>
          <v-text-field
            v-model="profile.averageConsumptionTime"
            suffix="heures"
            style="max-width: 100px"
          ></v-text-field>
        </div>
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between">
        <v-spacer></v-spacer>
        <v-btn text color="primary">Sauvegarder</v-btn>
      </v-card-actions>
    </v-card>

    <!-- Films et séries -->
    <v-card width="80%" class="ma-6 pa-3">
      <v-card-title>Films</v-card-title>

      <v-card-text>
        <h4>Genres préférés</h4>
        <div v-if="video_genres.length == 0" class="mt-2 mb-4">
          Aucun genre n'a été trouvé
        </div>
        <div class="d-flex flex-wrap">
          <v-checkbox
            v-model="profile.preferedVideoGenres[genre]"
            v-for="genre in video_genres"
            :key="genre"
            :label="genre"
            style="min-width: 200px;"
          ></v-checkbox>
        </div>

        <h4>Réalisateurs préférés</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;">
            <v-autocomplete
              hide-details
              prepend-inner-icon="mdi-plus"
              label="Ajouter nouveau"
            >
            </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="director in profile.preferedDirectors"
              :key="director"
              class="mx-2"
              color="primary"
              outlined
              close
              @click:close="deletePreferedMovieDirector(director)"
            >
              {{ director }}
            </v-chip>
          </div>
        </div>

        <h4>Acteurs préférés</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;">
            <v-autocomplete
              hide-details
              prepend-inner-icon="mdi-plus"
              label="Ajouter nouveau"
            >
            </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="actor in profile.preferedActors"
              :key="actor"
              class="mx-2"
              color="primary"
              outlined
              close
              @click:close="deletePreferedMovieActor(actor)"
            >
              {{ director }}
            </v-chip>
          </div>
        </div>

        <h4>Préférences offres</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;" id="app">
            <!-- Switch -->
            <div style="max-width: 100px;"  >
              <v-sheet
              >
                <v-switch
                  v-model="prefAb"
                  flat
                  :label="`${prefAb.toString()}`"
                ></v-switch>
                <v-switch
                  v-model="prefDo"
                  flat
                  :label="`${prefDo.toString()}`"
                ></v-switch>
              </v-sheet>
            </div>
          </div>
        </div>
          
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between">
        <v-spacer></v-spacer>
        <v-btn text color="primary">Sauvegarder</v-btn>
      </v-card-actions>
    </v-card>

    <!-- Musique -->
    <v-card width="80%" class="ma-6 pa-3">
      <v-card-title>Musique</v-card-title>

      <v-card-text>
        <h4>Genres préférés</h4>
        <div v-if="music_genres && music_genres.length == 0" class="mt-2 mb-4">
          Aucun genre n'a été trouvé
        </div>
        <div class="d-flex flex-wrap">
          <v-checkbox
            v-model="profile.preferedMusicGenres[genre]"
            v-for="(genre, index) in music_genres"
            :key="index"
            :label="genre"
            style="min-width: 200px;"
          ></v-checkbox>
        </div>

        <h4>Artistes préférés</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;">
            <v-autocomplete
              hide-details
              prepend-inner-icon="mdi-plus"
              label="Ajouter nouveau"
            >
            </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="artist in profile.preferedMusicArtists"
              :key="artist"
              class="mx-2"
              color="primary"
              outlined
              close
              @click:close="deletePreferedMusicArtist(artist)"
            >
              {{ artist }}
            </v-chip>
          </div>
        </div>

        <h4>Préférences offres</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" id="app">
            <!-- Switch -->
            <div style="max-width: 100px;"  >
              <v-sheet
              >
                <v-switch
                  v-model="prefAbM"
                  flat
                  :label="`${prefAbM.toString()}`"
                ></v-switch>
                <v-switch
                  v-model="prefDoM"
                  flat
                  :label="`${prefDoM.toString()}`"
                ></v-switch>
              </v-sheet>
            </div>
          </div>
        </div>
      </v-card-text>

      <v-card-actions class="d-flex justify-space-between">
        <v-spacer></v-spacer>
        <v-btn text color="primary">Sauvegarder</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<style scoped>
div {
  display: inline-block;
}
</style>

<script>
import { mapState } from "vuex";
export default {
  name: "Profil",


  data() {
    return {
      video_genres: [],
      directors: [],
      actors: [],
      music_genres: [],
      artists: [],
      prefAb: ['Abonnement'],
      prefDo: ['Téléchargement'],
      prefAbM: ['Abonnement'],
      prefDoM: ['Téléchargement']
    };
  },

  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if (this.profile == null) this.$router.push({ name: "login" });
    else {
      this.getAllArtists();
      this.getAllDirectors();
      this.getAllActors();
      this.getAllMusicGenres();
      this.getAllVideoGenres();
    }
  },

  methods: {

    // Data from db
    getAllDirectors() {
      this.$axios.get("/db/directors").then((response) => {
        this.directors = response.body;
      });
    },

    getAllActors() {
      this.$axios.get("/db/actors").then((response) => {
        this.actors = response.body;
      });
    },

    getAllArtists() {
      this.$axios.get("/db/artists").then((response) => {
        this.artists = response.body;
      });
    },

    getAllVideoGenres() {
      this.$axios.get("/db/video_genres").then((response) => {
        this.video_genres = response.body;
      });
    },

    getAllMusicGenres() {
      this.$axios.get("/db/music_genres").then((response) => {
        this.music_genres = response.body;
      });
    },

    // Prefs

    addPreferedDirector(director) {
      this.$axios.post("/directors/preferred", director).then((response) => {
        this.results = response.body;
      });
    },

    deletePreferedActor(actor) {
      this.$axios.delete("/directors/preferred", actor).then((response) => {
        this.results = response.body;
      });
    },

    addPreferedMusicArtist(artist) {
      this.$axios.post("/directors/preferred", artist).then((response) => {
        this.results = response.body;
      });
    },

    deletePreferedMusicArtist(artist) {
      this.$axios.delete("/directors/preferred", artist).then((response) => {
        this.results = response.body;
      });
    },
  },
};
</script>
