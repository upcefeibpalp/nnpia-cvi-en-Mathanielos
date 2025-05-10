import { createSlice } from '@reduxjs/toolkit';

const initialToken = localStorage.getItem('token');

const authenticationSlice = createSlice({
    name: 'auth',
    initialState: {
        token: initialToken || null,
    },
    reducers: {
        login: (state, action) => {
            const token = action.payload;
            state.token = token;
            localStorage.setItem('token', token);
        },
        logout: (state) => {
            state.token = null;
            localStorage.removeItem('token');
        },
    },
});

export const { login, logout } = authenticationSlice.actions;
export default authenticationSlice.reducer;
