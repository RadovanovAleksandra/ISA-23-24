import React from 'react';
import { Modal, Button } from 'react-bootstrap';

interface ModalProps {
  show: boolean;
  handleClose: () => void;
  handleSubmit: () => void;
}

const ConfirmationModal: React.FC<ModalProps> = ({ show, handleClose, handleSubmit }) => {

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Are you sure you want do confirm the action?</Modal.Title>
      </Modal.Header>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          No
        </Button>
        <Button variant="primary" onClick={handleSubmit} >
          Yes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ConfirmationModal;
