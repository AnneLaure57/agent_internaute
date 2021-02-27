<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center justify-center flex-grow-1"
  >
    <v-card width="50%" max-width="500px" class="d-flex flex-column ma-10 pa-3">
      <v-card-title>Connexion</v-card-title>
      <v-card-subtitle>Veuillez vous identifier</v-card-subtitle>

      <v-card-text class="d-flex">
        <v-text-field v-model="login" placeholder="Identifiant"></v-text-field>
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between">
        <div>
          Pas encore de compte ? <br /><a @click="goToRegister()"
            >Enregistrez votre profil.</a
          >
        </div>
        <v-spacer></v-spacer>
        <v-btn
          text
          color="primary"
          @click="connexion()"
          :disabled="login.length == 0"
          >Connexion</v-btn
        >
      </v-card-actions>
    </v-card>
    <div>
      <v-snackbar v-model="snackbar" color="red" outlined :timeout="5000">
        Le profil {{ login }} n'existe pas, mais vous pouvez l'enregistrer.
      </v-snackbar>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Login",

  data() {
    return {
      login: "",
      snackbar: false,
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
