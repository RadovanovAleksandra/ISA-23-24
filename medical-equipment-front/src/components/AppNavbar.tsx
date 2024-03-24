import React, { useContext } from 'react';
import { Button, Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { AuthContext } from '../services/AuthProvider';

function AppNavbar() {

    const authContext = useContext(AuthContext);

    function isLoggedIn() {
        return authContext?.user!!;
    }

    function isAdmin() {
        return authContext?.user?.role && authContext?.user?.role === 'ADMIN';
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
                        <Nav.Link as={Link} to="/companies">Companies</Nav.Link>
                        {isLoggedIn() && <Nav.Link as={Link} to="/profile">Profile</Nav.Link>}
                        {isLoggedIn() && <Nav.Link as={Link} to="/companies/rate">Rate companies</Nav.Link>}
                        {isAdmin() && <Nav.Link as={Link} to="/admin/complaint">Complaints</Nav.Link>}
                        {isLoggedIn() && <Nav.Link as={Link} to="/complaint">Complaint</Nav.Link>}
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