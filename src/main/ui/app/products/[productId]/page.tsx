"use client";
import { useSession } from "next-auth/react";
import { fetchData } from "next-auth/client/_utils";
import { useEffect, useState } from "react";
import ProductDetails from "./product";
import { IProduct } from "@/app/interfaces/IProduct";



function useFetchProduct(sessionToken : string){
  const [product, setProduct] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (sessionToken) {
          const productId = window.location.pathname.split("/")[2];
          const res = await fetch("http://localhost:8080/api/v1/products/" + productId, {
            method: "GET",
            mode: "cors",
            headers: {
              Accept: "application/json",
              Authorization: `Bearer ${sessionToken}`,
            },
          });
          const data = await res.json();
          setProduct(data.value);
        }
      } catch (error) {
        console.log('Error fetching products:');
      }
    };
    fetchData();
  }, [sessionToken]); 
  return product;
}



export default function Home() {
  const { data: session } = useSession();
  const sessionToken = session?.user.value?.token!;
  const product : IProduct = useFetchProduct(sessionToken)!;

  return (
    <div>
      <ProductDetails product={product}></ProductDetails>
    </div>
  );
}