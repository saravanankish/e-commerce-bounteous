import { combineReducers } from 'redux';
import loginReducer from './loginSlice';
import cartReducer from './cartSlice';

const rootReducer = combineReducers({
    login: loginReducer,
    cart: cartReducer,
})

export default rootReducer;