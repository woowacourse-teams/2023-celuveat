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

export const isExpired = (expirationDate: Date) => expirationDate < new Date();

export const isLogin = (name = 'JSESSIONID') => {
  if (hasCookie(name)) {
    const expirationDate = new Date(getCookie(name)[1]);
    return !isExpired(expirationDate);
  }

  return false;
};
