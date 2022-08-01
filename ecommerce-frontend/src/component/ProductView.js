import { Grid } from "@mui/material";
import banner from "../static/banner1.jpg"

const ProductView = ({ products, loggedIn, size }) => {

    return (
        <Grid container item spacing={1} xs={size}>
            <Grid item style={{ height: "300px", objectFit: "cover" }} xs={12} >
                <img src={banner} alt="banner" style={{ width: "100%", height: "100%" }} />
            </Grid>
            <Grid item container spacing={1}>
                {
                    products.map((prod, index) => (
                        <Grid key={index} xs={3}>

                        </Grid>
                    ))
                }
            </Grid>
        </Grid>
    )

}

export default ProductView;