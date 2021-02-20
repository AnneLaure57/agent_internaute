<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <div class="d-flex align-baseline ma-6">
      <h1 class="mr-12">Profil de l'utilisateur</h1>
      <v-btn text color="primary" class="ma-0 pa-0" :to="{ name: 'rechercher' }">
        Retour à l'accueil
        </v-btn>
    </div>

    <!-- Général -->
    <v-card width="80%" class="ma-6 pa-3">
      <v-card-title>Réglages</v-card-title>
      <v-card-text class="d-flex justify-space-between flex-wrap">
        <div style="max-width: 25%">
          <h4>Age</h4>
          <v-text-field v-model="age" suffix="ans" style="max-width: 100px"></v-text-field>
        </div>

        <div style="max-width: 25%">
          <h4>Sexe</h4>
          <v-radio-group v-model="sex" row>
            <v-radio label="Femme" value="woman"></v-radio>
            <v-radio label="Homme" value="man"></v-radio>
            <v-radio label="Indéterminé" value="whoknows"></v-radio>
          </v-radio-group>
        </div>

        <div style="max-width: 25%">
          <h4>Budget mensuel maximum</h4>
          <v-text-field v-model="budget" suffix="€" style="max-width: 100px"></v-text-field>
        </div>

        <div style="max-width: 25%">
          <h4>Temps consommation moyen mensuel</h4>
          <v-text-field v-model="time" suffix="heures" style="max-width: 100px"></v-text-field>
        </div>
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between">
        <v-spacer></v-spacer>
        <v-btn text color="primary">Sauvegarder</v-btn>
      </v-card-actions>
    </v-card>

    <!-- Films -->
    <v-card width="80%" class="ma-6 pa-3">
      <v-card-title>Films</v-card-title>

      <v-card-text>
        <h4>Genres préférés</h4>
        <div v-if="movies_genres.length == 0" class="mt-2 mb-4">Aucun genre n'a été trouvé</div>
        <div class="d-flex flex-wrap">
          <v-checkbox
            v-model="movies_prefered_genres[genre]"
            v-for="genre in movies_genres"
            :key="genre"
            :label="genre"
            style="min-width: 200px;"
          ></v-checkbox>
        </div>

        <h4>Réalisateurs préférés</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;">
            <v-autocomplete hide-details prepend-inner-icon="mdi-plus" label="Ajouter nouveau"> </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="director in movies_prefered_directors"
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
            <v-autocomplete hide-details prepend-inner-icon="mdi-plus" label="Ajouter nouveau"> </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="actor in movies_prefered_actors"
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
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between">
        <v-spacer></v-spacer>
        <v-btn text color="primary">Sauvegarder</v-btn>
      </v-card-actions>
    </v-card>

    <!-- Séries -->
    <v-card width="80%" class="ma-6 pa-3">
      <v-card-title>Séries</v-card-title>

      <v-card-text>
        <h4>Genres préférés</h4>
        <div v-if="tv_shows_genres.length == 0" class="mt-2 mb-4">Aucun genre n'a été trouvé</div>
        <div class="d-flex flex-wrap">
          <v-checkbox
            v-model="tv_shows_prefered_genres[genre]"
            v-for="genre in tv_shows_genres"
            :key="genre"
            :label="genre"
            style="min-width: 200px;"
          ></v-checkbox>
        </div>

        <h4>Réalisateurs préférés</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;">
            <v-autocomplete hide-details prepend-inner-icon="mdi-plus" label="Ajouter nouveau"> </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="director in tv_shows_prefered_directors"
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
            <v-autocomplete hide-details prepend-inner-icon="mdi-plus" label="Ajouter nouveau"> </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="actor in tv_shows_prefered_actors"
              :key="actor"
              class="mx-2"
              color="primary"
              outlined
              close
              @click:close="deletePreferedTvShowActor(actor)"
            >
              {{ director }}
            </v-chip>
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
        <div v-if="musics_genres.length == 0" class="mt-2 mb-4">Aucun genre n'a été trouvé</div>
        <div class="d-flex flex-wrap">
          <v-checkbox
            v-model="musics_prefered_genres[genre]"
            v-for="genre in musics_genres"
            :key="genre"
            :label="genre"
            style="min-width: 200px;"
          ></v-checkbox>
        </div>

        <h4>Artistes préférés</h4>
        <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
          <div class="mr-10" style="max-width: 400px;">
            <v-autocomplete hide-details prepend-inner-icon="mdi-plus" label="Ajouter nouveau"> </v-autocomplete>
          </div>

          <div class="ml-10">
            <v-chip
              v-for="artist in musics_prefered_artists"
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
export default {
  name: "Profil",

  data() {
    return {
      age: 20,
      sex: "whoknows",
      budget: 40,
      time: 60,
      movies_genres: ["action", "horreur", "romance", "science fiction", "suspense"],
      movies_prefered_genres: {
        action: true,
        horreur: false,
        romance: false,
        "science fiction": true,
        suspense: false,
      },
      movies_directors: ["Quentin Tarantino"],
      movies_prefered_directors: ["Quentin Tarantino"],
      movies_actors: [],
      movies_prefered_actors: [],
      tv_shows_genres: ["action", "comédie", "romance", "science fiction"],
      tv_shows_prefered_genres: {
        action: true,
        horreur: false,
        romance: false,
        "science fiction": true,
        suspense: false,
      },
      tv_shows_directors: [],
      tv_shows_prefered_directors: [],
      tv_shows_actors: [],
      tv_shows_prefered_actors: [],
      musics_genres: ["acid", "club", "metal", "pop", "reggae"],
      musics_prefered_genres: {
        acid: false,
        reggae: true,
      },
      musics_artists: [],
      musics_prefered_artists: ["Lorie"],
    };
  },

  mounted() {},

  methods: {
    search() {
      this.$axios.get("/profil").then((response) => {
        this.results = response.body;
      });
    },

    addPreferedMovieDirector(director) {
      this.$axios.post("/movies_directors/preferred", director).then((response) => {
        this.results = response.body;
      });
    },

    deletePreferedMovieDirector(director) {
      this.$axios.delete("/movies_directors/preferred", director).then((response) => {
        this.results = response.body;
      });
    },

    addPreferedMovieActor(actor) {
      this.$axios.post("/movies_directors/preferred", actor).then((response) => {
        this.results = response.body;
      });
    },

    deletePreferedMovieActor(actor) {
      this.$axios.delete("/movies_directors/preferred", actor).then((response) => {
        this.results = response.body;
      });
    },

    addPreferedMusicArtist(artist) {
      this.$axios.post("/movies_directors/preferred", artist).then((response) => {
        this.results = response.body;
      });
    },

    deletePreferedMusicArtist(artist) {
      this.$axios.delete("/movies_directors/preferred", artist).then((response) => {
        this.results = response.body;
      });
    },
  },
};
</script>
