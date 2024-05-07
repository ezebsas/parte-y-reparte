"use client";
import { redirect } from "next/navigation";
import { useSession } from "next-auth/react";
import { useGetData } from "../../../custom hooks/useGetData";
import NotificationsList from "./notifications-list";

const resource = "/api/v1/users/me/notifications";

export default function Home() {
  const { data: session } = useSession();
  const sessionToken = session?.user.value.token;

  const {
    authError,
    error,
    data: notificationsResponse,
    isLoading,
  } = useGetData({ resource: resource, token: sessionToken });

  if (authError) {
    redirect("/api/auth/signin");
  }

  return (
      <section className="flex flex-col items-center gap-x-8 gap-y-4">
        <h1 className="text-2xl">Notifications</h1>
        {isLoading ? (
          "Loading..."
        ) : error ? (
          "Error, please try again later."
        ) : notificationsResponse?.value && notificationsResponse.value.length > 0 ? (
          <NotificationsList notifications={notificationsResponse.value} />
        ) : (
          <p>You have no notifications at the moment. Check back later!</p>
        )}
      </section>
  );
}


