import { Button, Container, Grid, TextField, Typography } from "@mui/material";
import { useState } from "react";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

const AddCustomer = ({ type }) => {

    const role = useSelector(state => state.login.role);
    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    if (role === "SUPPORT") return <Navigate to="/admin" />

    return (
        <Container>
            <Typography variant="h4" color="primary" sx={{ textAlign: "center", pt: 3 }}>Add Customer</Typography>
            <form>
                <div
                    style={{ justifyContent: "center", display: "flex", marginTop: "30px", marginBottom: "40px" }}
                >
                    <Grid container spacing={2} style={{ maxWidth: "600px" }}>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Name"
                                required
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Username"
                                required
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Email"
                                required
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Password"
                                required
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                required
                                label="Confirm Password"
                            />
                        </Grid>
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