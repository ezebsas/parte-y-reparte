import { useSession } from 'next-auth/react';
import React, { useEffect, useState } from 'react'

// TODO get path from env
const path = "http://localhost:8080"

type Response = {
  message : string,
  value : any
}

export const useGetData = ({ resource } : { resource : string}) => {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<boolean>(false);
  const [data, setData] = useState<Response>();
  const [authError, setAuthError] = useState<boolean>(false);
  const { data: session, status, update } = useSession();

  useEffect(() => {

    const fetchData = async () => {

      if (status == "loading") {
        return;
      }

      let sessionUser : JWTSessionUser = session?.user;
      let token;
      
      try {
        token = sessionUser!.value?.token;
      } catch(err) {
        setAuthError(true);
        return;
      }

      if (!token) {
        setAuthError(true);
        return;
      }

        try {
          const response = await fetch(path + resource, {
            method: "GET",
            mode: "cors",
            headers: {
              Accept: "application/json",
              Authorization: "Bearer " + token,
            },
          });
  
          if (response.status == 200) {
            const json = await response.json();
            setData(json);
          } else {
            setError(true);
          }
  
          setIsLoading(false);
        } catch(error) {
          setError(true);
        }
        finally {
          setIsLoading(false);
        }


    }
    fetchData();
  }, [session, isLoading, status, resource]);

  return {
    isLoading,
    error,
    authError,
    data
  }
  
}
