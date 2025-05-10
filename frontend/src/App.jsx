import { Routes, Route, Navigate } from 'react-router-dom';
import HomePage from './pages/HomePage';
import UsersPage from './pages/UsersPage';
import RegisterPage from './pages/RegisterPage';
import LoginPage from './pages/LoginPage';
import ProtectedLoginRoute from './components/ProtectedLoginRoute';

function App() {
    return (
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/users" element={<UsersPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/login" element={
                <ProtectedLoginRoute>
                    <LoginPage />
                </ProtectedLoginRoute>
            } />
        </Routes>
    );
}

export default App;
