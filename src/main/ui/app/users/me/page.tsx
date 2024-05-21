"use client";

import { useGetData } from "@/app/custom hooks/useGetData";
import Link from "@/node_modules/next/link";
import { Button ,buttonVariants} from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Toaster } from "@/components/ui/toaster";
import { useToast } from "@/components/ui/use-toast";
import { Terminal } from "lucide-react";
import { useSession } from "next-auth/react";
import { redirect } from "next/navigation";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";

const RESOURCE = "/api/v1/users/me";

type UserData = {
  id: number;
  name: string;
  age: number;
  email: string;
};

export default function Home() {
  const { authError, data, error, isLoading } = useGetData({
    resource: RESOURCE,
  });
  const [userData, setUserData] = useState<UserData>({
    id: 0,
    name: "",
    age: 0,
    email: "",
  });
  const { data: session } = useSession();
  const { toast } = useToast()

  const sessionToken = session?.user?.value?.token;

  useEffect(() => {
    if (!data?.value) {
      return;
    }

    setUserData(data.value);
  }, [data?.value]);

  const nameHandler = (e: ChangeEvent<HTMLInputElement>) => {
    const formName = e.target.value;
    setUserData({ ...userData, name: formName });
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const res = await fetch("http://localhost:8080/api/v1/users/me", {
      method: "PATCH",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionToken}`,
      },
      body: JSON.stringify({ name: userData.name }),
    });

    if (res.status == 200) {
      toast({
        title: "Name changed!",
        description: `You change your name successfully to ${userData.name}` ,
      })
    }
  };

  if (authError) {
    redirect("/api/auth/signin");
  }

  return (
    <section className="flex flex-col justify-center items-center">
      <div className="m-2">
        <Link href="/users/me/notifications" className={buttonVariants({ variant: "outline" })}>Notificaciones</Link>
        <Link href="/users/me/products" className={buttonVariants({ variant: "outline" })}>Productos</Link>
        <Link href="/users/me/subscriptions" className={buttonVariants({ variant: "outline" })}>Suscripciones</Link>
      </div>
      {error ? (
        "Try again later"
      ) : isLoading ? (
        "Loading ..."
      ) : (
        <div>
          <form onSubmit={handleSubmit}>
            <Card className="w-[350px]">
              <CardHeader>
                <CardTitle>Profile</CardTitle>
                <CardDescription>Here you can change your name</CardDescription>
              </CardHeader>
              <CardContent>
                <div className="grid w-full items-center gap-4">
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="name">Name</Label>
                    <Input
                      id="name"
                      onChange={nameHandler}
                      value={userData.name}
                    />
                  </div>
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="name">Age</Label>
                    <Input
                      disabled
                      id="name"
                      type="number"
                      placeholder="99"
                      value={userData.age}
                    />
                  </div>
                  <div className="flex flex-col space-y-1.5">
                    <Label htmlFor="name">Email</Label>
                    <Input
                      disabled
                      id="name"
                      placeholder="example@example.com"
                      value={userData.email}
                    />
                  </div>
                </div>
              </CardContent>
              <CardFooter className="flex justify-center ">
                <Button type="submit">Save</Button>
              </CardFooter>
            </Card>
          </form>
          <Toaster />
        </div>
      )}
    </section>
  );
}
