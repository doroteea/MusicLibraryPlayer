<template>
  <v-card>
    <v-card-title>
      Tracks
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        @input="filterTracks"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
<!--      <v-btn @click="filter">search</v-btn>-->
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="tracks"
      :search="search"
    ></v-data-table>

  </v-card>
</template>

<script>
import api from "../api";

export default {
  name: "Track",
  data() {
    return {
      tracks: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "id", value: "id" },
        { text: "link", value: "link" },
        { text: "duration", value: "duration" },
        { text: "explicit_lyrics", value: "explicit_lyrics" },
        { text: "preview", value: "preview" },
        { text: "artist", value: "artist" },
        { text: "album", value: "album" },
      ],
      dialogVisible: false,
    };
  },
  methods: {
    // async filterTracks() {
    //   if (this.search !== "") {
    //     this.tracks = await api.tracks.filterBy(this.search);
    //   } else {
    //     this.search = "";
    //     //this.tracks = await api.tracks.allTracks();
    //   }
    // },
    // filter() {
    //   if (this.search !== "") {
    //     this.tracks = api.tracks.filterBy(this.search);
    //   }
    // },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.tracks = await api.tracks.allTracks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
