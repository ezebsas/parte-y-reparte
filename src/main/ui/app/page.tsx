"use client";
import { Button, buttonVariants } from "@/components/ui/button"
import Link from 'next/link';
import { useSession } from "next-auth/react";
import { useState } from "react";


export default function Home() {
  const { data: session } = useSession();
  const [me, setMe] = useState();
  const fetchMe = async () => {
    console.log("ola");
    console.log(session)
    const res = await fetch("http://localhost:8080/api/v1/users/me", {
      method: "Get",
      headers: {
        authorization: `Bearer ${session?.user.value.token}`,
      },
    });

    const response = await res.json();
    setMe(response);
  };
  return (
  <div>
    <p>Landing page</p>
    <Link href="products" className={buttonVariants({ variant: "outline" })}>Productos</Link>
    <Button onClick={fetchMe}>Get Me</Button>
    {me && JSON.stringify(me)}
  </div>
  );

}

//npx shadcn-ui@latest add button
