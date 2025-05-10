import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BrowserRouter } from 'react-router-dom';
import App from './App.jsx';
import './index.css';

// MUI Dark Theme
const theme = createTheme({
    palette: {
        mode: 'dark',
    },
});

// Tanstack Query Client
const queryClient = new QueryClient();

createRoot(document.getElementById('root')).render(
    <StrictMode>
        <BrowserRouter>
            <QueryClientProvider client={queryClient}>
                <ThemeProvider theme={theme}>
                    <CssBaseline />
                    <App />
                </ThemeProvider>
            </QueryClientProvider>
        </BrowserRouter>
    </StrictMode>
);
