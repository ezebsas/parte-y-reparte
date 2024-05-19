"use client";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import { fetchData } from "next-auth/client/_utils";
import { OwnerDataTable } from "../users/me/products/owner-data-table";
import { Product, ownercolumns } from "../users/me/products/owner-colums";
import { Button } from "@/components/ui/button";
import { Link } from "lucide-react";



function useFetchProducts(sessionToken : string){
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
  const sessionToken = session?.user.value?.token!;
  const products = useFetchProducts(sessionToken);

  const handleRedirect = () => {
    window.location.href = `/products/new`; 
};

  return (
    <div>
      <OwnerDataTable columns={ownercolumns} data={products} />
        <Button variant="outline" className={'bg-blue-500 text-white'} onClick={handleRedirect} >
          Add Product
        </Button>
    </div>
  );
}