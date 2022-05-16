<template>
  <div class="text-center">
    <div class="spinner-border" role="status">
      <span class="sr-only">Loading...</span>
      <span class="sr-only">Payment successful...</span>
    </div>
    <div>
      <v-btn class="sr-only" @click="processOrder">Finish</v-btn>
    </div>
  </div>
</template>

<script>
// import api from "../../api";
// import router from "../../router";
import { BASE_URL } from "../api/http";

export default {
  name: "Success",
  props: ["baseURL"],
  data() {
    return {
      token: null,
      sessionId: null,
      payment: [],
    };
  },
  methods: {
    async refreshList() {
      await this.$http
        .get(BASE_URL + "/payment/" + this.$store.getters["auth/userId"])
        .then((response) => {
          if (response.status === 200) {
            let products = response.data;
            console.log(products);
            this.payment.id = products.id;
            console.log("refresh");
            console.log(this.payment);
          }
        });
    },
  },
  created() {
    this.refreshList();
  },
  mounted() {
    this.refreshList();
    this.token = localStorage.getItem("token");
    this.sessionId = localStorage.getItem("sessionId");
  },
};
</script>
