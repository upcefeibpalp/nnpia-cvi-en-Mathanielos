import { Routes, Route, Navigate } from 'react-router-dom';
import UsersPage from './pages/UsersPage';
import RegisterPage from './pages/RegisterPage';

function App() {
    return (
        <Routes>
            <Route path="/" element={<Navigate to="/users" />} />
            <Route path="/users" element={<UsersPage />} />
            <Route path="/register" element={<RegisterPage />} />
        </Routes>
    );
}

export default App;
