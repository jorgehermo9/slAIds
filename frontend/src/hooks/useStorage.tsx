import { useState } from "react";

const SERVICE_TOKEN_NAME = "serviceToken";

const setServiceToken = (serviceToken: string) =>
  sessionStorage.setItem(SERVICE_TOKEN_NAME, serviceToken);

const getServiceToken = () => sessionStorage.getItem(SERVICE_TOKEN_NAME);

const removeServiceToken = () => sessionStorage.removeItem(SERVICE_TOKEN_NAME);

export const useStorage = () => {
  const [token, setToken] = useState(getServiceToken);

  const setTokenWrapper = (serviceToken: string) => {
    setServiceToken(serviceToken);
  };

  return [token, setTokenWrapper, removeServiceToken];
};
