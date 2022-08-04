import { backendUrl } from '../config';
import axios from 'axios';
import getToken from '../util/tokenGetter';
import fetchCart from '../util/fetchCart';


const addToCart = async (product, cart, quantity, dispatch, enqueueSnackbar, existingProduct) => {

    if (!sessionStorage.getItem("token")) {
        enqueueSnackbar("Login to add products to cart", { variant: "warning" })
        return
    }

    if (cart?.products?.length) {
        var productPresent = cart.products.filter(prod => prod.product.productId === product.productId);
        if (productPresent.length !== 0 && !existingProduct) {
            enqueueSnackbar("Product already present in cart", { variant: "error" });
            return;
        }
    }
    var data = {
        product: {
            productId: product.productId
        },
        quantity: quantity
    }
    const token = await getToken();
    axios.post(`${backendUrl}/cart/add`, data, { headers: { "Authorization": "Bearer " + token } }).then(res => {
        if (res.data) {
            fetchCart(dispatch);
        }
    })
}

export default addToCart;