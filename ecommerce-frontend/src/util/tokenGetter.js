import axios from "axios";
import { authUrl } from "../config";

export default async function getToken() {
    var tokens = JSON.parse(sessionStorage.getItem("token"))
    var currentDate = new Date();
    var recievedDate = new Date(tokens.recieved);
    var diff = (currentDate.getTime() - recievedDate.getTime()) / 1000
    if (diff > tokens.expires_in) {
        let res = await axios.get(`${authUrl}/oauth2/token?refresh-token=` + tokens.refresh_token)
        tokens.access_token = res.data.access_token;
        tokens.recieved = new Date();
        tokens.expires_in = res.data.expires_in;
        sessionStorage.setItem("token", JSON.stringify(tokens));
        return res.data.access_token;
    } else {
        return tokens.access_token;
    }
}