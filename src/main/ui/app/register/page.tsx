"use client";

import { Button } from "@/components/ui/button"
import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

const RegisterPage = () => {
  const [errors, setErrors] = useState<string[]>([]);
  const [name, setName] = useState<string>();
  const [age, setAge] = useState<number>();
  const [email, setEmail] = useState<string>();
  const [password, setPassword] = useState<string>();
  const router = useRouter();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrors([]);

    const res = await fetch("http://localhost:8080/api/v1/auth/register",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name,
          age,
          email,
          password,
        }),
      }
    );

    const responseAPI = await res.json();

    if (!res.ok) {
      setErrors(responseAPI.message);
      return;
    }

    const responseNextAuth = await signIn("credentials", {
      email,
      password,
      redirect: false,
    });

    if (responseNextAuth?.error) {
      setErrors(responseNextAuth.error.split(","));
      return;
    }

    router.push("/");
  };

  return (
    <div className="flex justify-center">
      <form onSubmit={handleSubmit}>
        <Card className="w-full max-w-sm">
          <CardHeader>
            <CardTitle className="text-2xl">Register</CardTitle>
            <CardDescription>
              Ingrese todos los campos para continuar con la registración
            </CardDescription>
          </CardHeader>
          <CardContent className="grid gap-4">
            <div className="grid gap-2">
              <Label htmlFor="name">Nombre</Label>
              <Input id="name" type="text" placeholder="Juan" required 
                value={name}
                onChange={(event) => setName(event.target.value)} 
              />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="age">Edad</Label>
              <Input id="age" type="number" placeholder="20" required 
                value={age}
                onChange={(event) => setAge(Number(event.target.value))} 
              />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="email">Email</Label>
              <Input id="email" type="email" placeholder="m@example.com" required 
                value={email}
                onChange={(event) => setEmail(event.target.value)} 
              />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="password">Password</Label>
              <Input id="password" type="password" required 
                value={password}
                onChange={(event) => setPassword(event.target.value)} 
              />
            </div>
          </CardContent>
          <CardFooter>
            <Button className="w-full" type="submit">Register</Button>
          </CardFooter>
        </Card>
      </form>

    </div>
  );
};
export default RegisterPage;
