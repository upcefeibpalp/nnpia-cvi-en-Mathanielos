import React from 'react';
import { useForm } from 'react-hook-form';
import { TextField, Button, Box } from '@mui/material';
import axios from 'axios';

export default function UserForm() {
    const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm();

    const onSubmit = async (data) => {
        try {
            const payload = {
                email: data.email,
                password: data.password,
                active: true,
                profile: {
                    fullName: data.fullName,
                    bio: data.bio,
                },
            };

            console.log('Odesílám data na backend:', payload);

            await axios.post('http://localhost:9000/api/v1/users', payload);
            reset();
            alert('Uživatel úspěšně přidán!');
        } catch (error) {
            console.error('Chyba při přidávání uživatele:', error);
            alert('Přidání uživatele selhalo.');
        }
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Box mb={2}>
                <TextField
                    label="Email"
                    fullWidth
                    size="medium"
                    error={!!errors.email}
                    helperText={errors.email ? 'Email je povinný a musí být platný.' : ''}
                    {...register('email', {
                        required: true,
                        pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                    })}
                />
            </Box>
            <Box mb={2}>
                <TextField
                    label="Heslo"
                    type="password"
                    fullWidth
                    size="medium"
                    error={!!errors.password}
                    helperText={errors.password ? 'Heslo musí mít alespoň 6 znaků.' : ''}
                    {...register('password', {
                        required: true,
                        minLength: 6,
                    })}
                />
            </Box>
            <Box mb={2}>
                <TextField
                    label="Celé jméno"
                    fullWidth
                    size="medium"
                    error={!!errors.fullName}
                    helperText={errors.fullName ? 'Celé jméno je povinné.' : ''}
                    {...register('fullName', { required: true })}
                />
            </Box>
            <Box mb={2}>
                <TextField
                    label="Bio"
                    multiline
                    rows={3}
                    fullWidth
                    size="medium"
                    {...register('bio')}
                />
            </Box>
            <Button variant="contained" type="submit" size="large">
                Odeslat
            </Button>
        </form>
    );
}
