import React from 'react';
import './index.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Voir from "./component/Voir";
import Ajouter from "./component/Ajouter";
import Signer from "./component/Signer";
import ReactDOM from "react-dom";
import HeaderBar from "./component/Menu/HeaderBar";
import {Layout} from 'antd';
import Accueil from "./component/Accueil/accueil";

ReactDOM.render(
    <Router>
        <HeaderBar Header={Layout.Header}/>
        <Routes>
            <Route path="/" element={<Accueil/>}/>
            <Route path="/voir" element={<Voir/>}/>
            <Route path="/ajouter" element={<Ajouter/>}/>
            <Route path="/signer" element={<Signer/>}/>
        </Routes>
    </Router>,

    document.getElementById("root")
);


