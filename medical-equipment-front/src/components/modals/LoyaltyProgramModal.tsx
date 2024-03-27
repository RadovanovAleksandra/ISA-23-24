import React, { useEffect, useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

interface ModalProps {
  show: boolean;
  mode: number;
  program: any;
  handleClose: () => void;
  handleModalSubmit: (name: string, discountRate: number, minNumberOfPoints: number, newPoints: number) => void;
}

const LoyaltyProgramModal: React.FC<ModalProps> = ({ show, mode, program, handleClose, handleModalSubmit }) => {
  const [name, setName] = useState('');
  const [discountRate, setDiscountRate] = useState(0);
  const [minNumberOfPoints, setMinNumberOfPoints] = useState(0);
  const [newPoints, setNewPoints] = useState(0);

  useEffect(() => {
    if (program){
        setName(program.name)
        setDiscountRate(program.discountRate)
        setMinNumberOfPoints(program.minNumberOfPoints)
        setNewPoints(program.newPoints)
    } else {
        setName('')
        setDiscountRate(0)
        setMinNumberOfPoints(0)
        setNewPoints(0)
    }
  }, [program])

  const handleSubmit = () => {
    handleModalSubmit(name, discountRate, minNumberOfPoints, newPoints);
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>{mode === 1 ? 'Add new loyalty program' : 'Edit loyalty program'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="name">
            <Form.Label>Name</Form.Label>
            <Form.Control 
              type="text" 
              placeholder="Enter name" 
              value={name} 
              onChange={(e) => setName(e.target.value)} 
            />
          </Form.Group>

          <Form.Group controlId="discountRate">
            <Form.Label>Discount rate</Form.Label>
            <Form.Control 
              type="number" 
              placeholder="Enter discount rate" 
              value={discountRate} 
              onChange={(e) => setDiscountRate(+e.target.value)} 
            />
          </Form.Group>
          
          <Form.Group controlId="minNumberOfPoints">
            <Form.Label>Min number of points</Form.Label>
            <Form.Control 
              type="number" 
              placeholder="Enter min number of points" 
              value={minNumberOfPoints} 
              onChange={(e) => setMinNumberOfPoints(+e.target.value)} 
            />
          </Form.Group>

          <Form.Group controlId="newPoints">
            <Form.Label>New points on each purchase</Form.Label>
            <Form.Control 
              type="number" 
              placeholder="Enter points" 
              value={newPoints} 
              onChange={(e) => setNewPoints(+e.target.value)} 
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit} disabled={!name || !discountRate || !minNumberOfPoints || !newPoints}>
          Save
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default LoyaltyProgramModal;
