import { combineReducers } from 'redux';
import { dataReducer } from './data/reducer';
import { urlReducer } from './data/routerReducer';
import { uiReducer } from './data/uiReducer';
import { authReducer } from './data/authReducer';

export const rootReducer = combineReducers({
    data: dataReducer,
    routing: urlReducer,
    ui: uiReducer,
    auth: authReducer
});
