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

const Register = () => {

    const loggedIn = useSelector(state => state.loggedIn)
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

    if (loggedIn) {
        return <Navigate to="/" />
    }

    const validatePassword = () => {
        if (password === "") {
            setErrors(prev => ({ ...prev, password: "Password is required" }))
            return false;
        } else if (password !== "" && confirmPassword === "") {
            setErrors(prev => ({ ...prev, password: "Password and confirm password does not match" }))
            return false;
        } else if (password !== confirmPassword) {
            setErrors(prev => ({ ...prev, password: "Password and confirm password does not match" }))
            return false;
        } else if (!password.match(/[A-Z]/g)) {
            setErrors(prev => ({ ...prev, password: "Password should contain uppercase letter" }))
            return false;
        } else if (!password.match(/[a-z]/g)) {
            setErrors(prev => ({ ...prev, password: "Password should contain lowercase letter" }))
            return false;
        } else if (!password.match(/[0-9]/g)) {
            setErrors(prev => ({ ...prev, password: "Password should contain digits" }))
            return false;
        } else if (!password.match(/[!@#$%^&*]/g)) {
            setErrors(prev => ({ ...prev, password: "Password should contain special characters" }))
            return false;
        } else {
            setErrors(prev => ({ ...prev, password: "" }))
            return true;
        }
    }

    const validateEmail = () => {
        var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        if (email === "") {
            setErrors(prev => ({ ...prev, email: "Email is required" }))
            return false;
        } else if (!email.match(validRegex)) {
            setErrors(prev => ({ ...prev, email: "Invalid email" }))
            return false;
        } else {
            setErrors(prev => ({ ...prev, email: "" }))
            return true;
        }
    }

    const validateUsername = () => {
        if (username.length < 6) {
            setErrors(prev => ({ ...prev, username: "Username should contain alteast 6 letter" }))
            return false;
        } else {
            setErrors(prev => ({ ...prev, username: "" }))
            return true;
        }
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateEmail() && validatePassword() && validateUsername()) {
            const data = {
                username,
                name,
                password,
                role: "USER",
                email
            }
            axios.post(`${backendUrl}/user/register`, data).then(res => {
                if (res.data.userId) {
                    alert("Registered successfully")
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
                                    onBlur={validateUsername}
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
                                    onBlur={validateEmail}
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
                                    onBlur={validatePassword}
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
                                    onBlur={validatePassword}
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