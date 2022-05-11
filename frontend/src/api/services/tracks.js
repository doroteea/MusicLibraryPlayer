import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allTracks() {
    return HTTP.get(BASE_URL + "/tracks", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  filterBy(filter) {
    return HTTP.get(BASE_URL + "/tracks/filter/" + filter, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
