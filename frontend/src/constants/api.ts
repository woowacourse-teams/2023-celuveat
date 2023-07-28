export const BASE_URL = `${window.location.protocol}//${process.env.BASE_URL}/api`;

export const OAUTH_LINK = {
  google: `${BASE_URL}/oauth/google`,
  kakao: `${BASE_URL}/oauth/kakao`,
  naver: `${BASE_URL}/oauth/naver`,
};

export const OAUTH_BUTTON_MESSAGE = {
  google: '구글로 로그인하기',
  kakao: '카카오로 로그인하기',
  naver: '네이버로 로그인하기',
};
