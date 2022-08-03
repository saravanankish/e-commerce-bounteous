import { useSearchParams, useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { loggout } from '../util/loginSlice';
import { authUrl } from '../config';

const LogoutButton = ({ refresh, ...rest }) => {

    const [searchParam,] = useSearchParams();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logoutSuccess = () => {
        sessionStorage.removeItem("token")
        navigate("/")
        dispatch(loggout())
    }

    useEffect(() => {
        window.logout = logoutSuccess;
    }, [])

    useEffect(() => {
        if (searchParam.get("logout")) {
            window.opener.logout()
            window.close()
        }
    }, [searchParam.get("logout")])

    const popup = () => {
        window.open(`${authUrl}/logout`, 'popup', 'width=300,height=350');
        return false;
    }
    return (
        <>
            <Button {...rest} onClick={popup} >Logout</Button>
        </>
    )
}

export default LogoutButton;