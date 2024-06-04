"use client";
import { OwnerDataTable } from "../products/owner-data-table";
import { ownercolumns } from "../products/owner-colums";
import SWR from "swr"
import { getDataFetcher } from "@/utils/fetchers";
import { parteYRepartePaths } from "@/utils/paths";

export default function Home() {

  const {data : productResponse, error, isLoading} = SWR("user-suscription", () => getDataFetcher(parteYRepartePaths.me.suscriptions.base))
  
 /* if (authError) {
    redirect("/api/auth/signin");
  }*/
  return (
    <section className="flex flex-col items-center	gap-x-8 gap-y-4">
      <h1 className="text-2xl">My subscriptions</h1>
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
