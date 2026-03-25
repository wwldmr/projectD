import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { doUpdateUrl } from '../../store/data/routerReducer';

const navItems = [
    { to: '/workspace', label: 'Рабочий стол' },
    { to: '/administration', label: 'Администрирование' },
    { to: '/analytics', label: 'Аналитический блок' },
    { to: '/sim-cards', label: 'Сим карты' },
    { to: '/reports', label: 'Отчеты' },
    { to: '/requests', label: 'Заявки' }
];

const sidebarStyle = {
    background: '#101828',
    color: '#e6edf7',
    padding: '18px 12px',
    borderRight: '1px solid #1f2937'
};

const title = {
    margin: '0 10px 16px 10px',
    fontSize: 13,
    textTransform: 'uppercase',
    opacity: 0.7,
    letterSpacing: 0.7
};

const linkBase = {
    display: 'block',
    padding: '10px 12px',
    marginBottom: 4,
    borderRadius: 8,
    textDecoration: 'none',
    color: '#e5e7eb',
    fontSize: 14
};

export default function Sidebar() {
    const dispatch = useDispatch();
    const pathname = useSelector(s => s.routing.pathname);

    return (
        <aside style={sidebarStyle}>
            <h2 style={title}>Навигация</h2>
            {navItems.map(({ to, label }) => {
                const isSimDetails = to === '/sim-cards' && /^\/sim-cards\/(\d+)\/description$/.test(pathname);
                const isActive = pathname === to || isSimDetails;

                return (
                    <a
                        key={to}
                        href={to}
                        onClick={e => {
                            e.preventDefault();
                            if (pathname !== to) {
                                dispatch(doUpdateUrl(to));
                            }
                        }}
                        style={{
                            ...linkBase,
                            background: isActive ? '#344054' : 'transparent',
                            fontWeight: isActive ? 700 : 500
                        }}
                    >
                        {label}
                    </a>
                );
            })}
        </aside>
    );
}
