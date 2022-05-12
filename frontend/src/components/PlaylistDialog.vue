<template>
  <v-dialog
    transition="dialog-bottom-transition"
<<<<<<< HEAD
    max-width="600"
=======
    max-width="1000"
>>>>>>> df4adf84a4661dd3b3178057f391bc873ed956c1
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create playlist" : "Edit playlist" }}
        </v-toolbar>
        <v-form>
<<<<<<< HEAD
          <v-text-field v-model="playlist.title" label="Title" />
        </v-form>

=======
          <v-card-title>
            <v-text-field v-model="playlist.title" label="Title" />
            <v-spacer></v-spacer>
            <v-text-field
              v-model="search"
              @input="filterTracks"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            <v-data-table
              :headers="headers"
              :items="playlist.tracks"
              :search="search"
            >
            </v-data-table
          ></v-card-title>
        </v-form>
>>>>>>> df4adf84a4661dd3b3178057f391bc873ed956c1
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create playlist" : "Save playlist" }}
          </v-btn>
<<<<<<< HEAD
=======
          <v-btn @click="deletePlaylist">Delete Playlist</v-btn>
>>>>>>> df4adf84a4661dd3b3178057f391bc873ed956c1
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "PlaylistDialog",
  props: {
    playlist: Object,
    opened: Boolean,
  },
<<<<<<< HEAD
=======
  data() {
    return {
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
      selectedPlaylist: {},
    };
  },
>>>>>>> df4adf84a4661dd3b3178057f391bc873ed956c1
  methods: {
    persist() {
      if (this.isNew) {
        api.playlists
          .create({
            title: this.playlist.title,
<<<<<<< HEAD
            duration : 0,
=======
            duration: 0,
>>>>>>> df4adf84a4661dd3b3178057f391bc873ed956c1
          })
          .then(() => {
            this.$emit("refresh");
          });
      } else {
        api.playlists
          .edit({
            id: this.playlist.id,
            title: this.playlist.title,
            tracks: this.playlist.tracks,
          })
          .then(() => this.$emit("refresh"));
      }
    },
<<<<<<< HEAD
  },
  computed: {
=======
    deletePlaylist() {
      api.playlists
        .delete({
          id: this.playlist.id,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    console: () => console,
>>>>>>> df4adf84a4661dd3b3178057f391bc873ed956c1
    isNew: function () {
      return !this.playlist.tracks && !this.playlist.id;
    },
  },
};
</script>

<style scoped></style>
