import { useState } from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';
import background from '../static/shop.jpg';
import {
    Paper,
    Grid,
    Typography,
    TextField,
    Button
} from "@mui/material";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { backendUrl } from '../config';
import { validateEmail, validatePassword, validateUsername } from '../util/validations';
import { useSnackbar } from 'notistack';

const Register = () => {

    const loggedIn = useSelector(state => state.login.loggedIn)
    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [email, setEmail] = useState("");
    const [errors, setErrors] = useState({
        email: "",
        password: "",
        username: "",
    })
    const navigate = useNavigate();
    const { enqueueSnackbar } = useSnackbar()

    if (loggedIn) {
        return <Navigate to="/" />
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateEmail(setErrors, email) && validatePassword(setErrors, password, confirmPassword) && validateUsername(setErrors, username)) {
            const data = {
                username,
                name,
                password,
                role: "CUSTOMER",
                email
            }
            axios.post(`${backendUrl}/user/register`, data).then(res => {
                if (res.data.userId) {
                    enqueueSnackbar("Registered successfully", { variant: "success" });
                    navigate("/")
                }
            })
        }
    }

    return (
        <Grid container style={{ backgroundImage: `url(${background})`, width: "100%", height: "90vh", backgroundPosition: "center", backgroundSize: "cover" }}>
            <Grid item xs={12} md={6} />
            <Grid item xs={12} md={6} style={{ display: 'flex', justifyContent: 'center', alignItems: "center" }}>
                <Paper sx={{ px: 4, py: 3, minWidth: '300px', width: "70%" }} >
                    <Typography variant='h4' color="dodgerblue" style={{ textAlign: "center", marginBottom: "10px" }}>Register</Typography>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={2}>
                            <Grid item xs={12} md={6}>
                                <TextField
                                    placeholder='Name'
                                    fullWidth
                                    label="Name"
                                    required
                                    value={name}
                                    onChange={e => setName(e.target.value)}
                                />
                            </Grid>
                            <Grid item xs={12} md={6}>
                                <TextField
                                    placeholder='Username'
                                    fullWidth
                                    label="Username"
                                    required
                                    value={username}
                                    onChange={e => setUsername(e.target.value)}
                                    onBlur={(e) => validateUsername(setErrors, e.target.value)}
                                    {...(errors.username && { helperText: errors.username, error: true })}
                                />
                            </Grid>
                            <Grid xs={12} item>
                                <TextField
                                    placeholder='Email'
                                    fullWidth
                                    label="Email"
                                    required
                                    type="email"
                                    value={email}
                                    onChange={e => setEmail(e.target.value)}
                                    onBlur={e => validateEmail(setErrors, e.target.value)}
                                    {...(errors.email && { helperText: errors.email, error: true })}
                                />
                            </Grid>
                            <Grid xs={12} item>
                                <TextField
                                    placeholder='Password'
                                    fullWidth
                                    label="Password"
                                    required
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                    type="password"
                                    onBlur={(e) => validatePassword(setErrors, e.target.value, confirmPassword)}
                                    {...(errors.password && { helperText: errors.password, error: true })}
                                />
                            </Grid>
                            <Grid xs={12} item>
                                <TextField
                                    placeholder='Confirm password'
                                    fullWidth
                                    label="Confirm password"
                                    required
                                    value={confirmPassword}
                                    onBlur={(e) => validatePassword(setErrors, password, e.target.value)}
                                    onChange={e => setConfirmPassword(e.target.value)}
                                    type="password"
                                />
                            </Grid>
                            <Grid item xs={12} >
                                <Button type="submit" variant="contained" fullWidth style={{ height: "45px" }}>Register</Button>
                            </Grid>
                        </Grid>
                    </form>
                </Paper>
            </Grid>
        </Grid>
    );
}

export default Register;