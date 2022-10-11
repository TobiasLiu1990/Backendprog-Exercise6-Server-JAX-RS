import reactLogo from './assets/react.svg'
import './App.css'
import {useEffect, useState} from "react";

function ItemList() {
    const [loading, setLoading] = useState(true);
    const [item, setItem] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await fetch("/api/items");
            setItem(await res.json());
            setLoading(false);
        })();
    }, [])

    if (loading) {
        return <div>Loading... </div>
    }
    return <ul>{item.map(i =>
        <div>
            {i.name} - {i.price}
            <p></p>
            {i.category}
            <hr></hr>
        </div>)}
    </ul>;
}

function AddBook() {
    const [item, setItem] = useState("");
    const [price, setPrice] = useState("");
    const [category, setCategory] = useState("");

    function handleSubmit(e) {
        e.preventDefault();

        //Server side fragment
        fetch("/api/items", {
            method: "post",
            body: JSON.stringify({item, price, category}),
            headers: {
                "Content-Type": "application/json"
            }
        });
    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Item: </label>
                    <input type="text" value={item} onChange={e => setItem(e.target.value)}/>
                </div>
                <div>
                    <label>Price: </label>
                    <input type="text" value={price} onChange={e => setPrice(e.target.value)}/>
                </div>
                <div>
                    <label>Category: </label>
                    <input type="text" value={category} onChange={e => setCategory(e.target.value)}/>
                </div>
                <button style={{borderColor: "black", margin: "1em"}}>Submit</button>
            </form>
        </div>
    );
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
            <AddBook/>
        </div>
    )
}

export default App
