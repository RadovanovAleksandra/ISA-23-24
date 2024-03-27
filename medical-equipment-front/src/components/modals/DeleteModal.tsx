import React from 'react';
import { Modal, Button } from 'react-bootstrap';

interface RateCompanyModalProps {
  show: boolean;
  handleClose: () => void;
  handleSubmit: () => void;
}

const DeleteModal: React.FC<RateCompanyModalProps> = ({ show, handleClose, handleSubmit }) => {

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Are you sure you want do delete selected item?</Modal.Title>
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

export default DeleteModal;
