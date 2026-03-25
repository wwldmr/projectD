import axios from 'axios';

export function fetchSimCards() {
    return axios.get('/api/sim-cards').then(response => response.data);
}

export function fetchSimCardById(id) {
    return axios.get(`/api/sim-cards/${id}`).then(response => response.data);
}
