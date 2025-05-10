import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import UserForm from '../components/UserForm';

export default function RegisterPage() {
    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Box textAlign="center">
                <Typography variant="h4" gutterBottom>
                    Registrace uživatele
                </Typography>
            </Box>
            <UserForm />
        </Container>
    );
}
