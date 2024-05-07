import Image from 'next/image'
import React from 'react'

export const ProductCard = ({title, body, timeleft, imageLink}) => {
  return (
    <div className="max-w-sm rounded overflow-hidden shadow-lg">
    <Image
      width={20}
      height={20}
      className="w-full"
      
      src={imageLink}
      alt={`image of product ${title}`}
    />
    <div className="px-6 py-4">
      <div className="font-bold text-xl mb-2">{title}</div>
      <p className="text-gray-700 text-base">
        {body}
      </p>
    </div>
    <div className="px-6 pt-4 pb-2">
      <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
        {timeleft}
      </span>
      <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">
        Active 🟢
      </span>
    </div>
  </div>
  )
}
