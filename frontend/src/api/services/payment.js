import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  checkout(payment) {
    return HTTP.post(BASE_URL + "/payment", payment, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
