import axios from "axios";
import { backendUrl } from "../config";
import getToken from "../util/tokenGetter";
import { getCart } from "../redux/cartSlice";

const fetchCart = async (dispatch) => {

    const token = await getToken();
    axios.get(`${backendUrl}/cart`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
        if (res.data) {
            dispatch(getCart(res.data));
        }
    })
}

export default fetchCart;