<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-form>
          <v-text-field v-model="track.id" label="Playlist id" />
        </v-form>
        <v-card-actions>
          <v-btn @click="addToPlaylist">Add to playlist</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "TracksApiDialog",
  props: {
    track: Object,
    opened: Boolean,
  },
  methods: {
    addToPlaylist() {
      api.tracksApi
        .addToPlaylist({
          id: this.track.id,
          title: this.track.title,
          link: this.track.link,
          duration: this.track.duration,
          explicit_lyrics: this.track.explicit_lyrics,
          preview: this.track.preview,
          artist: this.track.artist,
          album: this.track.album,
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
