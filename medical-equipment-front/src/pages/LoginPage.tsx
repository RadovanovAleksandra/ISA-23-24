import React, { useContext, useState } from 'react';
import { Alert, Button, Col, Container, Form, Row, Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../services/AuthProvider';
import axios from 'axios';
import { User } from '../types/Types';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [serverError, setServerError] = useState('');
    const [validated, setValidated] = useState(false);
    const [loading, setLoading] = useState(false);
  
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);


    async function handleSubmit(event:any) {
        event.preventDefault();
        event.stopPropagation();

        const form = event.currentTarget;  
        setValidated(true);

        if (form.checkValidity() === false) {
            return;
        }

        setLoading(true);

        try {
            const response = await axios.post<User>(process.env.REACT_APP_API_URL + 'api/auth/login', { email, password });
            
            if (authContext) {
              authContext.login(response.data);
            }
            
            navigate('/')
        } catch (error:any) {
            console.log(error)
            if (error.response.status === 400)
                setServerError("Login failed");
            else {
                setServerError("Server error");
            }
        }

        setLoading(false);
    }

    return (
            <Container>
              <Row className="justify-content-md-center">
                <Col md={4}>
                  <h2 className="mb-4">Login</h2>
                  {serverError && <Alert variant="danger">{serverError}</Alert>}
                  <Form noValidate validated={validated} onSubmit={handleSubmit}>
                    <Form.Group controlId="formBasicEmail">
                      <Form.Label>Email address</Form.Label>
                      <Form.Control
                        type="email"
                        required
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert valid email
                        </Form.Control.Feedback>
                    </Form.Group>
        
                    <Form.Group controlId="formBasicPassword">
                      <Form.Label>Password</Form.Label>
                      <Form.Control
                        type="password"
                        placeholder="Password"
                        value={password}
                        required
                        minLength={6}
                        onChange={(e) => setPassword(e.target.value)}
                      />
                      <Form.Control.Feedback type="invalid">
                            Please insert valid password
                      </Form.Control.Feedback>
                    </Form.Group>
        
                    <Button variant="primary" type="submit">
                      Submit
                    </Button>
                  </Form>
                </Col>
              </Row> 
              {loading && 
                <Container className="mt-3">
                <Row className="justify-content-center">
                    <Spinner animation="border" role="status">
                        <span className="visually-hidden">Loading...</span>
                    </Spinner>
                </Row>
                </Container>}
            </Container>                
    )
  }
  
  export default LoginPage;