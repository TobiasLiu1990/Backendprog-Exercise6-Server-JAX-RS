import reactLogo from './assets/react.svg'
import './App.css'
import {useEffect, useState} from "react";

function ItemList() {
    const [loading, setLoading] = useState(true);
    const [item, setItem] = useState([]);
    const [price, setPrice] = useState([]);
    const [category, setCategory] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await fetch("/api/listItems");
            setItem(await res.json());
            setPrice(await res.json());
            setCategory(await res.json());
            setLoading(false);
        })();
    }, [])

    if (loading) {
        return <div>Loading... </div>
    }
    return <ul>{item.map(i => <div>{i.name}</div>)}</ul>;
}

function App() {

    return (
        <div className="App">
            <div>
                <img src="/vite.svg" className="logo" alt="Vite logo"/>
                <img src={reactLogo} className="logo react" alt="React logo"/>
            </div>

            <h1>Vite + React</h1>

            <ItemList/>
        </div>
    )
}

export default App
