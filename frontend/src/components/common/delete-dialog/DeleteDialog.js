import {CButton, CCloseButton, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle} from "@coreui/react";

const DeleteDialog = ({ visible, header, onClose, itemId, body, onDelete }) => {

  return <>
    <CModal visible={visible} alignment="center" backdrop="static" onClose={onClose}>
      <CModalHeader>
        <CModalTitle>{header || 'Delete Confirm'}</CModalTitle>
      </CModalHeader>
      <CModalBody>
        {body || `Are you sure you want to delete this item (No. ${itemId})?`}
      </CModalBody>
      <CModalFooter>
        <CButton onClick={onClose} color="secondary" variant="ghost">Close</CButton>
        <CButton color="danger" onClick={onDelete}>Delete</CButton>
      </CModalFooter>
    </CModal>
  </>
}

export default DeleteDialog
