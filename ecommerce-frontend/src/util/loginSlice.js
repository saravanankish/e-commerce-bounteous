import { createSlice } from '@reduxjs/toolkit'

export const loginSlice = createSlice({
    name: "login",
    initialState: {
        loggedIn: JSON.parse(sessionStorage.getItem("token"))?.access_token != null
    },
    reducers: {
        login: (state) => {
            state.loggedIn = true;
        },
        loggout: (state) => {
            state.loggedIn = false;
        }
    }
})

export const { login, loggout } = loginSlice.actions

export default loginSlice.reducer;