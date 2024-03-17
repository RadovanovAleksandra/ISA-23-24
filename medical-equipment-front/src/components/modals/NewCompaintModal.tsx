import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

interface NewComplaintModalProps {
  show: boolean;
  companyAdmins: any[];
  companies: any[];
  handleClose: () => void;
  handleAddComplaint: (title: string, selectedComapny: string, selectedComapnyAdmin: string) => void;
}

const NewComplaintModal: React.FC<NewComplaintModalProps> = ({ show, companyAdmins, companies, handleClose, handleAddComplaint }) => {
  const [title, setTitle] = useState('');
  const [selectedComapnyAdmin, setSelectedCompanyAdmin] = useState('');
  const [selectedComapny, setSelectedCompany] = useState('');

  const handleSubmit = () => {
    handleAddComplaint(title, selectedComapny, selectedComapnyAdmin);
    setTitle('');
  };

  const handleSelecteCompanyChange = (e:any) => {
    setSelectedCompany(e.target.value);
  };

  const handleSelecteCompanyAdminChange = (e:any) => {
    setSelectedCompanyAdmin(e.target.value);
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Create New Complaint</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="complaintTitle">
            <Form.Label>Text</Form.Label>
            <Form.Control 
              type="text" 
              placeholder="Enter complaint title" 
              value={title} 
              onChange={(e) => setTitle(e.target.value)} 
            />
          </Form.Group>

          <Form.Group controlId="selectAdminOption">
            <Form.Label>Select company admin:</Form.Label>
            <Form.Select value={selectedComapnyAdmin} 
                onChange={handleSelecteCompanyAdminChange}
                disabled={!!selectedComapny}>
            <option value="">-- Please choose an option --</option>
            {companyAdmins.map((option, index) => (
                <option key={index} value={option.id}>
                {option.name}
                </option>
            ))}
            </Form.Select>
          </Form.Group>

          <Form.Group controlId="selectCompanyOption">
            <Form.Label>Select company:</Form.Label>
            <Form.Select
                 value={selectedComapny}
                onChange={handleSelecteCompanyChange}
                disabled={!!selectedComapnyAdmin}>
            <option value="">-- Please choose an option --</option>
            {companies.map((option, index) => (
                <option key={index} value={option.id}>
                {option.name}
                </option>
            ))}
            </Form.Select>
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit} disabled={!title || (!selectedComapny && !selectedComapnyAdmin)}>
          Save Complaint
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default NewComplaintModal;
