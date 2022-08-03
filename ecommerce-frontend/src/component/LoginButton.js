import { useSearchParams, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { Buffer } from "buffer";
import { Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { login } from '../redux/loginSlice';
import { authUrl, backendUrl } from '../config';

const LoginButton = ({ refresh, ...rest }) => {

    const [searchParam,] = useSearchParams();
    const [code, setCode] = useState('');
    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {
        window.getData = getData
    }, [])

    useEffect(() => {
        if (searchParam.get("code")) {
            window.opener.getData(searchParam.get("code"))
            window.close();
        }
        // eslint-disable-next-line 
    }, [searchParam.get("code")])

    const getData = (code) => {
        setCode(code)
    }

    useEffect(() => {
        if (code !== '') {
            axios.post(`${authUrl}/oauth2/token?client_id=e-commerce-site.TJQfmiEHV1ibmQ2nz0BsZ9Td44I1SZ5V&redirect_uri=http://127.0.0.1:3000/&grant_type=authorization_code&code=${code}&code_verifier=qPsH306-ZDDaOE8DFzVn05TkN3ZZoVmI_6x4LsVglQI&scope=e-commerce`, {}, { headers: { "Authorization": "Basic " + Buffer.from("e-commerce-site.TJQfmiEHV1ibmQ2nz0BsZ9Td44I1SZ5V:4McYM0hb0POnrliYCCAhhQC77fMuPgEL").toString("base64"), "Access-Control-Allow-Origin": "*" } }).then(res => {
                if (res.data.access_token) {
                    var token = res.data;
                    axios.get(`${backendUrl}/user`, { headers: { "Authorization": "Bearer " + token.access_token } }).then(data => {
                        if (data.data.userId) {
                            token.username = data.data.username;
                            token.received = new Date();
                            token.role = data.data.role;
                            sessionStorage.setItem("token", JSON.stringify(token))
                            navigate("/")
                            dispatch(login())
                        }
                    })
                }
            })
        }
        // eslint-disable-next-line 
    }, [code])

    const popup = () => {
        window.open(`${authUrl}/oauth2/authorize?response_type=code&client_id=e-commerce-site.TJQfmiEHV1ibmQ2nz0BsZ9Td44I1SZ5V&scope=e-commerce&redirect_uri=http://127.0.0.1:3000/&code_challenge=QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8&code_challenge_method=S256`, 'popup', 'width=300,height=350');
        return false;
    }

    return (
        <>
            <Button {...rest} onClick={popup} >Login</Button>
        </>
    )
}

export default LoginButton;