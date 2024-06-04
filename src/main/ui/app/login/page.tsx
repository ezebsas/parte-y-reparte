"use client";

import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { AlertCircle } from "lucide-react";

import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useState } from "react";

const LoginPage = () => {
  const [error, setError] = useState<number>();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const responseNextAuth = await signIn("credentials", {
      email,
      password,
      redirect: false,
    });

    console.log(responseNextAuth);
    if (responseNextAuth?.error) {
      setError(responseNextAuth.status);
      return;
    }

    router.push("/");
  };

  return (
    <div className="flex items-center flex-col ">
      <form onSubmit={handleSubmit}>
      <Card className="w-full max-w-sm">
        <CardHeader>
          <CardTitle className="text-2xl">Login</CardTitle>
          <CardDescription>
            Ingrese su mail abajo para continuar con el login
          </CardDescription>
        </CardHeader>
        <CardContent className="grid gap-4">
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
          <Button className="w-full" type="submit">Log in</Button>
        </CardFooter>
      </Card>
      </form>

      {error == 401 && (
        <div className="alert alert-danger mt-2">
          <Alert variant="destructive">
              <AlertCircle className="h-4 w-4" />
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>
                Wrong username or password.
              </AlertDescription>
            </Alert>
        </div>
      )}

    </div>
  );
};
export default LoginPage;
