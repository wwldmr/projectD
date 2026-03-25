// starting state for our URL pathname reducer
const initialState = {
    pathname: typeof location !== 'undefined' ? location.pathname : '/'
}

// the reducer itself
export const urlReducer = (state = initialState, action) => {
    if (action.type === 'UPDATE_URL') {
        return { pathname: action.payload }
    }
    return state
}

// an action creator for updating it
export const doUpdateUrl = pathname => ({ type: 'UPDATE_URL', payload: pathname })