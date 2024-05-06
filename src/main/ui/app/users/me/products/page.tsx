"use client";
import { OwnerDataTable } from "./owner-data-table";
import { ownercolumns } from "./owner-colums";
import { redirect } from "next/navigation";
import { useGetData } from "../../../custom hooks/useGetData";

//TODO maybe get path from .env
const resource = "/api/v1/users/me/products";

export default function Home() {
  const {
    authError,
    error,
    data: productResponse,
    isLoading,
  } = useGetData({ resource: resource });

  if (authError) {
    redirect("/api/auth/signin");
  }

  return (
    <section className="flex flex-col items-center	gap-x-8 gap-y-4">
      <h1 className="text-2xl">My products</h1>
      {isLoading ? (
        "Loading..."
      ) : error ? (
        "Error, please try again later."
      ) : (
        <OwnerDataTable columns={ownercolumns} data={productResponse?.value} />
      )}
    </section>
  );
}
