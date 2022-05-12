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
      <v-btn @click="createPlaylist">Add Playlist</v-btn>
    </v-card-title>

    <v-data-table
      :headers="headers"
      :items="playlists"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="'playlist' + playlitst.tracks"
      show-expand
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-spacer></v-spacer>
          <v-switch
            v-model="singleExpand"
            label="Single expand"
            class="mt-2"
          ></v-switch>
        </v-toolbar>
      </template>
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">More info about {{ item.tracks }}</td>
      </template>
    </v-data-table>
    <PlaylistDialog
      :opened="dialogVisible"
      :item="selectedPlaylist"
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
      expanded: [],
      singleExpand: true,
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
        { text: "", value: "data-table-expand" },
      ],
      dialogVisible: false,
      selectedPlaylist: {},
    };
  },
  methods: {
    createPlaylist() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.playlists = await api.playlists.allPlaylists();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
