import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { doUpdateUrl } from '../../../store/data/routerReducer';
import { setSelectedSimCard } from '../../../store/data/uiReducer';
import { fetchSimCards } from '../../api/simCardsApi';

const panel = {
    borderRadius: 14,
    background: '#fff',
    border: '1px solid #e5e9ef',
    boxShadow: '0 3px 12px rgba(17, 24, 39, 0.06)',
    overflow: 'hidden'
};

const header = {
    padding: 16,
    borderBottom: '1px solid #e8edf3',
    fontWeight: 600,
    fontSize: 18
};

const table = {
    width: '100%',
    borderCollapse: 'collapse'
};

const th = {
    textAlign: 'left',
    fontWeight: 600,
    fontSize: 13,
    padding: '12px 10px',
    color: '#475467',
    background: '#f8fafc',
    borderBottom: '1px solid #e8edf3'
};

const td = {
    padding: '10px',
    borderBottom: '1px solid #edf1f5',
    fontSize: 14
};

const actionBtn = {
    border: '1px solid #cbd5e1',
    borderRadius: 8,
    background: '#fff',
    color: '#0f172a',
    fontWeight: 600,
    padding: '6px 10px',
    cursor: 'pointer'
};

const message = {
    padding: 20,
    color: '#475467'
};

export default function SimTasksPage() {
    const dispatch = useDispatch();
    const [rows, setRows] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        let mounted = true;

        fetchSimCards()
            .then(data => {
                if (!mounted) return;
                setRows(Array.isArray(data) ? data : []);
                setError('');
            })
            .catch(() => {
                if (!mounted) return;
                setError('Не удалось загрузить данные SIM-карт.');
            })
            .finally(() => {
                if (!mounted) return;
                setLoading(false);
            });

        return () => {
            mounted = false;
        };
    }, []);

    const openDescription = id => {
        dispatch(setSelectedSimCard(id));
        dispatch(doUpdateUrl(`/sim-cards/${id}/description`));
    };

    return (
        <section style={panel}>
            <div style={header}>Страница задачи: SIM-карты</div>

            {loading && <div style={message}>Загрузка...</div>}
            {!loading && error && <div style={message}>{error}</div>}

            {!loading && !error && (
                <table style={table}>
                    <thead>
                        <tr>
                            <th style={{ ...th, width: 70 }}>Выделить</th>
                            <th style={th}>Мобильный оператор</th>
                            <th style={th}>DEF</th>
                            <th style={th}>ICCID</th>
                            <th style={th}>Статус SIM</th>
                            <th style={th}>Статус SIM (BDO)</th>
                            <th style={{ ...th, width: 170 }}>Показать описание</th>
                        </tr>
                    </thead>
                    <tbody>
                        {rows.map(row => (
                            <tr key={row.id}>
                                <td style={td}>
                                    <input type="checkbox" aria-label={`select-${row.id}`} />
                                </td>
                                <td style={td}>{row.mobileOperator || '—'}</td>
                                <td style={td}>{row.defNumber || '—'}</td>
                                <td style={td}>{row.iccid || '—'}</td>
                                <td style={td}>{row.simStatus || '—'}</td>
                                <td style={td}>{row.simStatusBdo || '—'}</td>
                                <td style={td}>
                                    <button style={actionBtn} onClick={() => openDescription(row.id)}>
                                        Описание
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </section>
    );
}
