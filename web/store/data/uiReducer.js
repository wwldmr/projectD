const initialState = {
    selectedSimCardId: null
};

export function uiReducer(state = initialState, action) {
    switch (action.type) {
        case 'SET_SELECTED_SIM_CARD':
            return { ...state, selectedSimCardId: action.payload };
        default:
            return state;
    }
}

export const setSelectedSimCard = id => ({
    type: 'SET_SELECTED_SIM_CARD',
    payload: id
});
