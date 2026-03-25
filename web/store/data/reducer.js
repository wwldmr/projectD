import {
    FETCH_DATA_FAIL,
    FETCH_DATA_SUCCESS,
    FETCH_DATA_REQUEST,
    CLEAR_DATA_REQUEST
} from "./fetchTypes";

const initialState = {
    loading: false,
    data: [],
    error: null,
}

export function dataReducer(state = initialState, action) {
    switch (action.type) {
        case FETCH_DATA_REQUEST:
            return {...state, loading: true, error: null}
        case FETCH_DATA_SUCCESS:
            return {...state, loading: false, data: action.payload }
        case FETCH_DATA_FAIL:
            return {...state, loading: false, error: action.payload }
        case CLEAR_DATA_REQUEST:
            return {data: null, loading: false, error: null}

        default: return state
    }
}