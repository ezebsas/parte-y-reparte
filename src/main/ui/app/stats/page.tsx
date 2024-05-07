"use client";
import { useSession } from "next-auth/react";
import { useState, useEffect } from "react";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"

export default function Home() {
  const { data: session } = useSession();
  const [uniqueUsers, setUniqueUsers]= useState();
  const [amountPublications, setAmountPublications]= useState();

  const fetchUniqueUsers = async () => {
    const res = await fetch("http://localhost:8080/api/v1/stats/unique-users", {
      method: "Get",
      headers: {
        authorization: `Bearer ${session?.user.value.token}`,
      },
    });

    const response = await res.json();
    setUniqueUsers(response);
  };
  const fetchAmountPublications = async () => {
    const res = await fetch("http://localhost:8080/api/v1/stats/publications", {
      method: "Get",
      headers: {
        authorization: `Bearer ${session?.user.value.token}`,
      },
    });

    const response = await res.json();
    setAmountPublications(response);
  };
  useEffect(()=> {
    fetchUniqueUsers();
    fetchAmountPublications();
  }, [])
  return (
    <div className="flex justify-center m-5">
      <Card className="min-w-96">
        <CardHeader>
          <CardTitle>Estadísticas</CardTitle>
          <CardDescription>Estadisticas de usuarios y productos</CardDescription>
        </CardHeader>
        <CardContent>
          <p>Cantidad de usuarios únicos: {uniqueUsers && uniqueUsers.value}</p>
        </CardContent>
        <CardFooter>
          <p>Cantidad de publicaciones: {amountPublications && amountPublications.value}</p>
        </CardFooter>
      </Card>
    </div>
  );
}
