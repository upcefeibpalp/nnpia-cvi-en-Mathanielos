import { Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function LoginButton() {
    const navigate = useNavigate();
    return (
        <Button variant="contained" onClick={() => navigate('/login')}>
            Přihlášení
        </Button>
    );
}
