export const BASE_URL = `${process.env.BASE_URL}`;

export const OAUTH_LINK = {
  google: `${BASE_URL}/api/oauth/google`,
  kakao: `${BASE_URL}/api/oauth/kakao`,
  naver: `${BASE_URL}/api/oauth/naver`,
};

export const OAUTH_BUTTON_MESSAGE = {
  google: '구글로 로그인하기',
  kakao: '카카오로 로그인하기',
  naver: '네이버로 로그인하기',
};
