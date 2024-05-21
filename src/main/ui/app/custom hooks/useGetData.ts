import { useSession } from 'next-auth/react';
import React, { useEffect, useState } from 'react'
import { Response } from '../interfaces/IResponse';

// TODO get path from env
const path = `http://${process.env.BACKEND_INTERNAL_URL}:8080`

export const useGetData = ({ resource } : { resource : string}) => {
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<boolean>(false);
  const [data, setData] = useState<Response>();
  const [authError, setAuthError] = useState<boolean>(false);
  const { data: session, status } = useSession();

  useEffect(() => {

    const fetchData = async () => {

      if (status == "loading") {
        return;
      }

      let token = session?.user.value?.token;
      
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
          } else if (response.status == 403) {
            setAuthError(true);
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
