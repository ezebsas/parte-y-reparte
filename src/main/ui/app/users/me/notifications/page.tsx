"use client";
import NotificationsList from "./notifications-list";
import { parteYRepartePaths } from "@/utils/paths";
import SWR from "swr"
import { getDataFetcher } from "@/utils/fetchers";

export default function Home() {

  const { data: notificationsResponse, error, isLoading } = SWR("my-notifications",() => getDataFetcher(parteYRepartePaths.me.notifications));

  /*if (authError) {
    redirect("/api/auth/signin");
  }*/

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


