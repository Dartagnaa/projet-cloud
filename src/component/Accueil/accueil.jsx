import {Component} from "react";
import classes from "./style.module.css";

class Accueil extends Component{

    render(){
        return(
            <div>
                <div className={classes.head}>
                    <img src="O.png" alt="logo"/>
                </div>
                <div className={classes.main}>
                    <h2 className={classes.center}>Bienvenue sur OCMACO</h2>
                    <p className={classes.p}>Le but de cette plateforme est de vous permettre de visualiser et de signer des pétitions
                    qui pourront nous aider à améliorer le monde d'aujourd'hui ainsi qu'à aider des personnes en
                    difficultés.</p>

                    <h2>Afficher des petitions</h2>
                    <p>...</p>
                </div>

            </div>
        );
    }
}
export default Accueil;