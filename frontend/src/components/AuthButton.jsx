import React from 'react';
import { Button } from '@mui/material';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../store/authenticationSlice';
import { useNavigate } from 'react-router-dom';

export default function AuthButton() {
    const token = useSelector((state) => state.auth.token);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogout = () => {
        dispatch(logout());
    };

    const handleLoginRedirect = () => {
        navigate('/login');
    };

    return token ? (
        <Button variant="outlined" color="secondary" onClick={handleLogout}>
            Odhlásit se
        </Button>
    ) : (
        <Button variant="contained" onClick={handleLoginRedirect}>
            Přihlásit se
        </Button>
    );
}
