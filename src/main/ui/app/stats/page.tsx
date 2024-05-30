"use client";
import { useSession } from "next-auth/react";
import { useState, useEffect } from "react";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
import { Response } from "../../interfaces/IResponse";
import SWR from "swr"
import { getDataFetcher } from "@/utils/fetchers";
import { parteYRepartePaths } from "@/utils/paths";

export default function Home() {
  const { data: session } = useSession();

  const { data : uniqueUsers, isLoading : uniqueUsersIsLoading, error : uniqueUsersError} = SWR("unique-users",() => getDataFetcher(parteYRepartePaths.stats.uniqueUsers));
  const { data : amountPublications, isLoading: amountPubliIsLoading, error: amountPubliError } = SWR("publications-amount",() => getDataFetcher(parteYRepartePaths.stats.publicationAmount));

  return (
    <div className="flex justify-center m-5">
      {uniqueUsersIsLoading || amountPubliIsLoading ? (
        "Loading"
      ) : uniqueUsersError || amountPubliError ? (
        uniqueUsersError.message
      ) : (
      <Card className="min-w-96">
        <CardHeader>
          <CardTitle>Estadísticas</CardTitle>
          <CardDescription>Estadisticas de usuarios y productos</CardDescription>
        </CardHeader>
        <CardContent>
          <p>Cantidad de usuarios únicos que interactuaron: {uniqueUsers && uniqueUsers.value}</p>
        </CardContent>
        <CardFooter>
          <p>Cantidad total de publicaciones: {amountPublications && amountPublications.value}</p>
        </CardFooter>
      </Card>)
      }
    </div>
  );
}
