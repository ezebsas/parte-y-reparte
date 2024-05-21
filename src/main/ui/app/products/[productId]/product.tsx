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

function ProductDetails({ product }: { product: IProduct }) {
  const { data: session } = useSession();
  const sessionToken = session?.user.value!.token;

  const [subscribed, setSubscribed] = useState(false);

  const handleSubscribe = async () => {
    try {
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
      } else {
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
      } else {
        throw new Error("Error desubscribing:" + res.statusText);
      }
    } catch (error) {
      let message = "Unknown Error";

      if (error instanceof Error) {
        message = error.message;
      }

      console.error("Error desubscribing:", message);
    }
  };

  if (subscribed) {
    return <h1>Succesfully Subscribed</h1>;
  }

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
            {product.subscribers.some(
              (u) => u.id == jwtParser(sessionToken!).sub
            ) ? (
              product.state != "OPEN" ? (
                <Button
                  variant="outline"
                  className={"bg-red-500 text-white"}
                  disabled={true}
                >
                  Closed
                </Button>
              ) :
              <Button
                variant="outline"
                className={"bg-red-500 text-white"}
                onClick={handleUnsubscribe}
                disabled={false}
              >
                Unsubscribe
              </Button>
            ) : product.owner.id == jwtParser(sessionToken!).sub ? (
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
              <Button
                variant="outline"
                className={"bg-blue-500 text-white"}
                onClick={handleSubscribe}
              >
                Subscribe
              </Button>
            )}
          </CardFooter>
        </Card>
      </div>
    </div>
  );
}

export default ProductDetails;
