import React, { useState } from 'react';
import {
    Table, TableBody, TableCell, TableContainer,
    TableHead, TableRow, Paper, Button
} from '@mui/material';

export default function UserTable({ initialUsers }) {
    const [users, setUsers] = useState(initialUsers);

    const toggleActive = (id) => {
        setUsers(users.map(u =>
            u.id === id ? { ...u, active: !u.active } : u
        ));
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
                            <TableCell>{user.profile.fullName}</TableCell>
                            <TableCell>{user.profile.bio}</TableCell>
                            <TableCell>{user.active ? 'Yes' : 'No'}</TableCell>
                            <TableCell>
                                <Button
                                    size="small"
                                    variant="contained"
                                    color={user.active ? 'error' : 'success'}
                                    onClick={() => toggleActive(user.id)}
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
