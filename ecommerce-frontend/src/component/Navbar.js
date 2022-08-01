import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import LoginButton from './LoginButton';
import LogoutButton from './LogoutButton';
import { useNavigate } from 'react-router-dom';


const Navbar = ({ showAuth }) => {

    const navigate = useNavigate();

    const popup = () => {
        window.open('http://localhost:9000/logout', 'popup', 'width=300,height=350');
        return false;
    }

    return (
        <AppBar position="fixed">
            <Toolbar>
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    {process.env.REACT_APP_APPLICATION_NAME}
                </Typography>
                {
                    showAuth ?
                        <>
                            <LoginButton variant="contained" color="success" sx={{ mr: 1 }} />
                            <Button variant="contained" color="warning" onClick={() => navigate("/register")}>Register</Button>
                        </>
                        :
                        <LogoutButton variant="contained" color="error" onClick={popup} />
                }
            </Toolbar>
        </AppBar>
    );
}

export default Navbar;