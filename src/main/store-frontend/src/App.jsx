import reactLogo from './assets/react.svg'
import './App.css'
import {useEffect, useState} from "react";

function ItemList() {
    const [loading, setLoading] = useState(true);
    const [item, setItem] = useState([]);

    useEffect(async () => {
        const res = await fetch("/api/items");
        setItem(await res.json());
        setLoading(false);
    }, [])

    if (loading) {
        return <div>Loading... </div>
    }
    return (
        <>
            {item.map(i =>
                <div>
                    {i.itemName} - {i.price}
                    <p></p>
                    {i.category}
                    <hr></hr>
                </div>)}
        </>
    );
}

function AddItem() {
    const [itemName, setItemName] = useState("");
    const [price, setPrice] = useState("");
    const [category, setCategory] = useState("");

    function handleSubmit(e) {
        e.preventDefault();

        //Server side fragment
        fetch("/api/items", {
            method: "post",
            body: JSON.stringify({item: itemName, price, category}),
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
                    <input type="text" value={itemName} onChange={e => setItemName(e.target.value)}/>
                </div>
                <div>
                    <label>Price: </label>
                    <input type="text" value={price} onChange={e => setPrice(e.target.value)}/>
                </div>
                <div>
                    <label id="label-category">Category: </label>
                    <select>
                        <option value="1">Hardware</option>
                        <option value="2">Games</option>
                        <option value="3">Songs</option>
                    </select>
                    <div>
                        <input type="text" value={category} onChange={e => setCategory(e.target.value)}/>
                    </div>

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
            <AddItem/>
        </div>
    )
}

export default App
