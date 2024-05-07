import React from 'react';
import Link from 'next/link';

const NotificationsList = ({ notifications }) => {
  return (
    <div className="max-w-2xl mx-auto mt-8 bg-white rounded-lg shadow-lg p-6">
      <ul className="divide-y divide-gray-200">
        {notifications.map((notification, index) => (
          <li key={index} className="py-4">
            <h3 className="text-xl font-semibold mb-2">{notification.title}</h3>
            <p className="text-gray-700 mb-1">{notification.message}</p>
            <p className="text-gray-500 mb-2">Fecha: {new Date(notification.date).toLocaleString()}</p>
            <Link href={`/products/${notification.productId}`}>
              <button className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-3 rounded-md shadow-md text-sm">
                Go to product
              </button>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default NotificationsList;







