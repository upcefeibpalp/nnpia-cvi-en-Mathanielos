import React from 'react';
import { Container, Typography, Box } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import UserTable from '../components/UserTable';

const fetchUsers = async () => {
    const res = await axios.get('http://localhost:9000/api/v1/users');
    return res.data;
};

export default function UsersPage() {
    const { data: users = [], isLoading, error } = useQuery({
        queryKey: ['users'],
        queryFn: fetchUsers,
        refetchInterval: 5000
    });

    if (isLoading) return <p>Načítání uživatelů...</p>;
    if (error) return <p>Chyba: {error.message}</p>;

    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Box textAlign="center">
                <Typography variant="h4" gutterBottom>
                    Seznam uživatelů
                </Typography>
            </Box>
            <UserTable users={users} />
        </Container>
    );
}
