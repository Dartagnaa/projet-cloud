import { Space } from 'antd';
import React from 'react';
import classes from './style.module.css';
import {Link} from "react-router-dom";
import 'antd/dist/antd.min.css';

const HeaderBar = (props) => {
    const {Header} = props;

    const styleHeader = {
        height: '50px',
        paddingBottom:'10px',
        margin:'0px',
        backgroundColor: '#FFE714',
    };

    return (
        <div className={classes.div}>
            <Header style={styleHeader}>
                <div>
                    <Space direction="horizontal" size="middle" style={{ display: 'flex', paddingTop:'13px' }}>
                        <Link to='/' className={classes.link}>
                            Accueil
                        </Link>
                        <Link to='/voir' className={classes.link}>
                            Voir les pétitons
                        </Link>
                        <Link to='/ajouter' className={classes.link}>
                            Créer une pétition
                        </Link>
                        <Link to='/signer' className={classes.link}>
                            Signer une pétition
                        </Link>
                    </Space>
                </div>
            </Header>
            <br/>
        </div>
    );
};

export default HeaderBar;
