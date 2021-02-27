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
          <v-btn color="primary" @click="buy(result)">Acheter</v-btn>
          <v-btn color="primary">S'abonner</v-btn>
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
      arrayBool:[],
      movies: false,
      tv_shows: false,
      musics: false,
      results: [
        { id : 1234, title: "Le parrain", year: 1972, rating: 4.5 },
        { id : 3214, title: "Le parrain 2", year: 1974, rating: 4.5 },
        { id : 3234, title: "Les bronzés", year: 1978, rating: 3.7 },
      ],
      newSearch: {
        searchfield: "",
        movies:false,
        tv_shows: false,
        musics: false,
        profile: null,

      },
      newPurchase: {
        rating: 0,
        itemId: 0,
        itemTitle: "",
        profile: null,

      }
    };
  },

		computed: {
			...mapState(["profile"]),
		},

		mounted() {
			if (this.profile == null) this.$router.push({ name: "login" });
		},

  methods: {
    // WITHOUT DTO
    /*search(searchfield, movies, tv_shows, musics) {
        console.log(searchfield);
        // push in arrayBool
        this.arrayBool.push(movies);
        this.arrayBool.push(tv_shows);
        this.arrayBool.push(musics);
        console.log(this.arrayBool);
        console.log(this.profile);
				this.$axios.get("/search", (this.searchfield, this.arrayBool, this.profile)).then((response) => {
					//TODO get Profil + type + title
          console.log(response);
					this.results = response.body;
				});
        this.arrayBool.splice(0);
		},*/
    search(searchfield, movies, tv_shows, musics) {
        this.newSearch.searchfield = searchfield;
        
        this.newSearch.movies = movies;
        this.newSearch.tv_shows = tv_shows;
        this.newSearch.musics = musics;

        this.newSearch.profile = this.profile;
        console.log(this.newSearch)
				this.$axios.get("/search", this.newSearch).then((response) => {
					//TODO get Profil + type + title
          console.log(response);
					this.results = response.body;
				});
		},
    buy(result) {
      // Ajout dans history
      this.newPurchase.rating = result.rating;
      this.newPurchase.itemTitle = result.title;
      this.newPurchase.itemId = result.id;
      this.newPurchase.profile = this.profile;
      console.log(this.newPurchase);
      this.$axios.post("/purchases", this.newPurchase).then(
        (response) => {
          console.log(response);
          // this.profile = response.data;
          // this.$store.commit("setProfile", response.data);
          // this.$router.push({ name: "profile" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>
