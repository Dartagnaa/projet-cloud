import {Component} from "react";
import classes from "./style.module.css";
import { Button, Form, Input } from 'antd';
import 'antd/dist/antd.min.css';

class Ajouter extends Component{
    render(){
        const onFinish = (values) => {
            console.log('Success:', values);
        };

        const onFinishFailed = (errorInfo) => {
            console.log('Failed:', errorInfo);
        };
        return(
            <div className={classes.main}>
                <br/>
                <h1>Lancer une pétition</h1>
                <br/>
                <Form
                    name="petition"
                    labelCol={{
                        span: 8,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    initialValues={{
                        remember: true,
                    }}
                    size="small"
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete="off"
                >
                    <Form.Item className={classes.input}
                        label="Titre"
                        name="titre"
                        rules={[
                            {
                                required: true,
                                message: 'Veuillez renseigner le titre',
                            },
                        ]}
                    >
                        <Input size="large"/>
                    </Form.Item>

                    <Form.Item className={classes.input}
                        label="Description"
                        name="description"
                        rules={[
                            {
                                required: true,
                                message: 'Décrivez la cause que vous défendez...',
                            },
                        ]}
                    >
                        <Input.TextArea size="large"/>
                    </Form.Item>
                    <Form.Item className={classes.input}
                        wrapperCol={{
                            offset: 8,
                            span: 16,
                        }}
                    >
                        <Button type="primary" htmlType="submit">
                            Créer
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        );
    }
}
export default Ajouter;