import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Rating from '@mui/material/Rating';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { AddShoppingCartOutlined } from "@mui/icons-material";
import { useSelector, useDispatch } from 'react-redux';
import addToCart from '../util/addToCart';

const ProductCard = ({ product, setRefreshCart }) => {

    const [mouseOver, setMouseOver] = useState();
    const dispatch = useDispatch();
    const cart = useSelector(state => state.cart.cart);

    return (
        <Card
            elevation={mouseOver ? 6 : 3}
            onMouseEnter={() => setMouseOver(true)}
            onMouseLeave={() => setMouseOver(false)}
            style={{
                height: "100%",
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                cursor: "pointer"
            }}
        >
            <CardMedia
                component="img"
                height="200"
                image={product.thumbnail}
                alt={product.name}
            />
            <CardContent>
                <Typography variant="h6">
                    {product.name}
                </Typography>
                <Typography variant="body2" component="div"
                    style={{
                        maxHeight: "40px",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        lineClamp: "2",
                        wordWrap: "break-word",
                        display: "block",
                        webBoxOrient: "vertical"
                    }}>
                    {product.description}
                </Typography>
                <Typography variant="h6" style={{ fontWeight: 'bold' }}>
                    ₹ {product.price}
                </Typography>
                <Rating value={product.rating} readOnly precision={0.5} />
            </CardContent>
            <CardActions style={{ marginBottom: "1rem" }}>
                <Button
                    style={{
                        textTransform: "none",
                        fontWeight: "600"
                    }}
                    fullWidth
                    variant="contained"
                    endIcon={<AddShoppingCartOutlined />}
                    onClick={() => addToCart(product, cart, 1, dispatch)}
                >
                    ADD TO CART
                </Button>
            </CardActions>
        </Card>
    );
}

export default ProductCard;