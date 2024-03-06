import React, { useContext, useState } from 'react';
import { Alert, Button, Col, Container, Form, Row, Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../services/AuthProvider';
import axios from 'axios';

function SignupPage() {
    const [name, setName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');
    const [city, setCity] = useState('');
    const [phone, setPhone] = useState('');
    const [profession, setProfession] = useState('');
    const [serverError, setServerError] = useState('');
    const [responseSuccess, setResponseSuccess] = useState(false);
    const [validated, setValidated] = useState(false);
    const [loading, setLoading] = useState(false);
  
    const navigate = useNavigate();
    const authContext = useContext(AuthContext);


    async function handleSubmit(event:any) {
        event.preventDefault();
        event.stopPropagation();

        const form = event.currentTarget;  
        setValidated(true);

        if (form.checkValidity() === false || repeatPassword !== password) {
            return;
        }

        setLoading(true);

        try {
            await axios.post(process.env.REACT_APP_API_URL + 'api/auth/signup', 
            { email, password, name, lastName, city, phone, profession });
            
            setResponseSuccess(true);
            
            setTimeout(() => {
                navigate('/login')
            }, 500)
        } catch (error:any) {
            console.log(error)
            if (error.response.status === 400)
                setServerError(error.response.data.message);
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
                  <h2 className="mb-4">SignUp</h2>
                  {serverError && <Alert variant="danger">{serverError}</Alert>}
                  {responseSuccess && <Alert variant="success">Registration successful</Alert>}
                  <Form noValidate validated={validated} onSubmit={handleSubmit}>

                  <Form.Group controlId="formName">
                      <Form.Label>First Name</Form.Label>
                      <Form.Control
                        type="text"
                        required
                        placeholder="First Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert a name
                        </Form.Control.Feedback>
                    </Form.Group>

                    
                  <Form.Group controlId="formLastName">
                      <Form.Label>Last Name</Form.Label>
                      <Form.Control
                        type="text"
                        required
                        placeholder="Last Name"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert a last name
                        </Form.Control.Feedback>
                    </Form.Group>

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

                    <Form.Group controlId="formRepeatPassword">
                      <Form.Label>Repeat password</Form.Label>
                      <Form.Control
                        type="password"
                        placeholder="Repeat password"
                        value={repeatPassword}
                        required
                        isInvalid={repeatPassword !== password || repeatPassword.length === 0}
                        isValid={repeatPassword === password && repeatPassword.length !== 0}
                        onChange={(e) => setRepeatPassword(e.target.value)}
                      />
                      <Form.Control.Feedback type="invalid">
                            Please insert matching password
                      </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formCity">
                      <Form.Label>City</Form.Label>
                      <Form.Control
                        type="text"
                        required
                        placeholder="City"
                        value={city}
                        onChange={(e) => setCity(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert a city
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formPhone">
                      <Form.Label>Phone</Form.Label>
                      <Form.Control
                        type="text"
                        required
                        placeholder="Phone"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert a phone
                        </Form.Control.Feedback>
                    </Form.Group>

                    <Form.Group controlId="formProfession">
                      <Form.Label>Profession</Form.Label>
                      <Form.Control
                        type="text"
                        required
                        placeholder="Profession"
                        value={profession}
                        onChange={(e) => setProfession(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert a profession
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
  
  export default SignupPage;