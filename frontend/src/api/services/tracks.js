import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allTracks() {
    return HTTP.get(BASE_URL + "/tracks", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
};
