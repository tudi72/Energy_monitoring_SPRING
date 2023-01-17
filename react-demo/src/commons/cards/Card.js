import React from 'react';
import '../cards/Card.css';

function Card({title, imageUrl, body, handleAction, id, action, status, price,}) {
    return (
        <div className='card-container'>
            <div className='image-container'>
                <img src={imageUrl} alt=''/>
            </div>
            <div className='card-content'>
                <div className="card-title">
                    <h3>{title}</h3>
                </div>
                <div className='card-body'>
                    <p>{body}</p>
                </div>
                <div className='card-body'>
                    <p>{price}</p>
                </div>
                <div className='btn'> {/*changed from <a> </a> ----> <button> </button> */}
                    <a onClick={() => handleAction(id, status)}>
                        {action}
                    </a>
                </div>
            </div>

        </div>
    )
}

export default Card