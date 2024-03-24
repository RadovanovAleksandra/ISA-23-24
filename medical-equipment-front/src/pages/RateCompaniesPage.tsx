import axios from 'axios';
import React, { useState, useEffect, useContext } from 'react';
import { Table, Container, Row, Spinner, Button } from 'react-bootstrap';
import RateCompanyModal from '../components/modals/RateCompanyModal';
import { AuthContext } from '../services/AuthProvider';

function RateCompaniesPage() {
  const [companies, setCompanies] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [currentCompanyId, setCurrentCompanyId] = useState(0);

  useEffect(() => {
    const fetchCompanies = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + `api/companies`);
            setCompanies(response.data)
            setLoading(false)
        } catch (error) {
            console.log(error)                
        }

        setLoading(false)
    }

    fetchCompanies();
  }, []);

  const authContext = useContext(AuthContext);

  const handleOpenModal = (companyId : any) => {
    setShowModal(true);
    setCurrentCompanyId(companyId);
  }

  const handleCloseModal = () => setShowModal(false);

  async function handleRateCompany(comment: string, rate:any) {
    setLoading(true);
    try {
        const response = await axios.post(process.env.REACT_APP_API_URL + `api/company-rates`,{
            companyId: currentCompanyId,
            comment,
            rate: rate
        }, 
        {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
        console.log(response.data)
    } catch (error) {
        console.log(error)                
    }

    setLoading(false);

    handleCloseModal();
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
            <Table bordered hover>
                <thead>
                <tr>
                    <th>Company name</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {companies.map((company:any) => (
                    <tr key={company.id}>
                    <td>{company.name}</td>
                    <td>
                        <Button variant="primary" onClick={() => handleOpenModal(company.id)}>
                            Add rate
                        </Button>
                    </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <RateCompanyModal
                show={showModal} 
                handleClose={handleCloseModal} 
                handleRateCompany={handleRateCompany} 
            />
            </Row>
        </Container>
      
    </div>
  );
}

export default RateCompaniesPage;