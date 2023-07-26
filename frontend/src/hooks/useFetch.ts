type FetchMethod = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

const useFetch = (endPoint: string) => {
  const baseUrl = 'http://3.35.157.27:8080/api/';

  const handleFetch = async ({
    method = 'GET',
    body = {},
    queryString = '',
  }: {
    method?: FetchMethod;
    body?: object;
    queryString?: string;
  }) => {
    const url = `${baseUrl}${endPoint}${queryString && `?${queryString}`}`;
    const response = method === 'GET' ? await fetch(url) : await fetch(url, { method, body: JSON.stringify(body) });

    return response.json();
  };

  return { handleFetch };
};

export default useFetch;
