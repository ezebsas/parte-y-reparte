"use client";
import React, { useEffect } from "react";
import Image from "next/image";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { useSession } from "next-auth/react";
import { redirect } from "next/navigation";
import { IProduct } from "@/interfaces/IProduct";
import { jwtParser } from "@/utils/jwtParser";
import { toast, useToast } from "@/components/ui/use-toast";
import { Toaster } from "@/components/ui/toaster";

function ProductDetails({ product }: { product: IProduct }) {
  const { data: session } = useSession();
  const sessionToken = session?.user.value!.token;
  const { toast } = useToast();
  const [subscribed, setSubscribed] = useState(false);

  useEffect(() => {
    if (!product) {
      return;
    }

    const suscribeManage = async () => {
      const userID = jwtParser(sessionToken!).sub;

      if (product.subscribers.some((s) => s.id == userID)) {
        setSubscribed(true);
      }
    };

    suscribeManage();
  }, [sessionToken, product]);

  const handleSubscribe = async () => {
    try {

      toast({
        title: "Suscribing",
        description: "Please wait....",
      })
      const productId = product.id;
      const res = await fetch(
        "http://localhost:8080/api/v1/products/" + productId + "/subscription",
        {
          method: "POST",
          mode: "cors",
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${sessionToken}`,
          },
        }
      );

      if (res.ok) {
        setSubscribed(true);
        toast({
          title: "Suscribed",
          description: "Congrats!",
        })
      } else {
        toast({
          title: "Uh oh! Something went wrong.",
          description: "Error subscribing. Please reload the page",
        })
        console.log("cheems")
        throw new Error("Error subscribing:" + res.statusText);
      }
    } catch (error) {
      let message = "Unknown Error";

      if (error instanceof Error) {
        message = error.message;
      }

      console.error("Error subscribing:", message);
      // Handle errors here
    }
  };

  const handleUnsubscribe = async () => {
    try {
      toast({
        title: "Unsubscribing",
        description: "Please wait...",
      })
      const productId = product.id;
      const res = await fetch(
        "http://localhost:8080/api/v1/users/me/subscriptions/" + productId,
        {
          method: "DELETE",
          mode: "cors",
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${sessionToken}`,
          },
        }
      );

      if (res.ok) {
        setSubscribed(false);
        toast({
          title: "Unsuscribed",
          description: "Congrats!",
        })
      } else {
        throw new Error("Error desubscribing:" + res.statusText);
      }
    } catch (error) {
      let message = "Unknown Error";

      if (error instanceof Error) {
        message = error.message;
      }

      console.error("Error desubscribing:", message);

      toast({
        title: "Uh oh! Something went wrong.",
        description: "Error desubscribing. Please reload the page",
      })
      
    }
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div
      className="product-info-container"
      style={{ display: "flex", flexDirection: "column", alignItems: "center" }}
    >
      <div style={{ maxHeight: "10vw", maxWidth: "25vw", marginTop: "5rem" }}>
        {product.image ? (
          <img
            src={product.image}
            alt={product.name}
            style={{ maxWidth: "100%", height: "auto", maxHeight: "10vw" }}
          />
        ) : (
          <p>No image available</p>
        )}
      </div>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          width: "100%",
          padding: "5rem",
          boxSizing: "border-box",
        }}
      >
        <Card style={{ width: "100%" }}>
          <CardHeader style={{ alignItems: "center" }}>
            <CardTitle>{product.name}</CardTitle>
          </CardHeader>
          <CardContent
            style={{ display: "flex", flexDirection: "row", flexWrap: "wrap" }}
          >
            <div className="flex flex-col flex-1 min-w-0 mb-8 md:mb-0 md:mr-4">
              <div className="flex mb-2">
                <span className="font-bold mr-2">Total Cost:</span>
                <span>${product.totalCost}</span>
              </div>
              <div className="flex mb-2">
                <span className="font-bold mr-2">Quantity:</span>
                <span>{product.quantity}</span>
              </div>
              <div className="flex mb-2">
                <span className="font-bold mr-2">Unit:</span>
                <span>{product.unit}</span>
              </div>
              <div className="flex mb-2">
                <span className="font-bold mr-2">State:</span>
                <span>{product.state}</span>
              </div>
              <div className="flex mb-2">
                <span className="font-bold mr-2">Maximum People:</span>
                <span>{product.maxPeople}</span>
              </div>
              <div className="flex">
                <span className="font-bold mr-2">Minimum People:</span>
                <span>{product.minPeople}</span>
              </div>
            </div>
            <div className="flex flex-col flex-1 min-w-0">
              <div className="flex mb-2">
                <span className="font-bold mr-2">Link:</span>
                <span>{product.link}</span>
              </div>
              <div className="flex mb-2">
                <span className="font-bold mr-2">Deadline:</span>
                <span>
                  {product.deadline ? product.deadline : "Not specified"}
                </span>
              </div>
              <div className="flex mb-2">
                <span className="font-bold mr-2">Subscribers:</span>
                <span>{product.subscribers.length}</span>
              </div>
              <div className="flex">
                <span className="font-bold mr-2">Owner:</span>
                <span>
                  {product.owner ? product.owner.name : "Not specified"}
                </span>
              </div>
            </div>
          </CardContent>
          <CardFooter className="flex justify-end">
            {
              // CLOSED PRODUCT

              product.state != "OPEN" ? (
                // MY PRODUCT
                product.owner.id == jwtParser(sessionToken!).sub ? (
                  <Button
                    variant="outline"
                    className={"bg-green-500 text-white"}
                    onClick={() => {
                      window.location.href = `/products/${product.id}/edit`;
                    }}
                    disabled={false}
                  >
                    Edit
                  </Button>
                ) : (
                  // CLOSED PRODUCT
                  <Button
                    variant="outline"
                    className={"bg-red-500 text-white"}
                    disabled={true}
                  >
                    Closed
                  </Button>
                )
              ) : //OPENED PRODUCT
              product.owner.id == jwtParser(sessionToken!).sub ? (
                // MY PRODUCT
                <Button
                  variant="outline"
                  className={"bg-green-500 text-white"}
                  onClick={() => {
                    window.location.href = `/products/${product.id}/edit`;
                  }}
                  disabled={false}
                >
                  Edit
                </Button>
              ) : subscribed ? (
                // Suscribed
                <Button
                  variant="outline"
                  className={"bg-red-500 text-white"}
                  onClick={handleUnsubscribe}
                  disabled={false}
                >
                  Unsuscribed
                </Button>
              ) : product.maxPeople == product.subscribers.length ? (
                <Button
                  variant="outline"
                  className={"bg-amber-500 text-white"}
                  disabled={true}
                >
                  Full
                </Button>
              ) : (
                // Not suscribed
                <Button
                  variant="outline"
                  className={"bg-blue-500 text-white"}
                  onClick={handleSubscribe}
                >
                  Subscribe
                </Button>
              )
            }
          </CardFooter>
        </Card>
        <Toaster />
      </div>
    </div>
  );
}

export default ProductDetails;
