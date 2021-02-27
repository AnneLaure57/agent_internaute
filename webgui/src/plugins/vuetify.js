import Vue from "vue";
import Vuetify from "vuetify/lib";
import colors from "vuetify/lib/util/colors";
import fr from "vuetify/es5/locale/fr";

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: colors.teal,
        secondary: colors.blue,
        success: colors.green,
        error: colors.red,
        accent: colors.indigo.base,
      },
    },
  },
  icons: {
    iconfont: "mdi",
  },
  lang: {
    locales: { fr },
    current: "fr",
  },
});
