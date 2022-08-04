import { createSlice } from '@reduxjs/toolkit'

export const loginSlice = createSlice({
    name: "login",
    initialState: {
        loggedIn: JSON.parse(sessionStorage.getItem("token"))?.access_token != null,
        role: JSON.parse(sessionStorage.getItem("token"))?.role || ""
    },
    reducers: {
        login: (state, action) => {
            state.loggedIn = true;
            state.role = action.payload;
        },
        loggout: (state) => {
            state.loggedIn = false;
            state.role = "";
        }
    }
})

export const { login, loggout } = loginSlice.actions

export default loginSlice.reducer;