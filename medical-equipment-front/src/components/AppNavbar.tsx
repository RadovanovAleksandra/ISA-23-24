import React, { useContext } from 'react';
import { Button, Container, Nav, Navbar } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../services/AuthProvider';

function AppNavbar() {

    const authContext = useContext(AuthContext);
    const navigate = useNavigate();

    function isLoggedIn() {
        return authContext?.user!!;
    }

    function isAdmin() {
        return authContext?.user?.role && authContext?.user?.role === 'ADMIN';
    }

    function isCompanyAdmin() {
        return authContext?.user?.role && authContext?.user?.role === 'COMPANY_ADMIN';
    }

    function isRegularUser() {
        return authContext?.user?.role && authContext?.user?.role === 'USER';
    }

    function logout() {
        authContext?.logout();
        navigate('/')
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
                        <Nav.Link as={Link} to="/">Home</Nav.Link>
                        <Nav.Link as={Link} to="/companies">Companies</Nav.Link>
                        {isCompanyAdmin() && <Nav.Link as={Link} to="/statistics">Statistics</Nav.Link>}
                        {isRegularUser() && <Nav.Link as={Link} to="/successful-reservations/qr">QR reservations</Nav.Link>}
                        {isRegularUser() && <Nav.Link as={Link} to="/successful-reservations">Successful handovers</Nav.Link>}
                        {isRegularUser() && <Nav.Link as={Link} to="/scheduled-terms">Scheduled terms</Nav.Link>}
                        {isLoggedIn() && <Nav.Link as={Link} to="/profile">Profile</Nav.Link>}
                        {isRegularUser() && <Nav.Link as={Link} to="/companies/rate">Rate companies</Nav.Link>}
                        {isAdmin() && <Nav.Link as={Link} to="/admin/complaint">Complaints</Nav.Link>}
                        {isAdmin() && <Nav.Link as={Link} to="/admin/loyalty-programs">Loyalty programs</Nav.Link>}
                        {isRegularUser() && <Nav.Link as={Link} to="/complaint">Complaint</Nav.Link>}
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