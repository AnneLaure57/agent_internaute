<template>
  <div
    style="width: 100%; height: 100%; background-color: #efefef;"
    class="d-flex flex-column align-center justify-space-between flex-grow-1"
  >
    <v-card width="50%" max-width="400px" class="d-flex flex-column ma-10 pa-3">
      <v-card-title>Enregistrement nouveau profil</v-card-title>
      <v-card-subtitle
        >Merci de saisir les informations suivantes.</v-card-subtitle
      >
      <v-card-text class="d-flex flex-column">
        <div>
          <v-text-field
            class="mr-12"
            v-model="newProfile.name"
            placeholder="Identifiant"
            :rules="[rules.required]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Age</h4>
          <v-text-field
            v-model="newProfile.age"
            suffix="ans"
            style="max-width: 60px"
            :rules="[rules.required, rules.age]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Sexe</h4>
          <v-radio-group v-model="newProfile.sex" row>
            <v-radio label="F" value="woman"></v-radio>
            <v-radio label="H" value="man"></v-radio>
            <v-radio label="?" value="whoknows"></v-radio>
          </v-radio-group>
        </div>

        <div>
          <h4>Budget max</h4>
          <v-text-field
            v-model="newProfile.maxBudget"
            suffix="€/mois"
            style="max-width: 80px"
            :rules="[rules.required, rules.maxBudget]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Consommation moyenne</h4>
          <v-text-field
            v-model="newProfile.averageConsumptionTime"
            suffix="heures/mois"
            style="max-width: 120px"
            :rules="[rules.required, rules.averageConsumptionTime]"
            required
          ></v-text-field>
        </div>

        <div>
          <h4>Stratégie</h4>
          <v-select
              v-model="newProfile.strategy"
              :items="strategies"
              item-text="name"
              style="max-width: 200px"
              required
            >
              <template v-slot:item="{ item }">
                <span class="mr-1" :key="item.id">
                  {{ item.name }} : {{ item.description }}
                </span>
              </template>

              <template v-slot:selection="{ item }">
                <span class="mr-1" :key="item.id">
                  {{ item.name }}
                </span>
              </template>
            </v-select>
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
        strategies: [],
      },
      rules: {
        required: (value) => !!value || "Champ requis",
        age: (value) =>
          (value && /(?=^[0-9]*$)/.test(value) && value >= 18) ||
          "Mimimum 18 ans",
        maxBudget: (value) =>
          (value && /(?=^[0-9]*$)/.test(value) && value >= 5) || "Minimum 5€",
        averageConsumptionTime: (value) =>
          (value && /(?=^[0-9]*$)/.test(value) && value >= 1) ||
          "Minimum 1 heure",
      },
      strategies: [],
    };
  },

  computed: {
    ...mapState(["profile"]),
  },

  mounted() {
    if (this.profile != null) this.$router.push({ name: "rechercher" });
    this.getStrategies();
  },

  methods: {
    goToLogin() {
      this.$router.push({ name: "login" });
    },

    getStrategies() {
      this.$axios.get("/profil/strategies").then((response) => {
        this.strategies = response.data;
      });
    },

    register() {
      this.$axios.post("/profil", this.newProfile).then(
        (response) => {
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
