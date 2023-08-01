export const BASE_URL = `${process.env.BASE_URL}`;

const PROD = process.env.NODE_ENV === 'production';

export const SERVER_URL = PROD ? `${process.env.BASE_URL}/api/` : '/';

export const OAUTH_LINK = {
  google: `${process.env.BASE_URL}/api/oauth/google`,
  kakao: `${process.env.BASE_URL}/api/oauth/kakao`,
  naver: `${process.env.BASE_URL}/api/oauth/naver`,
};

export const OAUTH_BUTTON_MESSAGE = {
  google: '구글로 로그인하기',
  kakao: '카카오로 로그인하기',
  naver: '네이버로 로그인하기',
};
