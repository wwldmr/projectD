import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { authLoginSuccess } from '../../store/data/authReducer';
import { doUpdateUrl } from '../../store/data/routerReducer';

const shell = {
    minHeight: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    background: '#f4f5f7'
};

const card = {
    width: 360,
    background: '#fff',
    border: '1px solid #e5e9ef',
    borderRadius: 14,
    padding: 24,
    boxShadow: '0 3px 12px rgba(17, 24, 39, 0.06)'
};

const title = {
    margin: '0 0 8px 0',
    fontSize: 22
};

const hint = {
    margin: '0 0 16px 0',
    color: '#667085',
    fontSize: 14,
    lineHeight: 1.45
};

const input = {
    width: '100%',
    boxSizing: 'border-box',
    border: '1px solid #d0d5dd',
    borderRadius: 8,
    padding: '10px 12px',
    marginBottom: 12,
    fontSize: 14
};

const button = {
    width: '100%',
    border: 'none',
    borderRadius: 8,
    padding: '10px 12px',
    fontSize: 14,
    fontWeight: 600,
    color: '#fff',
    background: '#1d4ed8',
    cursor: 'pointer'
};

const errorText = {
    marginTop: 12,
    color: '#b42318',
    fontSize: 13
};

export default function LoginPage() {
    const dispatch = useDispatch();
    const [error, setError] = useState('');

    const onSubmit = async e => {
        e.preventDefault();
        setError('');

        const username = e.target.username.value.trim();
        const password = e.target.password.value;

        try {
            const response = await fetch('/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                throw new Error('Invalid login or password');
            }

            const data = await response.json();
            dispatch(
                authLoginSuccess({
                    token: 'session',
                    username: data.username,
                    roles: Array.isArray(data.roles) ? data.roles : []
                })
            );

            dispatch(doUpdateUrl('/workspace'));
        } catch (err) {
            setError('Не удалось выполнить вход. Проверьте логин и пароль.');
        }
    };

    return (
        <div style={shell}>
            <form style={card} onSubmit={onSubmit}>
                <h1 style={title}>Вход</h1>
                <p style={hint}>
                    Тестовые пользователи: admin / admin123 и user / user123.
                </p>
                <input style={input} name="username" placeholder="Логин" required />
                <input style={input} name="password" type="password" placeholder="Пароль" required />
                <button style={button} type="submit">
                    Войти
                </button>
                {error && <div style={errorText}>{error}</div>}
            </form>
        </div>
    );
}
