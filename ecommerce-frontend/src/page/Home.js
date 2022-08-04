import { useSelector } from 'react-redux';
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { loggout } from '../redux/loginSlice';
import ProductView from "../component/ProductView";
import { Grid } from "@mui/material";
import Cart from "../component/Cart";
import Navbar from '../component/Navbar';
import Footer from '../component/Footer';

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
        <>
            <Navbar showAuth={!loggedIn} />
            <div style={{ marginTop: "45px", minHeight: "95vh" }}>
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
            <Footer />
        </>
    );
}

export default Home;