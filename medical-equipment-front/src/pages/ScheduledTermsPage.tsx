import axios from 'axios';
import React, { useState, useEffect, useContext } from 'react';
import { Table, Container, Row, Spinner, Button } from 'react-bootstrap';
import { AuthContext } from '../services/AuthProvider';
import ConfirmationModal from '../components/modals/ConfirmationModal';

function ScheduledTermsPage() {
  const [terms, setTerms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [currentTerm, setCurrentTerm] = useState<any>(null);

  const authContext = useContext(AuthContext);

  const fetchTerms = async () => {
    try {
        const response = await axios.get(process.env.REACT_APP_API_URL + `api/terms/pending/for-user`,
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
        setTerms(response.data)
        setLoading(false)
    } catch (error) {
        console.log(error)                
    }

        setLoading(false)
    }
  useEffect(() => {
    if (authContext?.user?.token) {
        fetchTerms();
    }
  }, [authContext?.user?.token]);

  function openConfirmationDialog(term: any) {
    setCurrentTerm(term)
    setShowModal(true);
  }

  async function handleModalSubmit() {
    try {
        await axios.post(process.env.REACT_APP_API_URL + `api/terms/${currentTerm.id}/cancel`, {},
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
        await fetchTerms();
        setLoading(false)
        handleCloseModal();
    } catch (error) {
        console.log(error)                
    }
    setLoading(false)
  }

  const handleCloseModal = () => {
    setShowModal(false);
  }
 
  return (
    <div>
    {loading && 
        <Container className="mt-3">
        <Row className="justify-content-center">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </Row>
        </Container>}

        <Container className='mt-3'>
            <Row>

            <Table  bordered hover>
                <thead>
                <tr>
                    <th>Company</th>
                    <th>Starts at</th>
                    <th>Duration</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {terms.map((term:any) => (
                    <tr key={term.id}>
                        <td>{term.companyName}</td>
                        <td>{new Date(term.termStart).toLocaleDateString('sr-RS')} {new Date(term.termStart).toLocaleTimeString('sr-RS')}</td>
                        <td>{term.duration}</td>
                        <td>{term.cancellable && <Button className='btn-danger' onClick={() => openConfirmationDialog(term)}>Cancel</Button>}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
            </Row>
        </Container>
        <ConfirmationModal
                show={showModal} 
                handleClose={handleCloseModal} 
                handleSubmit={handleModalSubmit} 
            />
    </div>
  );
}

export default ScheduledTermsPage;