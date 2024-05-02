import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

interface NewComplaintModalProps {
  show: boolean;
  complaintId: number;
  handleClose: () => void;
  handleAnswerComplaint: (complaintId: number, answer: string) => void;
}

const AnswerComplaintModal: React.FC<NewComplaintModalProps> = ({ show, complaintId, handleClose, handleAnswerComplaint }) => {
  const [answer, setAnswer] = useState('');

  const handleSubmit = () => {
    handleAnswerComplaint(complaintId, answer);
    setAnswer('');
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Set answer</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="complaintAnswer">
            <Form.Label>Answer</Form.Label>
            <Form.Control 
              type="text" 
              placeholder="Enter answer" 
              value={answer} 
              onChange={(e) => setAnswer(e.target.value)} 
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit} disabled={!answer}>
          Save answer
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default AnswerComplaintModal;
