import { Box, Button, Grid } from "@mui/material";
import banner from "../static/banner1.jpg"
import axios from "axios";
import { useEffect, useState } from "react";
import WestIcon from '@mui/icons-material/West';
import EastIcon from '@mui/icons-material/East';
import ProductCard from "./ProductCard";

const ProductView = ({ loggedIn, size }) => {

    const [products, setProducts] = useState([]);
    const [page, setPage] = useState(0);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/v1/products?page=${page}`).then(res => {
            if (res.data.length) {
                setProducts(res.data);
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
            <Grid item container spacing={1} xs={12} style={{ marginLeft: "2px", marginRight: "5px" }}>
                {
                    products.map((product, index) => (
                        <Grid item xs={6} md={3} key={index}>
                            <ProductCard product={product} />
                        </Grid>
                    ))
                }
                <Grid xs={12} item style={{ display: 'flex', justifyContent: "center" }}>
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
                                borderRadius: 4
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
                        >
                            Next
                        </Button>
                    </div>
                </Grid>
            </Grid>
        </Grid>
    )

}

export default ProductView;