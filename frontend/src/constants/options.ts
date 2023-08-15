import All from '~/assets/all.png';

export const OPTION_FOR_USER = [
  { id: 1, value: '로그아웃' },
  { id: 2, value: '위시리스트' },
  { id: 3, value: '회원 탈퇴' },
];

export const OPTION_FOR_NOT_USER = [{ id: 1, value: '로그인' }];

export const OPTION_FOR_CELEB_ALL = {
  id: -1,
  name: '전체',
  youtubeChannelName: '@all',
  profileImageUrl: All,
};
