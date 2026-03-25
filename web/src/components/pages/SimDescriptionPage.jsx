import React, { useEffect, useState } from 'react';
import { fetchSimCardById } from '../../api/simCardsApi';

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
    fontSize: 14,
    verticalAlign: 'top'
};

const message = {
    padding: 20,
    color: '#475467'
};

export default function SimDescriptionPage({ simId }) {
    const [row, setRow] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        if (!simId) {
            setRow(null);
            setLoading(false);
            setError('');
            return;
        }

        let mounted = true;

        fetchSimCardById(simId)
            .then(data => {
                if (!mounted) return;
                setRow(data || null);
                setError('');
            })
            .catch(() => {
                if (!mounted) return;
                setError('Не удалось загрузить описание SIM-карты.');
            })
            .finally(() => {
                if (!mounted) return;
                setLoading(false);
            });

        return () => {
            mounted = false;
        };
    }, [simId]);

    const viewRow = row || {};

    return (
        <section style={panel}>
            <div style={header}>Описание SIM-карты</div>

            {loading && <div style={message}>Загрузка...</div>}
            {!loading && error && <div style={message}>{error}</div>}
            {!loading && !row && !error && <div style={message}>Данные по SIM-карте не найдены.</div>}

            <table style={table}>
                <thead>
                    <tr>
                        <th style={th}>Оператор</th>
                        <th style={th}>DEF-номер</th>
                        <th style={th}>ICCID</th>
                        <th style={th}>Статус SIM</th>
                        <th style={th}>Статус SIM (BDO)</th>
                        <th style={th}>Дата активации/деактивации</th>
                        <th style={th}>Номер объекта</th>
                        <th style={th}>Статус объекта</th>
                        <th style={th}>Адрес объекта</th>
                        <th style={th}>Модель оборудования</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style={td}>{viewRow.mobileOperator || '—'}</td>
                        <td style={td}>{viewRow.defNumber || '—'}</td>
                        <td style={td}>{viewRow.iccid || '—'}</td>
                        <td style={td}>{viewRow.simStatus || '—'}</td>
                        <td style={td}>{viewRow.simStatusBdo || '—'}</td>
                        <td style={td}>{(viewRow.activationDate || '—') + ' / ' + (viewRow.deactivationDate || '—')}</td>
                        <td style={td}>{viewRow.objectNumber ?? '—'}</td>
                        <td style={td}>{viewRow.objectStatus || '—'}</td>
                        <td style={td}>{viewRow.objectAddress || '—'}</td>
                        <td style={td}>{viewRow.equipmentModel || '—'}</td>
                    </tr>
                </tbody>
            </table>
        </section>
    );
}
