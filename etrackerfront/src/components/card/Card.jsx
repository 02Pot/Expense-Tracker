import './Card.css';

function Card(){

    return(
        <div className="card">
            <img className="card-image" alt="ProfilePicture"/>
            <h2 className='card-title'>ReBook</h2>
            <p className='card-text'>Todo List</p>
            <p className='card-text'>Make a Commercial</p>
        </div>
    );
}
export default Card