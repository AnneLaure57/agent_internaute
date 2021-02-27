<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center flex-grow-1"
  >
    <div class="d-flex align-baseline ma-6">
      <h1 class="mr-12">Historique de consommation</h1>
      <v-btn text color="primary" class="ma-0 pa-0" :to="{ name: 'rechercher' }">
        Retour à l'accueil
      </v-btn>
    </div>

    <v-card v-for="medium in media" :key="medium.title" style="width: 80%" class="ma-5">
      <v-card-text class="d-flex justify-space-between">
        <v-img contain max-height="200px" max-width="200px" width="200px" src="../assets/img/default.png"></v-img>
        <div class="d-flex flex-column flex-grow-1 mx-4">
          <span class="title">{{ medium.title }}</span>
          <span>{{ medium.year }}</span>
          <span>Visionné le {{ medium.view_date }}</span>
        </div>
        <div class="d-flex flex-column align-center justify-space-around ml-12 mr-4">
            <span>Note des internautes</span>
          <v-rating
            empty-icon="mdi-star-outline"
            full-icon="mdi-star"
            half-icon="mdi-star-half"
            length="5"
            size="24"
            color="yellow darken-3"
            background-color="grey darken-1"
            :value="medium.rating"
          ></v-rating>
          <v-btn color="primary">Noter</v-btn>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<style scoped></style>

<script>
import { mapState } from "vuex";
export default {
  name: "Historique",

  data() {
    return {
      media: [
        //{ title: "Les bronzés", year: 1978, rating: 3.7, view_date: "11/01/2021" },
      ],
    };
  },
  
  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if(this.profile == null) this.$router.push({ name: "login" });
    this.getPurchases();
  },

  methods: {
    //

    getPurchases() {
      console.log("appelle de la méthode " + typeof(this.profile.id));
      this.$axios.get("/purchases/"+ this.profile.id).then(
        (response) => {
          console.log(response.data);
          this.media = response.data;
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
