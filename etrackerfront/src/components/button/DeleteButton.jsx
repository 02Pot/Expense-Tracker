import './button.css';

import { FaTrash } from "react-icons/fa";

const DeleteButton = ({ onDelete }) => {
    return(
        <button className="button" onClick={onDelete}> <FaTrash /></button>
    );
};

export default DeleteButton;