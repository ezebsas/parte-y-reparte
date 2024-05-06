import React from "react";


function ProductDetails({ product }) {
    if (!product) {
      return <div>Loading...</div>; // O cualquier otro indicador de carga o mensaje apropiado
    }
  
    return (
        <div className="product-info-container">
            <div className="product-image-container">
                <img src={product.image} alt={product.name} />
            </div>
            <div className="product-details-container">
                <h2>{product.name}</h2>
                <p>ID: {product.id}</p>
                <p>Total Cost: ${product.totalCost}</p>
                <p>State: {product.state}</p>
                <p>Maximum People: {product.maxPeople}</p>
                <p>Minimum People: {product.minPeople}</p>
                <p>Link: {product.link}</p>
                <p>Deadline: {product.deadline ? product.deadline : 'Not specified'}</p>
                <p>Subscribers: {product.subscribers.length}</p>
                <p>Owner: {product.owner ? product.owner.name : 'Not specified'}</p>
            </div>
        </div>
    );
  }

export default ProductDetails;
