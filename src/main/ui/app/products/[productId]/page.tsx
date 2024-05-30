"use client";
import { useSession } from "next-auth/react";
import { fetchData } from "next-auth/client/_utils";
import { useEffect, useState } from "react";
import ProductDetails from "./product";
import { IProduct } from "@/interfaces/IProduct";
import SWR from "swr";
import { useParams } from "next/navigation";
import { parteYRepartePaths } from "@/utils/paths";
import { getDataFetcher } from "@/utils/fetchers";

export default function Home() {
  const params = useParams<{ productId: string }>();

  const {
    data: product,
    error,
    isLoading,
  } = SWR(
    `product-id-${params?.productId}`,
    () => getDataFetcher(parteYRepartePaths.products.details(params?.productId!))
  );

  return (
    <div>
      {isLoading ? (
        "Loading"
      ) : error ? (
        error.message
      ) : (
        <ProductDetails product={product.value}></ProductDetails>
      )}
    </div>
  );
}
