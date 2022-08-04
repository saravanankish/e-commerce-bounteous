import { useSelector } from 'react-redux';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loggout } from '../redux/loginSlice';
import ProductView from "../component/ProductView";
import { Grid } from "@mui/material";
import Cart from "../component/Cart";

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
        // eslint-disable-next-line
    }, [])

    useEffect(() => {
        if (searchParam.get("logout")) {
            window.opener.logout()
            window.close()
        }
        // eslint-disable-next-line
    }, [searchParam.get("logout")])

    return (
        <div style={{ minHeight: "95vh", backgroundColor: "cornsilk" }}>
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