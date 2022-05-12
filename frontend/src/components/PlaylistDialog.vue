<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create playlist" : "Edit playlist" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="playlist.title" label="Title" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create playlist" : "Save playlist" }}
          </v-btn>
          <v-btn @click="deletePlaylist">Delete Playlist</v-btn>
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
  methods: {
    persist() {
      if (this.isNew) {
        api.playlists
          .create({
            title: this.playlist.title,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.playlists
          .edit({
            id: this.playlist.id,
            title: this.playlist.title,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deletePlaylist() {
      api.playlists
        .delete({
          id: this.playlist.id,
        })
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.playlist.id;
    },
  },
};
</script>

<style scoped></style>
