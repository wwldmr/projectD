export function createStore(reducer) {
    let state = reducer(undefined, { type: "__INIT__" })
    let listeners = []

    return {
        dispatch(action) {
            state = reducer(state, action)
            listeners.forEach(listener => listener())
        },

        getState() {
            return state
        },

        subscribe(fn) {
            listeners.push(fn)
        }
    }
}