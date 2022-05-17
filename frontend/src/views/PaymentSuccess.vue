<template>
  <div class="text-center">
    <div class="spinner-border" role="status">
      <span class="sr-only">Loading...</span>
      <span class="sr-only">Payment successful...</span>
    </div>
    <div>
      <v-btn class="sr-only" @click="goBack">Finish</v-btn>
    </div>
  </div>
</template>

<script>
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
