import { Container, Typography } from '@mui/material';
import UserLoginForm from '../components/UserLoginForm';

export default function LoginPage() {
    return (
        <Container maxWidth="sm" sx={{ mt: 8 }}>
            <Typography variant="h5" gutterBottom>
                Přihlášení uživatele
            </Typography>
            <UserLoginForm />
        </Container>
    );
}
