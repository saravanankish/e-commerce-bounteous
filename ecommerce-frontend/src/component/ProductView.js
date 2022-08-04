import { Box, Button, Grid, Typography } from "@mui/material";
import banner from "../static/banner1.jpg"
import axios from "axios";
import { useEffect, useState } from "react";
import WestIcon from '@mui/icons-material/West';
import EastIcon from '@mui/icons-material/East';
import ProductCard from "./ProductCard";
import { backendUrl } from "../config";
import CircularProgress from '@mui/material/CircularProgress';

const ProductView = ({ loggedIn, size, setRefreshCart }) => {

    const [products, setProducts] = useState([]);
    const [page, setPage] = useState(0);
    const [loaded, setLoaded] = useState(false);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        setLoaded(false);
        axios.get(`${backendUrl}/products?page=${page}`).then(res => {
            if (res.data.products.length) {
                setProducts(res.data.products);
                setTotalPages(res.data.totalPages);
                console.log(res.data.products)
                setLoaded(true);
            }
        })
    }, [page])

    const nextPage = () => setPage(prev => prev + 1)
    const prevPage = () => setPage(prev => prev - 1)

    return (
        <Grid container item spacing={1} xs={size}>
            <Grid item style={{ height: "300px", objectFit: "cover" }} xs={12} >
                <img src={banner} alt="banner" style={{ width: "100%", height: "100%" }} />
            </Grid>
            {
                loaded ?
                    <Grid item container spacing={1} xs={12} style={{ marginLeft: "2px", marginRight: "5px" }}>
                        {
                            products.map((product, index) => (
                                <Grid item xs={6} md={3} key={index}>
                                    <ProductCard setRefreshCart={setRefreshCart} product={product} />
                                </Grid>
                            ))
                        }
                        <Grid xs={12} item style={{ display: 'flex', justifyContent: "center", marginTop: "10px", marginBottom: "20px" }}>
                            <div style={{ display: "flex" }}>
                                <Button
                                    style={{ backgroundColor: "gainsboro" }}
                                    variant="contained"
                                    startIcon={<WestIcon />}
                                    disabled={page === 0}
                                    onClick={prevPage}
                                    sx={{ mr: 2 }}
                                >
                                    Prev
                                </Button>
                                <Box
                                    style={{
                                        width: "50px",
                                        border: "1px solid",
                                        textAlign: "center",
                                        lineHeight: 2,
                                        borderRadius: 4,
                                        backgroundColor: "white"
                                    }}
                                >
                                    {page + 1}
                                </Box>
                                <Button
                                    style={{ backgroundColor: "gainsboro" }}
                                    onClick={nextPage}
                                    variant="contained"
                                    endIcon={<EastIcon />}
                                    sx={{ ml: 2 }}
                                    disabled={page === totalPages}
                                >
                                    Next
                                </Button>
                            </div>
                        </Grid>
                    </Grid>
                    :
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