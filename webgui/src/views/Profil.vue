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

    <v-card style="width:80%" class="d-flex flex-column ma-3 pa-3">
      <!-- Général -->
      <div>
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

          <div style="max-width: 300px">
            <h4>Budget mensuel maximum</h4>
            <v-text-field
              v-model="profile.maxBudget"
              suffix="€"
              style="max-width: 100px"
            ></v-text-field>
          </div>

          <div style="max-width: 300px">
            <h4>Temps consommation moyen mensuel</h4>
            <v-text-field
              v-model="profile.averageConsumptionTime"
              suffix="heures"
              style="max-width: 100px"
            ></v-text-field>
          </div>
        </v-card-text>
      </div>

      <!-- Films et séries -->
      <div class="d-flex flex-column justify-space-between mr-2">
        <div>
          <v-card-title class="pt-0">Films et séries</v-card-title>

          <v-card-text>
            <h4>Genres préférés</h4>
            <div v-if="video_genres.length == 0" class="mt-2 mb-4">
              Aucun genre n'a été trouvé
            </div>
            <div class="d-flex flex-wrap">
              <v-checkbox
                hide-details
                v-model="genre.selected"
                :label="genre.name"
                style="min-width: 200px;"
                v-for="genre in video_genres"
                :key="genre.id"
              ></v-checkbox>
            </div>

            <h4 class="mt-8">Réalisateurs préférés</h4>
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

            <h4 class="mt-8">Acteurs préférés</h4>
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

            <h4 class="mt-8">Préférences offres</h4>
            <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
              <!-- Switch -->
              <div style="max-width: 300px;">
                <v-switch
                  v-model="profile.preferDownloadsForVideos"
                  color="primary"
                  inset
                  flat
                  label="Préférer le téléchargement"
                ></v-switch>
              </div>
            </div>
          </v-card-text>
        </div>
      </div>

      <!-- Musique -->
      <div class="d-flex flex-column justify-space-between ml-2">
        <div>
          <v-card-title class="pt-0">Musique</v-card-title>

          <v-card-text>
            <h4>Genres préférés</h4>
            <div
              v-if="music_genres && music_genres.length == 0"
              class="mt-2 mb-4"
            >
              Aucun genre n'a été trouvé
            </div>
            <div class="d-flex flex-wrap">
              <v-checkbox
                style="min-width: 200px;"
                v-for="genre in music_genres"
                :key="genre.id"
                v-model="genre.selected"
                hide-details
                :label="genre.name"
              ></v-checkbox>
            </div>

            <h4 class="mt-8">Artistes préférés</h4>
            <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
              <div class="mr-10" style="max-width: 400px;">

                <v-autocomplete
                  label="Ajouter nouveau"
                  v-model="selected_artists"
                  :items="artists"
                  item-text="name"
                  item-value="id"
                  multiple
                  hide-details
                  prepend-inner-icon="mdi-plus"
                >
                  <template v-slot:selection="{ item, index }">
                    <span
                      class="mr-1"
                      :key="item"
                      v-if="index === 0"
                    >
                      {{ item.name }}
                    </span>
                    <span
                      v-if="index === 1"
                      class="grey--text caption"
                      >(+{{ selected_artists.length - 1 }} autres)</span
                    >
                  </template>
                </v-autocomplete>
              </div>

              <div class="ml-10">
                <v-chip
                  v-for="artist in selected_artists"
                  :key="artist.name"
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

            <h4 class="mt-8">Préférences offres</h4>
            <div class="d-flex flex-wrap align-baseline mt-2 mb-4">
              <!-- Switch -->
              <div style="max-width: 300px;">
                <v-switch
                  v-model="profile.preferDownloadsForMusics"
                  flat
                  color="primary"
                  inset
                  label="Préferer le téléchargement"
                ></v-switch>
              </div>
            </div>
          </v-card-text>
        </div>

        <v-card-actions class="d-flex justify-space-between">
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="savePrefs()">Sauvegarder</v-btn>
        </v-card-actions>
      </div>
    </v-card>

    <!-- Snackbar -->
    <div class="d-flex flex-grow-0">
      <v-snackbar
        v-model="snackbar"
        :color="snackbar_status_ok ? 'success' : 'error'"
        outlined
        absolute
        bottom
        :timeout="5000"
      >
        {{
          snackbar_status_ok
            ? "Le profil a été mis à jour"
            : "Le profil n'a pas été mis à jour"
        }}
      </v-snackbar>
    </div>
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
      selected_artists: [],
      artists: [],
      snackbar: false,
      snackbar_status_ok: false,
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
    getProfile() {
      this.$axios.get("/profil/" + this.profile.id).then((response) => {
        this.$store.commit("setProfile", response.data);
      });
    },

    savePrefs() {
      console.log(this.profile);
      this.profile.preferedVideoGenres = this.video_genres
        .filter((obj) => obj.selected)
        .map((obj) => obj.id);
      this.profile.preferedMusicGenres = this.music_genres
        .filter((obj) => obj.selected)
        .map((obj) => obj.id);
      this.$axios.put("/profil/" + this.profile.id, this.profile).then(
        (response) => {
          if (response.status == 200) {
            this.getProfile();
            this.snackbar_status_ok = true;
            this.snackbar = true;
          } else {
            this.getProfile();
            this.snackbar_status_ok = false;
            this.snackbar = true;
          }
        },
        () => {
          this.getProfile();
          this.snackbar_status_ok = false;
          this.snackbar = true;
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
        this.artists.forEach(
          (artist) =>
           this.selected_artists = this.profile.artists.includes(
              artist.id
            ))
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
