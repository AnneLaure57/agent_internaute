<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <v-card width="50%" max-width="400px" class="d-flex flex-column ma-10 pa-3">
      <v-card-title>Enregistrement nouveau profil</v-card-title>
      <v-card-subtitle>Merci de saisir les informations suivantes.</v-card-subtitle>
      <v-card-text class="d-flex flex-column">
        <div>
          <v-text-field
            class="mr-12"
            v-model="newProfile.name"
            placeholder="Identifiant"
            :rules="[rules.required, rules.password]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Age</h4>
          <v-text-field
            v-model="newProfile.age"
            suffix="ans"
            style="max-width: 100px"
            :rules="[rules.required, rules.password]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Sexe</h4>
          <v-radio-group v-model="newProfile.sex" row>
            <v-radio label="Femme" value="woman"></v-radio>
            <v-radio label="Homme" value="man"></v-radio>
            <v-radio label="Indéterminé" value="whoknows"></v-radio>
          </v-radio-group>
        </div>

        <div>
          <h4>Budget mensuel maximum</h4>
          <v-text-field
            v-model="newProfile.maxBudget"
            suffix="€"
            style="max-width: 100px"
            :rules="[rules.required, rules.password]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Temps consommation moyen mensuel</h4>
          <v-text-field
            v-model="newProfile.averageConsumptionTime"
            suffix="heures"
            style="max-width: 100px"
            :rules="[rules.required, rules.password]"
            required
          ></v-text-field>
        </div>
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between">
        <v-btn text @click="goToLogin()">Retour</v-btn>
        <v-btn text color="primary" @click="register()">Créer internaute</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Register",

  data() {
    return {
      newProfile: {
        name: "",
        age: 18,
        sex: "whoknows",
        maxBudget: 5,
        averageConsumptionTime: 1,
      },
      rules: {
        required: (value) => !!value || "Champ requis",
        age: (value) => (value && /(?=^[0-9]*$)/.test(value) && value >= 18) || "Mimimum 18 ans",
        maxBudget: (value) => (value && /(?=^[0-9]*$)/.test(value) && value >= 5) || "Minimum 5€",
        averageConsumptionTime: (value) => (value && /(?=^[0-9]*$)/.test(value) && value >= 1) || "Minimum 1 heure",
      },
    };
  },

  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if (this.profile != null) this.$router.push({ name: "rechercher" });
  },

  methods: {
    goToLogin() {
      this.$router.push({ name: "login" });
    },
    register() {
      this.$axios.post("/profil", this.newProfile).then(
        (response) => {
          console.log(response);
          this.profile = response.data;
          this.$store.commit("setProfile", response.data);
          this.$router.push({ name: "profil" });
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};
</script>
