import { createSlice } from '@reduxjs/toolkit';

export const cartSlice = createSlice({
    name: "login",
    initialState: {
        cart: {
            products: []
        }
    },
    reducers: {
        getCart: (state, action) => {
            state.cart = { ...action.payload };
        }
    }
})

export const { getCart } = cartSlice.actions

export default cartSlice.reducer;