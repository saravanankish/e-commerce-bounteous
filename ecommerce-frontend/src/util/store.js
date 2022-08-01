import { configureStore } from '@reduxjs/toolkit';
import myReducer from "./loginSlice";

export default configureStore({
    reducer: myReducer,
})