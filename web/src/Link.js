import React from 'react'
import { useDispatch, useSelector } from "react-redux";
import { doUpdateUrl } from "../store/data/routerReducer"

export default function MyLink({ to, children }) {
    const dispatch = useDispatch();
    const current = useSelector(s => s.routing.pathname);

    return (
        <a
            href={to}
            onClick={(e) => {
                if (e.metaKey || e.ctrlKey) return;
                e.preventDefault();
                if (to !== current) dispatch(doUpdateUrl(to));
            }}
        >
            {children}
        </a>
    );
}
