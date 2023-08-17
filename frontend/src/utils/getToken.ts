interface TokenStateType {
  state: {
    token: string;
  };
}

const getToken = (): string => {
  const data = localStorage.getItem('CELUVEAT-STORAGE');

  if (!data) return '';

  const tokenState: TokenStateType = JSON.parse(localStorage.getItem('CELUVEAT-STORAGE') || '');

  return tokenState.state.token;
};

export default getToken;
