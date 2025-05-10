import React from 'react';
import { TableContainer, Paper, Table, TableHead, TableRow, TableCell, TableBody, Button } from '@mui/material';
import axios from 'axios';
import { useQueryClient } from '@tanstack/react-query';

export default function UserTable({ users }) {
    const queryClient = useQueryClient();

    const toggleActive = async (userId, currentState) => {
        try {
            const endpoint = `http://localhost:9000/api/v1/users/${userId}/${currentState ? 'deactivate' : 'activate'}`;
            await axios.post(endpoint);
            queryClient.invalidateQueries(['users']); // ✅ znovu načte data
        } catch (err) {
            console.error('Chyba při změně stavu:', err);
        }
    };

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell>Password</TableCell>
                        <TableCell>Version</TableCell>
                        <TableCell>Full Name</TableCell>
                        <TableCell>Bio</TableCell>
                        <TableCell>Active</TableCell>
                        <TableCell>Action</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {users.map(user => (
                        <TableRow key={user.id}>
                            <TableCell>{user.id}</TableCell>
                            <TableCell>{user.email}</TableCell>
                            <TableCell>{user.password}</TableCell>
                            <TableCell>{user.version}</TableCell>
                            <TableCell>{user.profile?.fullName || ''}</TableCell>
                            <TableCell>{user.profile?.bio || ''}</TableCell>
                            <TableCell>{user.active ? 'Yes' : 'No'}</TableCell>
                            <TableCell>
                                <Button
                                    variant="contained"
                                    color={user.active ? 'error' : 'success'}
                                    onClick={() => toggleActive(user.id, user.active)}
                                >
                                    {user.active ? 'Deactivate' : 'Activate'}
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
