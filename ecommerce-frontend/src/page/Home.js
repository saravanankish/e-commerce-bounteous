import { useSelector } from 'react-redux';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loggout } from '../redux/loginSlice';
import ProductView from "../component/ProductView";
import { Grid, setRef } from "@mui/material";
import Cart from "../component/Cart";
import fetchCart from '../util/fetchCart';

const Home = () => {
    const [searchParam,] = useSearchParams();
    const dispatch = useDispatch();
    const loggedIn = useSelector(state => state.login.loggedIn)
    const [refreshCart, setRefreshCart] = useState(false);

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
        <div style={{ paddingBottom: "20px", backgroundColor: "cornsilk" }}>
            {/* <Navbar showAuth={!loggedIn} /> */}
            <Grid container >
                <ProductView setRefreshCart={setRefreshCart} loggedIn={loggedIn} size={loggedIn ? 9.5 : 12} />
                {
                    loggedIn &&
                    <Grid item xs={2.5}>
                        <Cart refreshCart={refreshCart} />
                    </Grid>
                }
            </Grid>
        </div>
    );
}

export default Home;