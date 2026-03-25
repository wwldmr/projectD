const storedToken = typeof localStorage !== 'undefined' ? localStorage.getItem('auth_token') : null;
const storedUsername = typeof localStorage !== 'undefined' ? localStorage.getItem('auth_username') : null;
const storedRoles = typeof localStorage !== 'undefined' ? localStorage.getItem('auth_roles') : null;

const initialState = {
    token: storedToken,
    username: storedUsername,
    roles: storedRoles ? JSON.parse(storedRoles) : [],
    isAuthenticated: Boolean(storedToken)
};

export function authReducer(state = initialState, action) {
    switch (action.type) {
        case 'AUTH_LOGIN_SUCCESS': {
            const { token, username, roles } = action.payload;

            localStorage.setItem('auth_token', token);
            localStorage.setItem('auth_username', username);
            localStorage.setItem('auth_roles', JSON.stringify(roles || []));

            return {
                token,
                username,
                roles: roles || [],
                isAuthenticated: true
            };
        }
        case 'AUTH_LOGOUT': {
            localStorage.removeItem('auth_token');
            localStorage.removeItem('auth_username');
            localStorage.removeItem('auth_roles');

            return {
                token: null,
                username: null,
                roles: [],
                isAuthenticated: false
            };
        }
        default:
            return state;
    }
}

export const authLoginSuccess = payload => ({
    type: 'AUTH_LOGIN_SUCCESS',
    payload
});

export const authLogout = () => ({
    type: 'AUTH_LOGOUT'
});
