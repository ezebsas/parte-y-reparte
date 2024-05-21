"use client";

import { Button } from "@/components/ui/button";
import { ProductDataGrid } from "./product-data-grid";
import { productColumn } from "./product-column";
import { useGetData } from "../../custom hooks/useGetData";
import { redirect } from "next/navigation";

const RESOURCE = "/api/v1/products";

export default function Home() {
  const {
    authError,
    error,
    data: productResponse,
    isLoading,
  } = useGetData({ resource: RESOURCE });

  const handleRedirect = () => {
    window.location.href = `/products/new`;
  };

  if (authError) {
    redirect("/api/auth/signin");
  }

  return (
    <div className="flex flex-col content-center text-center my-2">
      <h1 className="text-2xl"> Products </h1>
      {isLoading ? (
        "Loading"
      ) : error ? (
        "Error. Please try again later"
      ) : (
        <>
          <ProductDataGrid
            columns={productColumn}
            data={productResponse?.value}
          />
          <Button
            variant="outline"
            className={"bg-blue-500 text-white"}
            onClick={handleRedirect}
          >
            Add Product
          </Button>
        </>
      )}
    </div>
  );
}
