import Navbar from "../component/Navbar";
import { useSelector } from 'react-redux';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loggout } from '../util/loginSlice';
import ProductView from "../component/ProductView";
import { Grid } from "@mui/material";


const Home = () => {
    const [searchParam,] = useSearchParams();
    const dispatch = useDispatch();
    const loggedIn = useSelector(state => state.loggedIn)

    const logoutSuccess = () => {
        sessionStorage.removeItem("token")
        dispatch(loggout())
    }

    useEffect(() => {
        window.logout = logoutSuccess;
    }, [])

    useEffect(() => {
        if (searchParam.get("logout")) {
            window.opener.logout()
            window.close()
        }
    }, [searchParam.get("logout")])

    return (
        <div>
            {/* <Navbar showAuth={!loggedIn} /> */}
            <Grid container spacing={2}>
                {
                    <ProductView products={[]} loggedIn size={loggedIn ? 9.5 : 12} />
                }
            </Grid>
        </div>
    );
}

export default Home;