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
        Retour à la recherche
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
          width="150px"
          src="../assets/img/default.png"
        ></v-img>
        <div class="d-flex flex-column justify-space-between flex-grow-1 mx-4">
          <div class="d-flex flex-column">
            <span class="title"
              >{{ purchase.titre }} ({{ purchase.dateSortie }})</span
            >
          </div>
          <div class="d-flex flex-column">
            <div>
              <span class="subtitle-2">Acteurs: </span>
              <span v-for="(actor, index) in purchase.acteurs" :key="actor.id"
                >{{ actor.prenom }} {{ actor.nom
                }}<span v-if="index + 1 != purchase.acteurs.length"
                  >,
                </span></span
              >
            </div>
            <div>
              <span class="subtitle-2">Réalisateur: </span
              ><span
                v-for="(director, index) in purchase.realisateurs"
                :key="director.id"
                >{{ director.prenom }} {{ director.nom
                }}<span v-if="index + 1 != purchase.realisateurs.length"
                  >,
                </span></span
              >
            </div>
            <div>
              <span class="subtitle-2">Distributeur: </span
              ><span
                v-for="distributor in purchase.distributor"
                :key="distributor.id"
                >{{ distributor.nom }}</span
              >
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
              :value="purchase.rating"
            ></v-rating>
          </div>
        </div>

        <div
          class="d-flex flex-column align-center justify-space-around ml-12 mr-4"
        >
          <div>Vu le {{ dateFromTimestamp(purchase.viewDate) }}</div>
          <v-btn color="primary" @click="openRatePurchaseDialog(purchase)"
            >Noter</v-btn
          >
        </div>
      </v-card-text>
    </v-card>

    <v-dialog v-model="dlg_ratings" width="600px" persistent v-if="editPurchase">
      <v-card>
        <v-card-title
          >{{ editPurchase.titre }}</v-card-title
        >
        <v-card-text>
          <div class="d-flex flex-wrap align-center">
            <h4 class="primary--text">Note générale de l'oeuvre</h4>
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              color="yellow darken-3"
              background-color="grey darken-1"
              v-model="editPurchase.itemRating"
            ></v-rating>
          </div>

          <h4 class="primary--text mt-4">Note du distributeur</h4>
          <div class="d-flex flex-wrap align-center">
            <span class="subtitle-2 mr-6">{{
              editPurchase.distributeurId
            }}</span>
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              color="yellow darken-3"
              background-color="grey darken-1"
              v-model="editPurchase.distributeurRating"
            ></v-rating>
          </div>

          <h4 class="primary--text mt-4">Note du producteur</h4>
          <div class="d-flex flex-wrap align-center">
            <span class="subtitle-2 mr-6">{{ editPurchase.producteurId }}</span>
            <v-rating
              empty-icon="mdi-star-outline"
              full-icon="mdi-star"
              half-icon="mdi-star-half"
              length="5"
              size="24"
              color="yellow darken-3"
              background-color="grey darken-1"
              v-model="editPurchase.producteurRating"
            ></v-rating>
          </div>
          <div v-if="editPurchase.artistes">
            <h4 class="primary--text mt-4">Notes des artistes</h4>
            <div
              v-for="artist in editPurchase.artistes"
              :key="artist.id"
              class="d-flex flex-wrap align-center"
            >
              <span class="subtitle-2 mr-6"
                >{{ artiste.prenom }} {{ artiste.nom }}</span
              >
              <v-rating
                empty-icon="mdi-star-outline"
                full-icon="mdi-star"
                half-icon="mdi-star-half"
                length="5"
                size="24"
                color="yellow darken-3"
                background-color="grey darken-1"
                v-model="artist.rating"
              ></v-rating>
            </div>
          </div>

          <div v-if="editPurchase.acteurs">
            <h4 class="primary--text mt-4">Notes des acteurs</h4>
            <div
              v-for="acteur in editPurchase.acteurs"
              :key="acteur.id"
              class="d-flex flex-wrap align-center"
            >
              <span class="subtitle-2 mr-6"
                >{{ acteur.prenom }} {{ acteur.nom }}</span
              >
              <v-rating
                empty-icon="mdi-star-outline"
                full-icon="mdi-star"
                half-icon="mdi-star-half"
                length="5"
                size="24"
                color="yellow darken-3"
                background-color="grey darken-1"
                v-model="acteur.rating"
              ></v-rating>
            </div>
          </div>

          <div v-if="editPurchase.realisateurs">
            <h4 class="primary--text mt-4">Notes des réalisateurs</h4>
            <div
              v-for="realisateur in editPurchase.realisateurs"
              :key="realisateur.id"
              class="d-flex flex-wrap align-center"
            >
              <span class="subtitle-2 mr-6"
                >{{ realisateur.prenom }} {{ realisateur.nom }}</span
              >
              <v-rating
                empty-icon="mdi-star-outline"
                full-icon="mdi-star"
                half-icon="mdi-star-half"
                length="5"
                size="24"
                color="yellow darken-3"
                background-color="grey darken-1"
                v-model="realisateur.rating"
              ></v-rating>
            </div>
          </div>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="dlg_ratings = false"
            >Fermer</v-btn
          >
          <v-btn text color="primary" @click="sendRatings()">Envoyer</v-btn>
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
    if (this.profile == null) this.$router.push({ name: "login" });
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
          this.dlg_ratings = false;
          console.log(response.data);
        },
        (error) => {
          this.dlg_ratings = false;
          console.log(error);
        }
      );
    },
  },
};
</script>
