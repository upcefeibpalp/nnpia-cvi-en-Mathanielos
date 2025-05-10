/* eslint-env jest */
import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import UsersPage from './UsersPage';
import axios from 'axios';

// ✅ Mock axios
jest.mock('axios');

// ✅ Mock config (import.meta.env replacement)
jest.mock('../config', () => ({
    API_URL: 'http://localhost:9000',
}));

const queryClient = new QueryClient();

const renderWithClient = (ui) => {
    return render(
        <QueryClientProvider client={queryClient}>
            {ui}
        </QueryClientProvider>
    );
};

describe('UsersPage', () => {
    it('vykreslí komponentu', () => {
        axios.get.mockResolvedValue({ data: [] });
        renderWithClient(<UsersPage />);
        expect(screen.getByText(/Načítání/i)).toBeInTheDocument();
    });

    it('vykreslí data z API (mock)', async () => {
        const mockData = [
            {
                id: 1,
                email: 'test@example.com',
                password: 'secret',
                version: 1,
                profile: { fullName: 'Testovací Uživatel', bio: 'Bio' },
                active: true,
            },
        ];

        axios.get.mockResolvedValueOnce({ data: mockData });

        renderWithClient(<UsersPage />);

        await waitFor(() => {
            expect(screen.getByText('Seznam uživatelů')).toBeInTheDocument();
            expect(screen.getByText('test@example.com')).toBeInTheDocument();
            expect(screen.getByText('Testovací Uživatel')).toBeInTheDocument();
        });
    });
});
