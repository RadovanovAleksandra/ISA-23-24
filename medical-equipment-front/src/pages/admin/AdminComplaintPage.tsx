import axios from 'axios';
import React, { useState, useEffect, useContext } from 'react';
import { Table, Container, Row, Spinner, Button } from 'react-bootstrap';
import { AuthContext } from '../../services/AuthProvider';
import AnswerComplaintModal from '../../components/modals/AnswerComplaintModal';

function AdminComplaintPage() {
  const [complaints, setComplaints] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [currentComplaintId, setCurrentComplaintId] = useState(0);

  const authContext = useContext(AuthContext);

  const fetchComplaints = async () => {
    try {
        const response = await axios.get(process.env.REACT_APP_API_URL + `api/complaints/for-admin`,
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
        setComplaints(response.data)
        setLoading(false)
    } catch (error) {
        console.log(error)                
    }

    setLoading(false)
}

  useEffect(() => {
    if (authContext?.user?.token) {
        fetchComplaints();
    }
  }, [authContext?.user?.token]);

    const handleOpenModal = (complaintId:any) => {
        setCurrentComplaintId(complaintId)
        setShowModal(true);
    }
    const handleCloseModal = () => setShowModal(false);

    const handleAnswerComplaint = async (complaintId: number, answer: string ) => {
    setLoading(true);
    try {
        const response = await axios.post(process.env.REACT_APP_API_URL + `api/complaints/answer`,{
            id: complaintId,
            answer
        }, 
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
        console.log(response.data)
        await fetchComplaints();
    } catch (error) {
        console.log(error)                
    }

    setLoading(false);

    handleCloseModal();
    };
 

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
            <Table bordered hover>
                <thead>
                <tr>
                    <th>Created at</th>
                    <th>Created by</th>
                    <th>Text</th>
                    <th>Complain for</th>
                    <th>Answer</th>
                </tr>
                </thead>
                <tbody>
                {complaints.map((complaint:any) => (
                    <tr key={complaint.id}>
                        <td>{new Date(complaint.timestamp).toLocaleDateString('sr-RS')} {new Date(complaint.timestamp).toLocaleTimeString('sr-RS')}</td>
                        <td>{complaint.userName}</td>
                        <td>{complaint.text}</td>
                        <td>{complaint.companyAdminName ? complaint.companyAdminName :  complaint.companyName}</td>
                        <td>
                        <Button variant="primary" onClick={() => handleOpenModal(complaint.id)}>
                            Answer
                        </Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            </Row>
        </Container>
        <AnswerComplaintModal 
            show={showModal} 
            handleClose={handleCloseModal} 
            handleAnswerComplaint={handleAnswerComplaint} 
            complaintId={currentComplaintId}
          />
      
    </div>
  );
}

export default AdminComplaintPage;