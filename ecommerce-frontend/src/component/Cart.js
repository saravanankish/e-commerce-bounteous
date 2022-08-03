import {
    AddOutlined,
    RemoveOutlined,
    ShoppingCart,
    ShoppingCartOutlined,
    Close
} from "@mui/icons-material";
import { Button, IconButton, Stack, Grid, Toolbar, Drawer, Typography, Box } from "@mui/material";
import { useEffect, useState } from "react";
import "./cart.css";
import { useSelector, useDispatch } from "react-redux";
import fetchCart from "../util/fetchCart";
import addToCart from "../util/addToCart";

const ItemQuantity = ({
    value,
    isReadOnly,
    cart,
    dispatch
}) => {
    if (isReadOnly) {
        return (
            <Box padding="0.5rem" fontWeight="500">
                Qty: {value.qty}
            </Box>
        )
    }

    return (
        <Stack direction="row" alignItems="center">
            <IconButton size="small" color="primary" onClick={() => addToCart(value.product, cart, value.quantity - 1, dispatch, true)}>
                <RemoveOutlined />
            </IconButton>
            <Box padding="0.5rem" data-testid="item-qty">
                {value?.quantity}
            </Box>
            <IconButton size="small" color="primary" onClick={() => addToCart(value.product, cart, value.quantity + 1, dispatch, true)}>
                <AddOutlined />
            </IconButton>
        </Stack>
    );
};


const Cart = () => {

    const cart = useSelector(state => state.cart.cart);
    const dispatch = useDispatch();

    useEffect(() => {
        fetchCart(dispatch)
    }, [])


    if (!cart.products?.length) {
        return (
            <Box className="cart empty" sx={{ mt: 1 }}>
                <ShoppingCartOutlined className="empty-cart-icon" />
                <Box color="#aaa" textAlign="center">
                    Cart is empty. Add more items to the cart to checkout.
                </Box>
            </Box>
        );
    }

    return (
        <Box className="cart" sx={{ mt: 1 }}>
            <Typography variant="h5" color="primary" sx={{ pl: "1rem", pt: "1rem", fontWeight: 700 }}>Cart</Typography>
            {
                cart.products.map(item => (
                    <Box display="flex" alignItems="flex-start" padding="1rem" key={item?._id}>
                        <Grid container spacing={1}>
                            <Grid item xs={5}>
                                <Box className="image-container">
                                    <img
                                        // Add product image
                                        src={item?.product.thumbnail}
                                        // Add product name as alt eext
                                        alt={item?.product.name}
                                        width="100%"
                                        height="100%"
                                    />
                                </Box>
                            </Grid>
                            <Grid item xs={7}>
                                <Box
                                    display="flex"
                                    flexDirection="column"
                                    paddingX="0.5rem"
                                >
                                    <div>{item?.product.name}</div>
                                    <Box fontWeight="700">
                                        ₹ {item?.product.price}
                                    </Box>
                                    <ItemQuantity
                                        value={item}
                                        cart={cart}
                                        dispatch={dispatch}
                                        isReadOnly={false}
                                    />
                                </Box>
                            </Grid>
                        </Grid>
                    </Box>
                ))
            }
        </Box>
    )

}

export default Cart;