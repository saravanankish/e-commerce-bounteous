import { Box, Button, Grid, Typography } from "@mui/material";
import banner from "../static/banner1.jpg"
import { useEffect, useState, useRef, useCallback } from "react";
import { useSnackbar } from 'notistack';
import ProductCard from "./ProductCard";
import CircularProgress from '@mui/material/CircularProgress';
import useProductRender from "../util/useProductsRender";

const ProductView = ({ loggedIn, size, setRefreshCart }) => {

    const [page, setPage] = useState(0);
    const { loading, errors, products, hasMore } = useProductRender(page);
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        if (errors)
            enqueueSnackbar("Some error occured", { variant: "error" })
    }, [errors])

    const lastItemObserver = useRef();
    const lastItemCallback = useCallback(node => {
        if (loading) return;
        if (lastItemObserver.current) lastItemObserver.current.disconnect();
        lastItemObserver.current = new IntersectionObserver(entry => {
            if (entry[0].isIntersecting && hasMore) {
                console.log('view')
                setPage(prevPageNumber => prevPageNumber + 1);
            }
        })
        if (node) lastItemObserver.current.observe(node)
    }, [hasMore, loading]);

    return (
        <Grid container item spacing={1} xs={size}>
            <Grid item style={{ height: "300px", objectFit: "cover" }} xs={12} >
                <img src={banner} alt="banner" style={{ width: "100%", height: "100%" }} />
            </Grid>
            <Grid item container spacing={1} xs={12} style={{ marginLeft: "2px", marginRight: "5px" }}>
                {
                    products.map((product, index) => (
                        <Grid ref={(index + 1 === products.length) ? lastItemCallback : null} item xs={6} md={3} key={index}>
                            <ProductCard setRefreshCart={setRefreshCart} product={product} />
                        </Grid>
                    ))
                }
            </Grid>
            {
                loading &&
                <Grid item xs={12} style={{
                    width: "100%", display: "flex", justifyContent: "center", alignItems: "center"
                }}>
                    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column' }}>
                        < CircularProgress />
                        <Typography variant="subtitle1">
                            Loading Products...
                        </Typography>
                    </Box>
                </Grid>
            }
        </Grid >
    )
}

export default ProductView;