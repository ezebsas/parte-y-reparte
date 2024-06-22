import { getSession } from "next-auth/react";

const ENDPOINT = `http://${process.env.NEXT_PUBLIC_BACKEND_INTERNAL_URL}:8080/api/v1`

export const getDataFetcher = async (RESOURCE : string) => {

  const token = await getSession().then(s => s?.user.value?.token)

  const response = await fetch(ENDPOINT + RESOURCE, {
    method: "GET",
    mode: "cors",
    headers: {
      Accept: "application/json",
      Authorization: "Bearer " + token,
    },
  });

  errorCodes(response.status);

  const data = await response.json();

  return data;
}

export const postDataFetch = async(RESOURCE: string, data: Object) => {

  const token = await getSession().then(s => s?.user.value?.token)

  const res = await fetch(ENDPOINT + RESOURCE, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  //errorCodes(res.status)

  return res;
}

export const deleteDataFetch = async (RESOURCE: string) => {

  const token = await getSession().then(s => s?.user.value?.token)

  const res = await fetch(
    ENDPOINT + RESOURCE,
    {
      method: "DELETE",
      mode: "cors",
      headers: {
        Accept: "application/json",
        Authorization: `Bearer ${token}`,
      },
    }
  );

  //errorCodes(res.status)

  return res;
}

export const putDataFetch = async (RESOURCE: string) => {

  const token = await getSession().then(s => s?.user.value?.token)

  const res = await fetch(
        ENDPOINT + RESOURCE,
        {
          method: "PUT",
          mode: "cors",
          headers: {
            Accept: "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
  //errorCodes(res.status)

  return res;
}

export const patchDataFetch = async (RESOURCE: string, data: Object) => {

  const token = await getSession().then(s => s?.user.value?.token)

  const res = await fetch(ENDPOINT + RESOURCE, {
      method: "PATCH",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(data),
    });

  return res;
}


const errorCodes = (code : number) => {
  
  if (code >= 500) {
    throw new Error("Server Error. Please try again later");
  }

  if (code == 404) {
    throw new Error(`Error 404: NOT FOUND`)
  }

  if (code == 400) {
    throw new Error(`Error 400: Bad Request`)
  }

  if (code == 403) {
    // TODO
    throw new Error(`Error 403: Forbidden. Auth failed`)
  }
}
