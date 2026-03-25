import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Sidebar from '../Sidebar';
import { authLogout } from '../../../store/data/authReducer';
import { doUpdateUrl } from '../../../store/data/routerReducer';

const shell = {
    display: 'grid',
    gridTemplateColumns: '260px 1fr',
    minHeight: '100vh',
    background: '#f4f5f7',
    color: '#1f2937',
    fontFamily: '"Segoe UI", Tahoma, Geneva, Verdana, sans-serif'
};

const contentArea = {
    display: 'flex',
    flexDirection: 'column'
};

const topBar = {
    height: 66,
    display: 'flex',
    justifyContent: 'flex-end',
    alignItems: 'center',
    gap: 10,
    padding: '0 28px',
    background: '#ffffff',
    borderBottom: '1px solid #dde2e8'
};

const account = {
    background: '#eef3ff',
    color: '#1d4ed8',
    border: '1px solid #c8d6ff',
    borderRadius: 10,
    fontWeight: 600,
    fontSize: 14,
    padding: '8px 14px'
};

const logoutButton = {
    border: '1px solid #d0d5dd',
    borderRadius: 8,
    background: '#fff',
    color: '#344054',
    padding: '7px 12px',
    fontSize: 13,
    cursor: 'pointer'
};

const pageContainer = {
    padding: 24
};

function roleLabel(roles) {
    if (roles.includes('ROLE_ADMIN')) {
        return 'Администратор';
    }
    if (roles.includes('ROLE_USER')) {
        return 'Пользователь';
    }
    return 'Гость';
}

export default function AppLayout({ children }) {
    const dispatch = useDispatch();
    const { username, roles } = useSelector(state => state.auth);

    const onLogout = () => {
        dispatch(authLogout());
        dispatch(doUpdateUrl('/login'));
    };

    return (
        <div style={shell}>
            <Sidebar />
            <section style={contentArea}>
                <header style={topBar}>
                    <div style={account}>{`${username} (${roleLabel(roles)})`}</div>
                    <button style={logoutButton} onClick={onLogout}>Выход</button>
                </header>
                <main style={pageContainer}>{children}</main>
            </section>
        </div>
    );
}
