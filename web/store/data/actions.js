import {
    FETCH_DATA_FAIL,
    FETCH_DATA_SUCCESS,
    FETCH_DATA_REQUEST,
    CLEAR_DATA_REQUEST
} from "./fetchTypes";

export const fetchData = () => async (dispatch) => {
    dispatch({ type: FETCH_DATA_REQUEST });

    try {
        await new Promise(resolve => setTimeout(resolve, 1000));

        const fakeResponce = {
            message: "OK",
            time: new Date(),
            code: 200
        }

        dispatch({
            type: FETCH_DATA_SUCCESS,
            payload: fakeResponce
        })

        dispatch({
            type: CLEAR_DATA_REQUEST,

        })
    } catch (error) {
        dispatch({
            type: FETCH_DATA_FAIL,
            payload: error.message
        })
    }


}
