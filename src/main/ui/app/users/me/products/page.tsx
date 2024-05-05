"use client";
import { OwnerDataTable } from "./owner-data-table";
import { Product, ownercolumns } from "./owner-colums";
import { useEffect, useState } from "react";

//TODO Get path from .env
const path = "http://localhost:8080"
const resource = "/api/v1/users/me/products"

type Response = {
  message : string,
  value : Array<Product>
}

export default function Home() {
  const [isLoading, setIsLoading] = useState(true);
  const [products, setProducts] = useState(new Array<Product>());

  //TODO Get token from somewhere (like cookie)
  const token = "";


  const receiveData = (data : Response) => {
    return data.value;
  }

  useEffect(() => {

    const fetchData = async () => {
      try {
        const response = await fetch(path + resource, {
          method: "GET",
          mode: "cors",
          headers: {
            Accept: "application/json",
            Authorization: "Bearer " + token,
          },
        });

        if (response.status == 200) {
          const json = await response.json();
          const dataList = receiveData(json);
          setProducts(dataList);
        }

        setIsLoading(false);

      } catch {
        console.log("Please try again later");
        setIsLoading(false);
      }
    }
    fetchData();
  }, []);

  return (
    <section className="flex flex-col items-center	gap-x-8 gap-y-4">
      <h1 className="text-2xl">My products</h1>
      {isLoading ? (
        "Loading.."
      ) : (
        <OwnerDataTable columns={ownercolumns} data={products} />
      )}
    </section>
  );
}
