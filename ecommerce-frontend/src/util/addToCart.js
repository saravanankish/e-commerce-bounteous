import { backendUrl } from '../config';
import axios from 'axios';
import getToken from '../util/tokenGetter';
import fetchCart from '../util/fetchCart';

const addToCart = async (product, cart, quantity, dispatch, existingProduct) => {

    if (!sessionStorage.getItem("token")) {
        alert("Log in to add to cart")
        return
    }

    if (cart?.products?.length) {
        var productPresent = cart.products.filter(prod => prod.product.productId === product.productId);
        if (productPresent.length !== 0 && !existingProduct) {
            alert("Product already present");
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