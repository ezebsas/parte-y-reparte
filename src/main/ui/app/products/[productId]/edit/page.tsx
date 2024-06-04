"use client";
import ProductDetails from "./product";
import { useParams } from "next/navigation";
import { getDataFetcher } from "@/utils/fetchers";
import SWR from "swr"
import { parteYRepartePaths } from "@/utils/paths";

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
    <>
      {isLoading ? (
        "Loading"
      ) : error ? (
        error.message
      ) : (
        <ProductDetails product={product.value}></ProductDetails>
      )}
    </>
  );
}
