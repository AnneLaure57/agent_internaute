"use strict";

import Vue from "vue";
import axios from "axios";

axios.defaults.baseURL = "http://" + process.env.VUE_APP_API_HOST + ":" + process.env.VUE_APP_API_PORT;

let config = {};

const _axios = axios.create(config);

_axios.interceptors.request.use(
  function(config) {
    // Do something before request is sent
    return config;
  },
  function(error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
_axios.interceptors.response.use(
  response => response,
  error => { 
    return Promise.reject(error);
 }
);

Plugin.install = function(Vue) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    }
  });
};

Vue.use(Plugin);

export default Plugin;
