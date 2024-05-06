"use client";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import { fetchData } from "next-auth/client/_utils";
import { OwnerDataTable } from "../users/me/products/owner-data-table";
import { Product, ownercolumns } from "../users/me/products/owner-colums";



function useFetchProducts(sessionToken){
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (sessionToken) {
          const res = await fetch("http://localhost:8080/api/v1/products", {
            method: "GET",
            mode: "cors",
            headers: {
              Accept: "application/json",
              Authorization: `Bearer ${sessionToken}`,
            },
          });
          const data = await res.json();
          console.log('s');
          setProducts(data?.value || []);
        }
      } catch (error) {
        console.log('Error fetching products:');
      }
    };
    fetchData();
  }, [sessionToken]); 
  console.log(products);

  return products;
}

export default function Home() {
  const { data: session } = useSession();
  const sessionToken = session?.user.value.token;
  const products = useFetchProducts(sessionToken);

  return (
    <div>
      <OwnerDataTable columns={ownercolumns} data={products} />
    </div>
  );
}