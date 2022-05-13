<template>
  <v-card>
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
<!--        <account-info :playlist="selectedPlaylist" />-->
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
