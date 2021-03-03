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
		name: "Login",

		data() {
			return {
				satisfactions: []
			};
		},

		computed: {
			...mapState(["profile"]),
		},

		mounted() {
			if (this.profile != null) this.$router.push({ name: "rechercher" });
		},

		methods: {
			goToRegister() {
				this.$router.push({ name: "register" });
			},

			connexion() {
				this.$axios.get("/profil?name=" + this.login).then(
					(response) => {
						this.$store.commit("setProfile", response.data);
						this.$router.push({ name: "rechercher" });
					},
					() => {
						this.snackbar = true;
					}
				);
			},
		},
	};
</script>
