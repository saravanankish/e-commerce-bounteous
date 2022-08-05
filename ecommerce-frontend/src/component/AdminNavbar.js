import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import LogoutButton from './LogoutButton';
import { useNavigate } from 'react-router-dom';
import { authUrl } from '../config';
import AdminDrawer from './AdminDrawer';
import React, { useState } from 'react';

const AdminNavbar = () => {
    const navigate = useNavigate();
    const [openDrawer, setOpenDrawer] = useState(false);

    const popup = () => {
        window.open(`${authUrl}/logout`, 'popup', 'width=300,height=350');
        return false;
    }

    return (
        <>
            <AppBar position="fixed">
                <Toolbar variant="dense">
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                        onClick={() => setOpenDrawer(true)}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Box sx={{ flexGrow: 1 }}>
                        <Typography variant="h6" component="div" sx={{ cursor: "pointer", width: "fit-content" }} onClick={() => navigate("/admin")} >
                            {process.env.REACT_APP_APPLICATION_NAME}
                        </Typography>
                    </Box>
                    <LogoutButton variant="contained" color="error" style={{ height: "30px" }} onClick={popup} />
                </Toolbar>
            </AppBar>
            <AdminDrawer open={openDrawer} setOpen={setOpenDrawer} />
        </>
    );
}

export default AdminNavbar;