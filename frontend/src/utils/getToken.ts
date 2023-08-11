interface TokenStateType {
  state: {
    token: string;
  };
}

const getToken = (): string => {
  const tokenState: TokenStateType = JSON.parse(localStorage.getItem('CELUVEAT-STORAGE') || '');

  return tokenState.state.token;
};

export default getToken;
