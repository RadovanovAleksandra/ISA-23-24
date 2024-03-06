import React, { useContext } from 'react';
import { Button, Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { AuthContext } from '../services/AuthProvider';

function AppNavbar() {

    const authContext = useContext(AuthContext);

    function isLoggedIn() {
        return authContext?.user!!;
    }

    function logout() {
        authContext?.logout();
    }

    return (
        <>
            <Navbar bg="dark" data-bs-theme="dark">
                <Container fluid>
                <Navbar.Brand href="#home">Medical equipment App</Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">
                    <Nav
                        className="me-auto my-2 my-lg-0"
                        style={{ maxHeight: '100px' }}
                        navbarScroll
                    >
                        <Nav.Link as={Link} to="/home">Home</Nav.Link>
                        {!isLoggedIn() && <Nav.Link as={Link} to="/login">Login</Nav.Link>}
                        {!isLoggedIn() && <Nav.Link as={Link} to="/signup">Sign Up</Nav.Link>}
                        {isLoggedIn() && <Button onClick={logout} >Logout</Button>}
                    </Nav>
                </Navbar.Collapse>
                </Container>
            </Navbar>
        </>  
    );
  }
  
  export default AppNavbar;