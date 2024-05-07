"use client";
import Link from "@/node_modules/next/link";
import { Button, buttonVariants } from "@/components/ui/button"
import { signIn, signOut, useSession } from "next-auth/react";
import React from "react";

const AppBar = () => {
  const { data: session } = useSession();

  return (
    <div className="flex mx-auto justify-between items-center">
      <div className="flex ml-5 gap-4">
        <Link href="/" className={buttonVariants({ variant: "outline" })}>Home</Link>
        {session?.user.value.token ? (
          <>
            <Link href="/products" className={buttonVariants({ variant: "outline" })}>Productos</Link>
            <Link href="/users/me" className={buttonVariants({ variant: "outline" })}>Perfil</Link>
            <Link href="/stats" className={buttonVariants({ variant: "outline" })}>Estadísticas</Link>
          </>
        ) : (<></>)
        }
      </div>
      <div className="flex mr-5 gap-4">
        {session?.user.value.token ? (
          <>
            <p className="text-sky-600"> {session.user.name}</p>
            <Button className="secondary" onClick={() => signOut()}>
              Sign Out
            </Button>
          </>
        ) : (
          <>
            <Link href="register" className={buttonVariants({ variant: "outline" })}>Register</Link>
            <Button onClick={() => signIn()}>
              Sign In
            </Button>
          </>
        )}
      </div>
    </div>
  );
};

export default AppBar;