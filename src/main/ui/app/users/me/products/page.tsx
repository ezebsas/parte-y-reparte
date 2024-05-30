"use client";
import { OwnerDataTable } from "./owner-data-table";
import { ownercolumns } from "./owner-colums";
import { redirect } from "next/navigation";
import { useGetData } from "../../../../custom hooks/useGetData";
import SWR from "swr"
import { getDataFetcher } from "@/utils/fetchers";
import { parteYRepartePaths } from "@/utils/paths";

export default function Home() {

  const { data: productResponse, error, isLoading } = SWR("my-products",() => getDataFetcher(parteYRepartePaths.me.products));

 /* if (authError) {
    redirect("/api/auth/signin");
  }*/

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
