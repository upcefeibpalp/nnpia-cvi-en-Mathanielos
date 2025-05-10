import React from 'react';
import { useForm } from 'react-hook-form';
import { TextField, Button, Box } from '@mui/material';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { login } from '../store/authenticationSlice';
import { useNavigate } from 'react-router-dom';
import { API_URL } from '../config';

export default function UserLoginForm() {
    const { register, handleSubmit } = useForm();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onSubmit = async (data) => {
        try {
            const response = await axios.post(`${API_URL}/login`, {
                email: data.email,
                password: data.password
            });


            const token = response.data;
            dispatch(login(token));
            alert('Přihlášení úspěšné!');
            navigate('/users');  // přesměrování na hlavní stránku
        } catch (error) {
            console.error('Chyba při přihlášení:', error);
            alert('Přihlášení selhalo.');
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Box mb={2}>
                <TextField
                    label="Email"
                    fullWidth
                    {...register('email', { required: true })}
                />
            </Box>
            <Box mb={2}>
                <TextField
                    label="Heslo"
                    type="password"
                    fullWidth
                    {...register('password', { required: true })}
                />
            </Box>
            <Button variant="contained" type="submit">Přihlásit</Button>
        </form>
    );
}
