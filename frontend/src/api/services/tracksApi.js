import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allTracks() {
    return HTTP.get(BASE_URL + "/tracksAPI", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  addToPlaylist(track) {
    return HTTP.put(BASE_URL + "/tracksAPI/" + track.id, track, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
