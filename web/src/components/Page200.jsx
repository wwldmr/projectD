import React from "react"
import {useDispatch, useSelector} from "react-redux";
import {fetchData} from "../../store/data/actions";
import {CLEAR_DATA_REQUEST} from "../../store/data/fetchTypes";
import {createStore} from "../myRedux/redux"
import {counterReducer} from "../myRedux/reducer";
import CustomReduxCounter from "./TestTask2/CustomReduxCounter";
import Sidebar from "./Sidebar";
import Button from "./Button";

const demoStore = createStore(counterReducer);

export default function Page200() {
    const dispatch = useDispatch()

    const { loading, data, error } = useSelector(state => state.data)

    return (
        <div>
            <h1>Page 200</h1>
        </div>
    )
}
