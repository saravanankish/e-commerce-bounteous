import { Button, Container, Grid, TextField, Typography, FormControl, InputLabel, Select, MenuItem } from "@mui/material";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { validateEmail, validatePassword, validateUsername } from "../util/validations";
import axios from "axios";
import { backendUrl } from "../config";
import getToken from "../util/tokenGetter";
import { useSnackbar } from "notistack";

const AddCustomer = ({ type, edit }) => {

    const userRole = useSelector(state => state.login.role);
    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errors, setErrors] = useState({
        email: "",
        password: "",
        username: "",
    });
    const navigate = useNavigate();
    const { enqueueSnackbar } = useSnackbar();
    const { customerId } = useParams();
    const [role, setRole] = useState("CUSTOMER");

    useEffect(() => {
        if (customerId && edit) {
            getCustomerData()
        }
    }, [customerId])

    const getCustomerData = async () => {
        var token = await getToken()
        axios.get(`${backendUrl}/user/${customerId}`, { headers: { "Authorization": "Bearer " + token } }).then(res => {
            if (res.data.userId) {
                var user = res.data;
                setUsername(user.username);
                setName(user.name);
                setEmail(user.email);
            }
        })
    }

    const addUser = async (edit) => {
        var token = await getToken()
        if (validateEmail(setErrors, email) && (validatePassword(setErrors, password, confirmPassword) || edit) && validateUsername(setErrors, username)) {
            const data = {
                username,
                name,
                role,
                email
            }
            if (!edit) data.password = password;
            if (edit) data.userId = parseInt(customerId);
            if (!edit)
                axios.post(`${backendUrl}/user`, data, { headers: { "Authorization": "Bearer " + token } }).then(res => {
                    if (res.data.userId) {
                        enqueueSnackbar("Customer created successfully", { variant: "success" });
                        navigate("/admin/customers")
                    }
                })
            else
                axios.put(`${backendUrl}/user/${customerId}`, data, { headers: { "Authorization": "Bearer " + token } }).then(res => {
                    if (res.data.userId) {
                        enqueueSnackbar("Customer updated successfully", { variant: "success" });
                        navigate("/admin/customers")
                    }
                })
        }
    }


    const handleSubmit = async (e) => {
        e.preventDefault();
        addUser(edit)
    }

    if (userRole === "SUPPORT") return <Navigate to="/admin" />

    return (
        <Container>
            <Typography variant="h4" color="primary" sx={{ textAlign: "center", pt: 3 }}>{type} Customer</Typography>
            <form onSubmit={handleSubmit}>
                <div
                    style={{ justifyContent: "center", display: "flex", marginTop: "30px", marginBottom: "40px" }}
                >
                    <Grid container spacing={2} style={{ maxWidth: "600px" }}>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Name"
                                required
                                value={name}
                                onChange={e => setName(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Username"
                                required
                                value={username}
                                onChange={e => setUsername(e.target.value)}
                                onBlur={(e) => validateUsername(setErrors, e.target.value)}
                                {...(errors.username && { helperText: errors.username, error: true })}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Email"
                                required
                                value={email}
                                onChange={e => setEmail(e.target.value)}
                                onBlur={e => validateEmail(setErrors, e.target.value)}
                                {...(errors.email && { helperText: errors.email, error: true })}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <FormControl fullWidth>
                                <InputLabel id="demo-simple-select-label">Role</InputLabel>
                                <Select
                                    labelId="demo-simple-select-label"
                                    id="demo-simple-select"
                                    value={role}
                                    label="Role"
                                    onChange={(e) => setRole(e.target.value)}
                                >
                                    <MenuItem value="CUSTOMER">Customer</MenuItem>
                                    <MenuItem value="SUPPORT">Support</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        {
                            !edit &&
                            <>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        label="Password"
                                        required
                                        value={password}
                                        onChange={e => setPassword(e.target.value)}
                                        onBlur={(e) => validatePassword(setErrors, e.target.value, confirmPassword)}
                                        {...(errors.password && { helperText: errors.password, error: true })}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        fullWidth
                                        required
                                        label="Confirm Password"
                                        value={confirmPassword}
                                        onChange={e => setConfirmPassword(e.target.value)}
                                        onBlur={(e) => validatePassword(setErrors, password, e.target.value)}
                                    />
                                </Grid>
                            </>
                        }
                        <Grid item xs={12}>
                            <Button variant="contained" type="submit" fullWidth>{type} Customer</Button>
                        </Grid>
                    </Grid>
                </div>
            </form>
        </Container>
    )
}

export default AddCustomer;