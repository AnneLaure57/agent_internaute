<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center flex-grow-1"
  >
    <div class="d-flex align-baseline ma-6">
      <h1 class="mr-12">Historique de consommation</h1>
      <v-btn
        text
        color="primary"
        class="ma-0 pa-0"
        :to="{ name: 'rechercher' }"
      >
        Retour à l'accueil
      </v-btn>
    </div>

    <v-card
      v-for="purchase in purchases"
      :key="purchase.id"
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
          <span class="title">{{ purchase.itemTitle }}</span>
          <span>Acheté et visionné le {{ dateFromTimestamp(purchase.purchaseDate) }}</span>
        </div>
        <div
          class="d-flex flex-column align-center justify-space-around ml-12 mr-4"
        >
          <span>Note des internautes</span>
          <v-rating
            empty-icon="mdi-star-outline"
            full-icon="mdi-star"
            half-icon="mdi-star-half"
            length="5"
            size="24"
            color="yellow darken-3"
            background-color="grey darken-1"
            :value="purchase.rating"
          ></v-rating>
          <v-btn color="primary" @click="openRatePurchaseDialog(purchase)">Noter</v-btn>
        </div>
      </v-card-text>
    </v-card>

    <v-dialog
      v-model="dlg_ratings"
      width="600px"
      persistent
      v-if="editPurchase"
    >
      <v-card>
        <v-card-title
          >Donner vos notes pour {{ editPurchase.itemTitle }}</v-card-title
        >
        <v-card-text>
          <h4>Note générale de l'oeuvre</h4>
          <v-rating
            empty-icon="mdi-star-outline"
            full-icon="mdi-star"
            half-icon="mdi-star-half"
            length="5"
            size="24"
            color="yellow darken-3"
            background-color="grey darken-1"
            v-model="editPurchase.mediumRating"
          ></v-rating>

          <h4>Note du distributeur</h4>
          <v-rating
            empty-icon="mdi-star-outline"
            full-icon="mdi-star"
            half-icon="mdi-star-half"
            length="5"
            size="24"
            color="yellow darken-3"
            background-color="grey darken-1"
            v-model="editPurchase.distributorRating"
          ></v-rating>

          <h4>Note du producteur</h4>
          <v-rating
            empty-icon="mdi-star-outline"
            full-icon="mdi-star"
            half-icon="mdi-star-half"
            length="5"
            size="24"
            color="yellow darken-3"
            background-color="grey darken-1"
            v-model="editPurchase.productorRating"
          ></v-rating>

          <h4>Notes des artistes</h4>
          <div v-for="medium in editPurchase.artistsRating" :key="medium.id">
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              color="yellow darken-3"
              background-color="grey darken-1"
              v-model="medium.artistsRating"
            ></v-rating>
          </div>

          <h4>Notes des acteurs</h4>
          <div v-for="medium in editPurchase.actorsRating" :key="medium.id">
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              color="yellow darken-3"
              background-color="grey darken-1"
              v-model="medium.actorsRating"
            ></v-rating>
          </div>

          <h4>Notes des réalisateurs</h4>
          <div v-for="medium in editPurchase.directorsRating" :key="medium.id">
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              color="yellow darken-3"
              background-color="grey darken-1"
              v-model="medium.directorsRating"
            ></v-rating>
          </div>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="dlg_ratings = false"
            >Fermer</v-btn
          >
          <v-btn text color="primary" @click="sendRatings()"
            >Envoyer</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<style scoped></style>

<script>
import moment from "moment";
import { mapState } from "vuex";
export default {
  name: "Historique",

  data() {
    return {
      editPurchase: null,
      purchases: [],
      dlg_ratings: false,
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
    getPurchases() {
      this.$axios.get("/purchases/" + this.profile.id).then(
        (response) => {
          this.purchases = response.data;
          console.log(this.purchases);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    openRatePurchaseDialog(purchase) {
      this.editPurchase = purchase;
      this.dlg_ratings = true;
    },

    dateFromTimestamp(timestamp) {
      return timestamp ? moment.utc(timestamp).format("DD/MM/YYYY") : "";
    },

    moment(date) {
      if (date) return moment.utc(date).format("YYYY-MM-DD HH:mm:ss");
      else return "inconnu";
    },

    sendRatings() {
      this.$axios.post("/ratings", this.editPurchase).then(
        (response) => {
          this.getPurchases();
          this.dlg_ratings = false
          console.log(response.data);
        },
        (error) => {
          this.dlg_ratings = false
          console.log(error);
        }
      );
    },
  },
};
</script>
