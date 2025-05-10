import { Container, Typography, Stack } from '@mui/material';
import LoginButton from '../components/LoginButton';
import RegisterButton from '../components/RegisterButton';

export default function HomePage() {
    return (
        <Container maxWidth="sm" sx={{ mt: 8 }}>
            <Typography variant="h4" gutterBottom>
                Vítejte v aplikaci NNPIA
            </Typography>
            <Stack spacing={2}>
                <LoginButton />
                <RegisterButton />
            </Stack>
        </Container>
    );
}
