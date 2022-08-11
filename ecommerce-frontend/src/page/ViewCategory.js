import {
    Container,
    TableContainer,
    TableCell,
    TableRow,
    TableBody,
    TableHead,
    Table,
    Button,
    Typography,
    TextField,
    InputAdornment
} from "@mui/material";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import SearchIcon from '@mui/icons-material/Search';
import getToken from "../util/tokenGetter";
import axios from "axios";
import { backendUrl } from "../config";
import { useSnackbar } from "notistack";

const ViewCategory = () => {

    const role = useSelector(state => state.login.role);
    const navigate = useNavigate();
    const [search, setSearch] = useState("");
    const [categories, setCategories] = useState([]);
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        fetchCategories();
    }, [])

    const fetchCategories = async () => {
        var token = await getToken();
        axios.get(`${backendUrl}/category/all`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
            if (res.status === 200) {
                setCategories(res.data);
            }
        }).catch(err => {
            if (err)
                enqueueSnackbar(err, { variant: "error" });
        })
    }

    return (
        <Container sx={{ mt: 1, mx: 3, mb: 3 }} >
            <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "15px" }}>
                <Typography variant="h4" color="primary">Categories</Typography>
                {
                    role === "ADMIN" &&
                    <Button variant="contained" onClick={() => navigate("/admin/add/category")}>Add Category</Button>
                }
            </div>
            <TextField
                sx={{ mt: 1 }}
                fullWidth
                label="Search Category"
                placeholder="Search"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
            />
            <TableContainer
                style={{
                    border: '1px solid #c9c3ad',
                    borderRadius: "5px",
                    backgroundColor: "white",
                    marginTop: "5px"
                }}
            >
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">Category ID</TableCell>
                            <TableCell align="center">Category Name</TableCell>
                            <TableCell align="center">Subcategories</TableCell>
                            {
                                role === "ADMIN" &&
                                <TableCell align="center">Actions</TableCell>
                            }
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            categories.map((category, index) => (
                                <TableRow hover>
                                    <TableCell align="center">{category.id}</TableCell>
                                    <TableCell align="center">{category.name}</TableCell>
                                    <TableCell align="center">{category.subCategory}</TableCell>
                                    {/* <TableCell align="center">{category.name}</TableCell> */}
                                </TableRow>
                            ))
                        }
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    )
}

export default ViewCategory;