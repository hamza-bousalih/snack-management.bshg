import {CButton, CSpinner} from "@coreui/react";

const SpinnerButton = ({ when, color, icon, label, onClick } = { when: false, color: 'primary' }) => {
  return <>
    <CButton onClick={onClick} color={color}>
      {when ? <CSpinner size="sm"/> : icon}
      {label}
    </CButton>
  </>
}

export default SpinnerButton
