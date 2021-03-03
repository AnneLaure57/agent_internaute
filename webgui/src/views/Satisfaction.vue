<template>
	<!-- <div
		style="width: 100%; height: 100%; background-color: #efefef"
		class="d-flex flex-column align-center justify-center flex-grow-1"
	> -->
		<v-card
			width="50%"
			max-width="500px"
			class="d-flex flex-column ma-10 pa-3"
		>
			<v-card-title>Historique des satisfactions moyennes par mois</v-card-title>

			<v-card-text class="d-flex">
				
			</v-card-text>
		</v-card>

	<!-- </div> -->
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
