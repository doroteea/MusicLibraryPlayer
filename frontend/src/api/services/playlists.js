import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allPlaylists() {
    return HTTP.get(BASE_URL + "/playlists", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(playlist) {
    return HTTP.post(BASE_URL + "/playlists", playlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  edit(playlist) {
    return HTTP.put(BASE_URL + "/playlists/" + playlist.id, playlist, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  delete(playlist) {
    return HTTP.delete(BASE_URL + "/playlists/" + playlist.id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
