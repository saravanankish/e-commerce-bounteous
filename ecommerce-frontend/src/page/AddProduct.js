import {
    Button,
    Container,
    Grid,
    TextField,
    Typography,
    IconButton,
    Autocomplete
} from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { backendUrl } from "../config";
import getToken from "../util/tokenGetter";
import { useSnackbar } from "notistack";
import DeleteIcon from '@mui/icons-material/Delete';
import { validateImageUrl } from "../util/validations";

const AddProduct = ({ type, edit }) => {

    const [brands, setBrands] = useState([]);
    const [categories, setCategories] = useState([]);
    const { enqueueSnackbar } = useSnackbar();
    const [images, setImages] = useState([""]);
    const [name, setName] = useState("");
    const [price, setPrice] = useState(1);
    const [rating, setRating] = useState(5);
    const [quantity, setQuantity] = useState(1);
    const [brand, setBrand] = useState(null);
    const [category, setCategory] = useState("");
    const [description, setDescription] = useState("");
    const [thumbnail, setThumbnail] = useState("");
    const { productId } = useParams();
    const [errors, setErrors] = useState({
        thumbnail: "",
        images: [""]
    })
    const navigate = useNavigate();

    useEffect(() => {
        if (productId) {
            getProductData(productId)
        }
    }, [productId])

    const handleSubmit = async (e) => {
        e.preventDefault()
        var token = await getToken()
        var headers = { "Authorization": "Bearer " + token };
        var productData = {
            name,
            description,
            price,
            quantity,
            rating,
            thumbnail,
            images,
            brand,
            category
        }
        if (edit) productData.productId = productId;
        if (!edit) {
            axios.post(`${backendUrl}/products`, productData, { headers }).then(res => {
                if (res.status === 201) {
                    enqueueSnackbar("Product added successfully", { variant: "success" })
                    navigate("/admin/products")
                }
            }).catch(err => {
                if (err) {
                    enqueueSnackbar(err, { variant: "error" })
                }
            })
        } else {
            axios.put(`${backendUrl}/products/${productId}`, productData, { headers }).then(res => {
                if (res.status === 201) {
                    enqueueSnackbar("Product updated successfully", { variant: "success" })
                    navigate("/admin/products");
                }
            }).catch(err => {
                if (err)
                    enqueueSnackbar(err, { variant: "error" })
            })
        }
    }

    const getProductData = async (productId) => {
        axios.get(`${backendUrl}/products/${productId}`).then(res => {
            if (res.status === 200) {
                var product = res.data;
                setName(product.name);
                setDescription(product.description)
                setRating(product.rating)
                setPrice(product.price);
                setQuantity(product.quantity)
                setThumbnail(product.thumbnail)
                setImages(product.images)
                setBrand(product.brand)
                setCategory(product.category)
            }
        }).catch(err => {
            enqueueSnackbar(err, { variant: "error" })
        })
    }

    const fetchBrands = async () => {
        var token = await getToken();
        axios.get(`${backendUrl}/brand`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
            if (res.status === 200) {
                setBrands(res.data);
            }
        }).catch(err => {
            enqueueSnackbar("Some error occured", { variant: "error" })
        })
    }

    const fetchSubCategories = async () => {
        var token = await getToken();
        axios.get(`${backendUrl}/category/subcategory`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
            if (res.status === 200) {
                setCategories(res.data);
            }
        }).catch(err => {
            enqueueSnackbar("Some error occured", { variant: "error" })
        })
    }

    useEffect(() => {
        fetchBrands()
        fetchSubCategories()
    }, [])

    return (
        <Container>
            <Typography variant="h4" color="primary" sx={{ textAlign: "center", pt: 2, mb: 2 }}>{type} Product</Typography>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={1.5} style={{ marginBottom: "20px" }}>
                    <Grid item xs={12} md={6}>
                        <TextField
                            label="Product Name"
                            fullWidth
                            required
                            value={name}
                            onChange={e => setName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <TextField
                            label="Price"
                            required
                            fullWidth
                            InputLabelProps={{ shrink: true }}
                            inputProps={{ inputMode: 'numeric', pattern: '[.0-9]*' }}
                            value={price}
                            onChange={e => setPrice(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <TextField
                            label="Rating"
                            InputLabelProps={{ shrink: true }}
                            fullWidth
                            required
                            inputProps={{ inputMode: 'numeric', pattern: '[.0-9]*' }}
                            value={rating}
                            onChange={e => setRating(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <TextField
                            label="Quantity"
                            InputLabelProps={{ shrink: true }}
                            required
                            fullWidth
                            value={quantity}
                            inputProps={{ inputMode: 'numeric', pattern: '[0-9]*' }}
                            onChange={e => setQuantity(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Autocomplete
                            options={brands}
                            required
                            getOptionLabel={(option) => option.name}
                            value={brand}
                            onChange={(event, newValue) => {
                                setBrand(newValue)
                            }}
                            openOnFocus
                            renderInput={(params) => (
                                <TextField required fullWidth {...params} label="Brands" />
                            )}
                        />
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Autocomplete
                            required
                            options={categories}
                            getOptionLabel={(option) => option}
                            value={category}
                            onChange={(event, newValue) => {
                                setCategory(newValue)
                            }}
                            openOnFocus
                            renderInput={(params) => (
                                <TextField required fullWidth {...params} label="Category" />
                            )}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="Description"
                            fullWidth
                            multiline
                            required
                            rows={3}
                            value={description}
                            onChange={e => setDescription(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            label="Thumbnail Image"
                            fullWidth
                            required
                            value={thumbnail}
                            onChange={e => setThumbnail(e.target.value)}
                        />
                    </Grid>
                    {
                        images.map((image, index) => (
                            <Grid
                                item
                                xs={12}
                                style={{
                                    display: "flex",
                                    alignItems: "center"
                                }}
                                key={index}
                            >
                                <TextField
                                    label="Image"
                                    required
                                    value={image}
                                    style={{ flex: 1, marginRight: 10 }}
                                    onChange={e => {
                                        var tempImages = images;
                                        tempImages[index] = e.target.value;
                                        setImages([...tempImages]);
                                    }}
                                />
                                <IconButton
                                    color="error"
                                    onClick={() => setImages(prev => prev.filter((_, ind) => index != ind))}
                                    disabled={images.length === 1}
                                >
                                    <DeleteIcon />
                                </IconButton>
                            </Grid>

                        ))
                    }
                    <Grid item xs={12}>
                        <Button
                            variant="contained"
                            onClick={() => {
                                setImages(prev => [...prev, ""])
                            }}
                        >
                            Add Image field
                        </Button>
                    </Grid>
                    <Grid item xs={12}>
                        <Button
                            variant="contained"
                            fullWidth
                            type="submit"
                        >
                            {type} product
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </Container>
    )

}

export default AddProduct;