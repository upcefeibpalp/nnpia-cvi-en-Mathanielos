import { configureStore } from '@reduxjs/toolkit';
import authenticationReducer from './authenticationSlice';

const store = configureStore({
    reducer: {
        auth: authenticationReducer,
    },
});

export default store;
