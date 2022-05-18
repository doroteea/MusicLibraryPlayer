<template>
  <v-card>
    <v-alert v-if="showAlert" type="error" value="errors">{{ errors }}</v-alert>
    <v-card-title>
      Playlists
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addPlaylist">Add Playlist</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="playlists"
      :search="search"
      @click:row="editPlaylist"
    >
    </v-data-table>
    <PlaylistDialog
      :opened="dialogVisible"
      :playlist="selectedPlaylist"
      @refresh="refreshList"
    ></PlaylistDialog>
  </v-card>
</template>

<script>
import api from "../api";
import PlaylistDialog from "../components/PlaylistDialog";

export default {
  name: "Playlists",
  components: { PlaylistDialog },
  data() {
    return {
      showAlert: false,
      errors: [],
      playlists: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Id", value: "id" },
        { text: "Duration", value: "duration" },
      ],
      dialogVisible: false,
      selectedPlaylist: {},
    };
  },
  methods: {
    addPlaylist() {
      this.dialogVisible = true;
    },
    editPlaylist(playlist) {
      if (
        this.selectedPlaylist.userId !== this.$store.getters["auth/getUserID"]
      ) {
        this.showAlert = true;
        this.errors =
          "You can't edit or delete the comment because you did not add it!☺";
      } else {
        this.showAlert = false;
        this.dialogVisible = true;
      }
      this.selectedPlaylist = playlist;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedPlaylist = {};
      this.playlists = await api.playlists.allPlaylists();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
