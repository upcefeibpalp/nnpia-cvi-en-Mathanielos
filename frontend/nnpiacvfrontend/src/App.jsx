import React from 'react';
import { Container, Typography } from '@mui/material';
import UserTable from './components/UserTable';
import './App.css';

function App() {
    const sampleUsers = [
        {
            id: 1,
            email: 'alice@example.com',
            password: 'alicePass',
            version: 1,
            profile: { id: 1, fullName: 'Alice A.', bio: 'Bio Alice' },
            active: false,
        },
        {
            id: 2,
            email: 'bob@example.com',
            password: 'bobPass',
            version: 3,
            profile: { id: 2, fullName: 'Bob B.', bio: 'Bio Bob' },
            active: true,
        },
        {
            id: 3,
            email: 'carol@example.com',
            password: 'carolPass',
            version: 2,
            profile: { id: 3, fullName: 'Carol C.', bio: 'Bio Carol' },
            active: false,
        }
    ];

    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Typography variant="h4" gutterBottom>
                NNPIA – Single-page Application
            </Typography>

            <UserTable initialUsers={sampleUsers} />
        </Container>
    );
}

export default App;
