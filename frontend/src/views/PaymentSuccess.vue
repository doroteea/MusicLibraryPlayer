<template>
  <div class="text-center">
    <div class="spinner-border" role="status">
      <span class="sr-only">Loading...</span>
      <span class="sr-only">Payment successful...</span>
    </div>
    <div>
      <v-btn class="sr-only" @click="goBack">Finish</v-btn>
      <v-btn @click="generatePDF">Download tracks PDF</v-btn>
      <v-btn @click="generateCSV">Download tracks CSV</v-btn>
    </div>
  </div>
</template>

<script>
import api from "../api";

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
    goBack() {
      this.$router.push("/tracksApi");
    },
    generatePDF() {
      api.payment.generatePDF({
        user_id: this.$store.getters["auth/getUserID"],
      });
    },
    generateCSV() {
      api.payment.generateCSV({
        user_id: this.$store.getters["auth/getUserID"],
      });
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.sessionId = localStorage.getItem("sessionId");
  },
};
</script>
