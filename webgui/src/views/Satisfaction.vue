<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center flex-grow-1"
  >
    <div class="d-flex align-baseline ma-6">
      <h1 class="mr-12">Historique des satisfactions moyennes par mois</h1>
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
      style="width: 60%"
      class="ma-5"
    >
      <v-card-text class="d-flex justify-space-between">
       
      </v-card-text>
    </v-card>
  </div>
</template>
   

<script>
	import { mapState } from "vuex";
	export default {
		name: "Satisfaction",

		data() {
			return {
				satisfactions: []
			};
		},

		computed: {
			...mapState(["profile"]),
		},

		mounted() {
            this.$axios.get("/satisfaction/" + this.profile.id).then( (response) => {
                console.log(response.data);
            }, () => {
                this.satisfactions = [];
            });
		}
	};
</script>
