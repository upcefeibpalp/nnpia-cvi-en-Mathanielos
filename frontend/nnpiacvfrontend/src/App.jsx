import React from 'react';
import axios from 'axios';
import { Container, Typography } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import UserTable from './components/UserTable';
import './App.css';

const fetchUsers = async () => {
    const res = await axios.get('http://localhost:9000/api/v1/users');
    return res.data;
};

function App() {
    const { data: users = [], isLoading, error } = useQuery({
        queryKey: ['users'],
        queryFn: fetchUsers,
        refetchInterval: 5000 // automatický refresh každých 5s
    });

    if (isLoading) return <p>Načítání uživatelů...</p>;
    if (error) return <p>Chyba při načítání: {error.message}</p>;

    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Typography variant="h4" gutterBottom>
                NNPIA – Single-page Application
            </Typography>
            <UserTable users={users} />
        </Container>
    );
}

export default App;
