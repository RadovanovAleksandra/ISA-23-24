import React, { useEffect, useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

interface ModalProps {
  show: boolean;
  mode: number;
  program: any;
  handleClose: () => void;
  handleModalSubmit: (name: string, discountRate: number, minNumberOfPoints: number, onReservation: number, onCancel: number) => void;
}

const LoyaltyProgramModal: React.FC<ModalProps> = ({ show, mode, program, handleClose, handleModalSubmit }) => {
  const [name, setName] = useState('');
  const [discountRate, setDiscountRate] = useState(0);
  const [minNumberOfPoints, setMinNumberOfPoints] = useState(0);
  const [onReservation, setOnReservation] = useState(0);
  const [onCancel, setOnCancel] = useState(0);

  useEffect(() => {
    if (program){
        setName(program.name)
        setDiscountRate(program.discountRate)
        setMinNumberOfPoints(program.minNumberOfPoints)
        setOnReservation(program.onReservation)
        setOnCancel(program.onCancel)
    } else {
        setName('')
        setDiscountRate(0)
        setMinNumberOfPoints(0)
        setOnReservation(0)
        setOnCancel(0)
    }
  }, [program])

  const handleSubmit = () => {
    handleModalSubmit(name, discountRate, minNumberOfPoints, onReservation, onCancel);
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

          <Form.Group controlId="onReservation">
            <Form.Label>New points on each reservation</Form.Label>
            <Form.Control 
              type="number" 
              placeholder="Enter points" 
              value={onReservation} 
              onChange={(e) => setOnReservation(+e.target.value)} 
            />
          </Form.Group>

          <Form.Group controlId="oncancel">
            <Form.Label>Points to subtract on cancellation</Form.Label>
            <Form.Control 
              type="number" 
              placeholder="Enter points" 
              value={onCancel} 
              onChange={(e) => setOnCancel(+e.target.value)} 
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleSubmit} disabled={!name || !discountRate || !minNumberOfPoints || !onReservation}>
          Save
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default LoyaltyProgramModal;
