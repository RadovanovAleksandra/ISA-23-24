import { useContext, useEffect, useState } from "react";
import { Button, Container, Row, Spinner, Table } from "react-bootstrap";
import axios from "axios";
import { AuthContext } from "../../services/AuthProvider";
import { Complaint } from "../../types/Types";
import AnswerComplaintModal from "../../components/modals/AnswerComplaintModal";

function AdminComplaintPage() {
    const [complaints, setComplaints] = useState<Complaint[]>([]);
    const [currentComplaintId, setCurrentComplaintId] = useState(0);
      const [showModal, setShowModal] = useState(false);
      const [loading, setLoading] = useState(true);

      const authContext = useContext(AuthContext);

      async function fetchComplaints() {
        try {
            const response = await axios.get<Complaint[]>(process.env.REACT_APP_API_URL + `api/complaints/for-admin`, 
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            setComplaints(response.data);
        } catch (error) {
            console.log(error)                
        }
      }

      useEffect(() => {
        const fetchData = async () => {
            setLoading(true);

            if (!authContext?.user?.token) {
                return;
            }

            await fetchComplaints();
    
            setLoading(false)
        }
    
        fetchData();
      }, [authContext?.user?.token]);
    
      const handleOpenModal = (complaintId : number) => {
        setShowModal(true);
        setCurrentComplaintId(complaintId);
      }

      const handleCloseModal = () => setShowModal(false);
    
      const handleAnswerComplaint = async (complaintId: number, answer: string) => {
        setLoading(true);
        try {
            const response = await axios.post(process.env.REACT_APP_API_URL + `api/complaints/answer`,{
                id: complaintId,
                answer: answer
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
        <Container>
            {loading && 
                <Container className="mt-3">
                    <Row className="justify-content-center">
                        <Spinner animation="border" role="status">
                            <span className="visually-hidden">Loading...</span>
                        </Spinner>
                    </Row>
                </Container>}
          <h1>Non-answered Complaints</h1>
          <Table bordered hover>
                <thead>
                <tr>
                    <th>Created at</th>
                    <th>Text</th>
                    <th>Created for</th>
                    <th>Created by</th>
                </tr>
                </thead>
                <tbody>
                {complaints.map((complaint:Complaint) => (
                    <tr key={complaint.id}>
                        <td>{new Date(complaint.timestamp).toLocaleDateString('sr-RS')} {new Date(complaint.timestamp).toLocaleTimeString('sr-RS')}</td>
                        <td>{complaint.text}</td>
                        <td>{complaint.companyAdminName ? complaint.companyAdminName :  complaint.companyName}</td>
                        <td>{complaint.userName}</td>
                        <td>
                        <Button variant="primary" onClick={() => handleOpenModal(complaint.id)}>
                            Answer
                        </Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
          <AnswerComplaintModal 
            show={showModal} 
            handleClose={handleCloseModal} 
            handleAnswerComplaint={handleAnswerComplaint} 
            complaintId={currentComplaintId}
          />
        </Container>
      );
}

export default AdminComplaintPage;