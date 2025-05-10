import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

export default function ProtectedLoginRoute({ children }) {
    const token = useSelector(state => state.auth.token);

    // Pokud je uživatel přihlášen, přesměrujeme ho jinam (např. na home)
    if (token) {
        return <Navigate to="/" replace />;
    }

    return children;
}
