"use client";
import Link from "@/node_modules/next/link";
import { buttonVariants } from "@/components/ui/button"
import { signIn, signOut, useSession } from "next-auth/react";
import React from "react";

const AppBar = () => {
  const { data: session } = useSession();
  console.log({ session });

  return (
    <div className="p-2 flex gap-5 ">
      <div className="ml-auto flex gap-2">
        <Link href="/" className={buttonVariants({ variant: "outline" })}>Home</Link>
        <Link href="register" className={buttonVariants({ variant: "outline" })}>Register</Link>
        {session?.user ? (
          <>
            <p className="text-sky-600"> {session.user.name}</p>
            <button className="text-red-500" onClick={() => signOut()}>
              Sign Out
            </button>
          </>
        ) : (
          <button className="text-green-600" onClick={() => signIn()}>
            Sign In
          </button>
        )}
      </div>
    </div>
  );
};

export default AppBar;