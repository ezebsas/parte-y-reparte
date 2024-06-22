"use client";

import { Button } from "@/components/ui/button"
import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useState, useEffect, ChangeEvent } from "react";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle, } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { postDataFetch } from "@/utils/fetchers";
import { parteYRepartePaths } from "@/utils/paths";
import { IApiResponse } from "@/interfaces/parte-y-reparte-interfaces";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import { AlertCircle } from "lucide-react";

const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,}$/;

const RegisterPage = () => {
  const [errors, setErrors] = useState<string>();
  const [name, setName] = useState<string>();
  const [age, setAge] = useState<number>();
  const [email, setEmail] = useState<string>();
  const [password, setPassword] = useState<string>("");
  const [validationMessage, setValidationMessage] = useState('');
  const [validPassword, setValidPassword] = useState(false);
  const router = useRouter();

  useEffect(() => {

    const validatePassword = (password : string) => {
      return regex.test(password);
    };

    if (password.length === 0) {
      setValidationMessage('');
      setValidPassword(false);
    } else if (!validatePassword(password)) {
      setValidationMessage('Password must be at least 5 characters long, contain at least one uppercase letter and one number.');
      setValidPassword(false);
    } else {
      setValidationMessage('');
      setValidPassword(true);
    }
  }, [password]);

  const handleChange = (e : ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const res = await postDataFetch(parteYRepartePaths.register,{
      name,
      age,
      email,
      password,
    })

    const responseAPI : IApiResponse<any> = await res.json();

    if (!res.ok) {
      setErrors(responseAPI.value.error);
      return;
    }

    const responseNextAuth = await signIn("credentials", {
      email,
      password,
      redirect: false,
    });

    if (responseNextAuth?.error) {
      setErrors(responseNextAuth.error);
      return;
    }

    router.push("/");
  };

  return (
    <div className="flex items-center flex-col">
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
                onChange={handleChange} 
              />
              <p className="text-red-300">{validationMessage}</p>
            </div>
          </CardContent>
          <CardFooter>
            {
              validPassword? (<Button className="w-full" type="submit">Register</Button>)
              : (<Button className="w-full" type="submit" disabled>Register</Button>)
            }
            
          </CardFooter>
        </Card>
      </form>
      {errors?.length! > 0 && (
        <div className="alert alert-danger mt-2">
          <Alert variant="destructive">
              <AlertCircle className="h-4 w-4" />
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>
                {errors}
              </AlertDescription>
            </Alert>
        </div>
      )}
    </div>
  );
};
export default RegisterPage;
