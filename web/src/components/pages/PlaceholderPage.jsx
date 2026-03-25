import React from 'react';

const card = {
    borderRadius: 14,
    background: '#fff',
    border: '1px solid #e5e9ef',
    padding: 20,
    boxShadow: '0 3px 12px rgba(17, 24, 39, 0.06)'
};

const title = {
    margin: 0,
    fontSize: 20
};

const text = {
    marginTop: 10,
    color: '#4b5563'
};

export default function PlaceholderPage({ titleText }) {
    return (
        <div style={card}>
            <h1 style={title}>{titleText}</h1>
        </div>
    );
}
