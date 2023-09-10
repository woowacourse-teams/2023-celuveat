export const getCookie = (name = 'JSESSIONID') => {
  const cookie = `; ${document.cookie}`;
  const parts = cookie.split(`; ${name}=`);

  return parts;
};

export const hasCookie = (name = 'JSESSIONID') => {
  const cookie = `; ${document.cookie}`;
  const parts = cookie.split(`; ${name}=`);

  return parts.length === 2;
};

export const isCookieExpired = (name = 'JSESSIONID') => {
  if (hasCookie(name)) {
    const expirationDate = new Date(getCookie(name)[1]);
    return expirationDate < new Date();
  }

  return true;
};
