"use client";

import { Button } from "@/components/ui/button";
import { ProductDataGrid } from "./product-data-grid";
import { productColumn } from "./product-column";
import SWR from "swr";
import { getDataFetcher } from "@/utils/fetchers";
import Link from "next/link";
import { parteYRepartePaths } from "@/utils/paths";

export default function Home() {
  const {
    data: productData,
    error,
    isLoading,
  } = SWR("getAllProducts", () =>
    getDataFetcher(parteYRepartePaths.products.base)
  );

  return (
    <div className="flex flex-col content-center text-center my-2">
      <h1 className="text-2xl"> Products </h1>
      {isLoading ? (
        "Loading"
      ) : error ? (
        error.message
      ) : (
        <>
          <ProductDataGrid columns={productColumn} data={productData.value} />
          <Button
            asChild
            variant="outline"
            className={"bg-blue-500 text-white"}
          >
            <Link href={"/products/new"}>Add Product</Link>
          </Button>
        </>
      )}
    </div>
  );
}
