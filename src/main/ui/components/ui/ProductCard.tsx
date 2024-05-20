import { ProductState } from "@/app/enums/ProductState";
import { IProduct } from "@/app/interfaces/IProduct";
import Image from "next/image";
import React, { MouseEventHandler } from "react";

interface productCardI {
  product: IProduct;
  handleClic: MouseEventHandler<HTMLDivElement>;
}

const calculateDaysDifference = (dateString: string): string => {
  // Convert the input string to a Date object
  const inputDate = new Date(dateString);

  // Ensure the input string is a valid date
  if (isNaN(inputDate.getTime())) {
    throw new Error("Invalid date string");
  }

  // Get the current date
  const currentDate = new Date();

  // Calculate the difference in time (milliseconds)
  const timeDifference = inputDate.getTime() - currentDate.getTime();

  // Convert the difference to days, hours, and minutes
  const daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
  const hoursDifference = Math.floor(
    (timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
  );
  const minutesDifference = Math.floor(
    (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
  );

  // Return the appropriate string based on the difference
  if (daysDifference > 0) {
    return `${daysDifference} day(s)`;
  } else if (hoursDifference > 0) {
    return `${hoursDifference} hour(s)`;
  } else {
    return `${minutesDifference} min(s)`;
  }
};

export const ProductCard = ({ product, handleClic }: productCardI) => {

  const statusText = (state : ProductState) : string => {

    let text = "";

    switch(state) {
      case "OPEN":
        text = "🟢 Active";
        break;
      case "COMPLETED":
        text = "🔵 Completed";
        break;
      case "INCOMPLETE":
        text = "🔴 Incomplete";
        break;
      case "CANNOT_BE_DISTRIBUTED":
        text = "⚫ Cannot be distributed";
        break;
      default:
        text = "🟠 Planning";
        break
    }

    return text;
  }

  const bodyText = `Amount : ${product.quantity} ${product.unit.toLowerCase()}\nMin subs: ${product.minPeople}\nMax subs: ${product.maxPeople}`;

  return (
    <div
      className="relative flex flex-col items-center w-full h-full"
      onClick={handleClic}
    >
      <span className="absolute top-2 right-2 bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
        {
          statusText(product.state)
        }
      </span>
      <Image
        className="rounded-lg object-cover min-h-64 max-h-64"
        width={300}
        height={300}
        src={product.image}
        alt={`image of product ${product.name}`}
      />

      <div className="py-4 text-center">
        <div className="font-bold text-xl">{product.name}</div>
        <p
          className="text-gray-700 text-base text-left"
          style={{ whiteSpace: "pre-wrap" }}
        >
          {bodyText}
        </p>
      </div>
      <div className="flex flex-row flex-wrap gap-2 py-4 ">
        <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 ">
          Timeleft: {calculateDaysDifference(product.deadline)}
        </span>

        <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700">
          {`Subs: ${product.subscribers.length.toString()}`}
        </span>
      </div>
    </div>
  );
};
