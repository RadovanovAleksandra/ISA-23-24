import { useContext, useEffect, useState } from "react";
import { Complaint } from "../types/Types";
import { Button, Container, Row, Spinner, Table } from "react-bootstrap";
import NewComplaintModal from "../components/modals/NewCompaintModal";
import axios from "axios";
import { AuthContext } from "../services/AuthProvider";

function ComplaintPage() {
      const [complaints, setComplaints] = useState<Complaint[]>([]);
      const [companies, setCompanies] = useState<any[]>([]);
      const [companyAdmins, setCompanyAdmins] = useState<any[]>([]);
      const [showModal, setShowModal] = useState(false);
      const [loading, setLoading] = useState(true);

      const authContext = useContext(AuthContext);

      async function fetchComplaints() {
        try {
            const response = await axios.get<Complaint[]>(process.env.REACT_APP_API_URL + `api/complaints/for-user`, 
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

            try {
                const response = await axios.get(process.env.REACT_APP_API_URL + `api/companies/for-user`, 
                {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
                console.log(response.data)
                setCompanies(response.data);
            } catch (error) {
                console.log(error)                
            }

            try {
                const response = await axios.get(process.env.REACT_APP_API_URL + `api/users/company-admins/for-user`, 
                {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
                console.log(response.data)
                setCompanyAdmins(response.data);
            } catch (error) {
                console.log(error)                
            }
    
            setLoading(false)
        }
    
        fetchData();
      }, [authContext?.user?.token]);
    
      const handleOpenModal = () => setShowModal(true);
      const handleCloseModal = () => setShowModal(false);
    
      const handleAddComplaint = async (title: string, selectedComapny: string, selectedComapnyAdmin: string ) => {
        setLoading(true);
        try {
            const response = await axios.post(process.env.REACT_APP_API_URL + `api/complaints`,{
                text: title,
                adminId: selectedComapnyAdmin,
                companyId: selectedComapny
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
          <h1>Complaints</h1>
          <Table bordered hover>
                <thead>
                <tr>
                    <th>Created</th>
                    <th>Text</th>
                    <th>Complain for</th>
                    <th>Answer</th>
                </tr>
                </thead>
                <tbody>
                {complaints.map((complaint:Complaint) => (
                    <tr key={complaint.id}>
                        <td>{new Date(complaint.timestamp).toLocaleDateString('sr-RS')} {new Date(complaint.timestamp).toLocaleTimeString('sr-RS')}</td>
                        <td>{complaint.text}</td>
                        <td>{complaint.companyAdminName ? complaint.companyAdminName :  complaint.companyName}</td>
                        <td>{complaint.answer ? complaint.answer :  '/'}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
          <Button variant="primary" onClick={handleOpenModal}>
            Create New Complaint
          </Button>
          <NewComplaintModal 
            show={showModal} 
            handleClose={handleCloseModal} 
            handleAddComplaint={handleAddComplaint} 
            companies={companies}
            companyAdmins={companyAdmins}
          />
        </Container>
      );
}

export default ComplaintPage;