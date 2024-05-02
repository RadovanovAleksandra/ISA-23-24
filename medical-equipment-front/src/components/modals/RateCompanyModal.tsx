import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

interface RateCompanyModalProps {
  show: boolean;
  handleClose: () => void;
  handleRateCompany: (comment: string, rate: number) => void;
}

const RateCompanyModal: React.FC<RateCompanyModalProps> = ({ show, handleClose, handleRateCompany }) => {
  const [comment, setComment] = useState('');
  const [selectedRate, setSelectedRate] = useState(1);

  const handleSubmit = () => {
    handleRateCompany(comment, selectedRate);
    setComment('');
  };

  const handleSelecteRateChange = (e:any) => {
    setSelectedRate(e.target.value);
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Rate company</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="comment">
            <Form.Label>Comment</Form.Label>
            <Form.Control 
              type="text" 
              placeholder="Enter comment" 
              value={comment} 
              onChange={(e) => setComment(e.target.value)} 
            />
          </Form.Group>

          <Form.Group controlId="selectRate">
            <Form.Label>Select rate:</Form.Label>
            <Form.Select
                 value={selectedRate}
                onChange={handleSelecteRateChange}>
                {[1,2,3,4,5].map((option, index) => (
                    <option key={index} value={option}>
                        {option}
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
        <Button variant="primary" onClick={handleSubmit} disabled={!selectedRate}>
          Save Rate
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RateCompanyModal;
