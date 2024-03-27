import axios from 'axios';
import React, { useState, useEffect, useContext } from 'react';
import { Table, Container, Row, Spinner, Button, Col } from 'react-bootstrap';
import { AuthContext } from '../../services/AuthProvider';
import LoyaltyProgramModal from '../../components/modals/LoyaltyProgramModal';
import DeleteModal from '../../components/modals/DeleteModal';

function LoyaltyProgramAdminPage() {
  const [programs, setPrograms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [modalMode, setModalMode] = useState(1);
  const [currentProgram, setCurrentProgram] = useState<any>(null);

  const authContext = useContext(AuthContext);

  const fetchPrograms = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + `api/loyalty-programs`,
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            setPrograms(response.data)
            setLoading(false)
        } catch (error) {
            console.log(error)                
        }

        setLoading(false)
    }   

  useEffect(() => {
    if (authContext?.user?.token) {
        fetchPrograms();
    }
  }, [authContext?.user?.token]);

  const handleCloseModal = () => {
    setShowModal(false);
    setShowDeleteModal(false);
  }

  const openAddModal = () => {
    setModalMode(1)
    setCurrentProgram(null)
    setShowModal(true);
  }
  const openEditModal = (program:any) => {
    setModalMode(2)
    setCurrentProgram(program)
    setShowModal(true);
  }

  const openDeleteModal = (program:any) => {
    setCurrentProgram(program)
    setShowDeleteModal(true);
  }

  async function handleModalSubmit(name: string, discountRate: number, minNumberOfPoints: number, newPoints: number) {
    if (modalMode === 1) {
        try {
            await axios.post(process.env.REACT_APP_API_URL + `api/loyalty-programs`, 
            {name, discountRate, minNumberOfPoints, newPoints},
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            await fetchPrograms();
            setLoading(false)
            handleCloseModal();
        } catch (error) {
            console.log(error)                
        }
        setLoading(false)
    } else {
        try {
            await axios.put(process.env.REACT_APP_API_URL + `api/loyalty-programs/${currentProgram?.id}`, 
            {id: currentProgram?.id, name, discountRate, minNumberOfPoints, newPoints},
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            await fetchPrograms();
            handleCloseModal();
        } catch (error) {
            console.log(error)                
        }
        setLoading(false)
    }
  }

  async function handleDeleteModalSubmit() {
        try {
            await axios.delete(process.env.REACT_APP_API_URL + `api/loyalty-programs/${currentProgram?.id}`,
            {headers: {Authorization: `Bearer ${authContext?.user?.token}`}});
            await fetchPrograms();
            handleCloseModal();
        } catch (error) {
            console.log(error)                
        }
        setLoading(false)
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
            <Row className='text-end mb-4'>
                <Col>
                    <Button className='btn-success' onClick={openAddModal}>New program</Button>
                </Col>
            </Row>
            <Row>
            <Table bordered hover>
                <thead>
                <tr>
                    <th>Name </th>
                    <th>Discount rate</th>
                    <th>Min number of points</th>
                    <th>Number of new points on each purchase</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {programs.map((program:any) => (
                    <tr key={program.id}>
                        <td>{program.name}</td>
                        <td>{program.discountRate}</td>
                        <td>{program.minNumberOfPoints}</td>
                        <td>{program.newPoints}</td>
                        <td>
                            <Button className='btn-primary mx-2' onClick={() => openEditModal(program)}>Edit</Button>
                            <Button className='btn-danger' onClick={() => openDeleteModal(program)}>Delete</Button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
            <LoyaltyProgramModal
                show={showModal} 
                handleClose={handleCloseModal} 
                handleModalSubmit={handleModalSubmit} 
                mode={modalMode}
                program={currentProgram}
            />
            <DeleteModal
                show={showDeleteModal} 
                handleClose={handleCloseModal} 
                handleSubmit={handleDeleteModalSubmit} 
            />
            </Row>
        </Container>
      
    </div>
  );
}

export default LoyaltyProgramAdminPage;