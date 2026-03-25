import React, { useState } from 'react'

export default function Button() {
    const [data, setData] = useState([])

    async function handleButtonClick() {
        try {
            const response = await fetch("/api/sim-cards")
            const json = await response.json()
            setData(json)
        } catch (error) {
            console.error(error)
        }
    }
    return (
        <div>
            <button onClick={handleButtonClick}>
                click me
            </button>
            <pre>{JSON.stringify(data, null, 2)}</pre>
        </div>

    )
}
