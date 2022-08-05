import {
    Container,
    Typography,
    Button,
    TextField,
    InputAdornment,
    Table,
    TableContainer,
    TableRow,
    TableCell,
    TableHead,
    TableBody,
    IconButton
} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import axios from "axios";
import getToken from "../util/tokenGetter";
import { backendUrl } from "../config";
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import { useNavigate } from "react-router-dom";
import { useSnackbar } from "notistack";

const ViewCustomer = () => {

    const role = useSelector(state => state.login.role);
    const [customers, setCustomers] = useState([]);
    const navigate = useNavigate();
    const { enqueueSnackbar } = useSnackbar();

    useEffect(() => {
        getCustomers();
    }, [])

    const getCustomers = async () => {
        var token = await getToken()
        axios.get(`${backendUrl}/user/customer`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
            if (res.status === 200) {
                setCustomers(res.data);
            }
        })
    }

    const deleteCustomer = async (id) => {
        var token = await getToken()
        axios.delete(`${backendUrl}/user/${id}`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
            if (res.status === 204) {
                enqueueSnackbar("Deleted customer", { variant: "success" })
            }
        })
    }

    return (
        <Container style={{ minHeight: "76vh" }}>
            <div style={{ display: "flex", justifyContent: "space-between", paddingTop: "15px" }}>
                <Typography variant="h4" color="primary">Customers</Typography>
                {
                    role === "ADMIN" &&
                    <Button variant="contained" onClick={() => navigate("/admin/add/customer")}>Add Customer</Button>
                }
            </div>
            <TextField
                sx={{ mt: 1 }}
                fullWidth
                label="Search Customer"
                placeholder="Search"
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
            />
            <TableContainer style={{ border: "1px solid #c9c3ad", borderRadius: "5px", marginTop: "5px", marginBottom: "20px", backgroundColor: "white" }} >
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">Id</TableCell>
                            <TableCell align="center">Name</TableCell>
                            <TableCell align="center">Username</TableCell>
                            <TableCell align="center">Email</TableCell>
                            <TableCell align="center">Number of Orders</TableCell>
                            {
                                role === 'ADMIN' &&
                                <TableCell align="center">Action</TableCell>
                            }
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            customers.map((customer, index) => (
                                <TableRow key={index} hover>
                                    <TableCell align="center">{customer.userId}</TableCell>
                                    <TableCell align="center">{customer.name || "-"}</TableCell>
                                    <TableCell align="center">{customer.username}</TableCell>
                                    <TableCell align="center">{customer.email || "-"}</TableCell>
                                    <TableCell align="center">{customer.orders?.length || 0}</TableCell>
                                    {
                                        role === "ADMIN" &&
                                        <TableCell align="center">
                                            <IconButton color="primary" onClick={() => navigate("/admin/edit/customer/" + customer.userId)}>
                                                <EditIcon />
                                            </IconButton>
                                            <IconButton color="error" onClick={() => deleteCustomer(customer.userId)}>
                                                <DeleteForeverIcon />
                                            </IconButton>
                                        </TableCell>
                                    }
                                </TableRow>
                            ))
                        }
                    </TableBody>
                </Table>
            </TableContainer>
        </Container >
    )
}

export default ViewCustomer;