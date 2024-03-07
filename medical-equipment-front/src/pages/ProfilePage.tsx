import React, { useContext, useEffect, useState } from 'react';
import { Alert, Button, Col, Container, Form, Row, Spinner } from 'react-bootstrap';
import axios from 'axios';
import { AuthContext } from '../services/AuthProvider';

function ProfilePage() {
    const [name, setName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [city, setCity] = useState('');
    const [phone, setPhone] = useState('');
    const [state, setState] = useState('');
    const [profession, setProfession] = useState('');
    const [points, setPoints] = useState('');
    const [penalties, setPenalties] = useState('');
    const [loyaltyProgram, setLoyaltyProgram] = useState('');
    const [serverError, setServerError] = useState('');
    const [responseSuccess, setResponseSuccess] = useState(false);
    const [validated, setValidated] = useState(false);
    const [loading, setLoading] = useState(false);

    const authContext = useContext(AuthContext);

    useEffect(() => {
        if (!authContext?.user?.token) {
            setLoading(true);
            return;
        }

        const fetchProfile = async () => {
            try {
                const response = await axios.get(process.env.REACT_APP_API_URL + `api/profiles/${authContext?.user?.id}`,
                 {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
                
                 setName(response.data.name)
                 setLastName(response.data.lastName)
                 setCity(response.data.city)
                 setState(response.data.state)
                 setPhone(response.data.phone)
                 setProfession(response.data.profession)
                 setEmail(response.data.email)
                 setPoints(response.data.points)
                 setLoyaltyProgram(response.data.loyaltyProgram)
                 setPenalties(response.data.penalties)
                 
            } catch (error:any) {
                console.log(error)                
                setServerError("Failed to load user data");
            }

            setLoading(false)
        }

        fetchProfile();
    }, [authContext?.user?.id, authContext?.user?.token]);
  
    async function handleSubmit(event:any) {
        event.preventDefault();
        event.stopPropagation();
      
        setResponseSuccess(false);
        setServerError('');

        const form = event.currentTarget;  
        setValidated(true);

        if (form.checkValidity() === false) {
            return;
        }

        setLoading(true);

        try {
            await axios.put(process.env.REACT_APP_API_URL + `api/profiles/${authContext?.user?.id}`, 
            { name, lastName, city, phone, profession, state },
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            
            setResponseSuccess(true);
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
                  <h2 className="mb-4">Change profile data</h2>
                  {serverError && <Alert variant="danger">{serverError}</Alert>}
                  {responseSuccess && <Alert variant="success">Profile updated successfully</Alert>}
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
                        value={email}
                        disabled
                      />
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

                    <Form.Group controlId="formState">
                      <Form.Label>State</Form.Label>
                      <Form.Control
                        type="text"
                        required
                        placeholder="State"
                        value={state}
                        onChange={(e) => setState(e.target.value)}
                      />
                        <Form.Control.Feedback type="invalid">
                            Please insert a state
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
                <Container>
                <Row>Number of points: {points}</Row>
                <Row>Number of penalties: {penalties}</Row>
                <Row>Loyalty program: {loyaltyProgram ? loyaltyProgram : 'No enrolled loyalty program yet'}</Row>
                </Container>
            </Container>                
    )
  }
  
  export default ProfilePage;