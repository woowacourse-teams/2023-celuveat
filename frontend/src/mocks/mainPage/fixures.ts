import type { ProfileData, RestaurantListData } from '~/@types/api.types';

export const mockCelebs = [
  {
    id: 1,
    name: '히밥',
    youtubeChannelName: '@heebab',
    profileImageUrl: 'http://naver.com',
  },
  {
    id: 2,
    name: '성시경',
    youtubeChannelName: '@sikung',
    profileImageUrl: 'http://nave2r.com',
  },
  {
    id: 3,
    name: '말랑',
    youtubeChannelName: '@mallang',
    profileImageUrl: 'http://naver3.com',
  },
];

export let mockRestaurantListData: RestaurantListData = {
  content: [
    {
      lat: 37.5036335,
      lng: 127.052218,
      id: 330,
      name: '배가무닭볶음탕',
      category: '닭볶음탕',
      roadAddress: '서울 강남구 선릉로86길 30 1층 배가무닭볶음탕',
      isLiked: false,
      phoneNumber: '0507-1476-9799',
      naverMapUrl: 'https://naver.me/xB4hssJg',
      distance: 269,
      celebs: [
        {
          id: 6,
          name: '야식이',
          youtubeChannelName: '@yasigi',
          profileImageUrl:
            'https://yt3.googleusercontent.com/ytc/AOPolaQRjK6t7fPPaQrOdApWYUmbltGkWiN6ZowfUQFCMg=s176-c-k-c0x00ffffff-no-rj',
        },
      ],
      images: [
        {
          id: 401,
          name: 'eWFzaWdpX-uwsOqwgOustOuLreuztuydjO2DlQ',
          author: '@yasigi',
          sns: '@yasigi',
        },
      ],
    },
    {
      lat: 37.5036994,
      lng: 127.0524166,
      id: 116,
      name: '60년전통신촌황소곱창 선릉직영점',
      category: '곱창,막창,양',
      roadAddress: '서울 강남구 선릉로86길 32',
      isLiked: true,
      phoneNumber: '02-553-6698',
      naverMapUrl: 'https://naver.me/5HSEqwkA',
      distance: 272,
      celebs: [
        {
          id: 1,
          name: '먹적 - (스시에 대출 박는 놈)',
          youtubeChannelName: '@monstergourmet',
          profileImageUrl:
            'https://yt3.googleusercontent.com/ytc/AOPolaQ0vUJt9JWhig6GY1lWLPt_qIRiH-cKgO5Nnl5uicQ=s176-c-k-c0x00ffffff-no-rj',
        },
      ],
      images: [
        {
          id: 47,
          name: 'bW9uc3RlcmdvdXJtZXRfNjDrhYTsoITthrXsi6DstIztmanshozqs7HssL1f7ISg66aJ7KeB7JiB7KCQ',
          author: '@monstergourmet',
          sns: '@monstergourmet',
        },
      ],
    },
    {
      lat: 37.5047177,
      lng: 127.0546534,
      id: 68,
      name: '숙성회장',
      category: '일식당',
      roadAddress: '서울 강남구 삼성로85길 32 동보빌딩 2층',
      isLiked: true,
      phoneNumber: '0507-1305-0805',
      naverMapUrl:
        'https://map.naver.com/v5/search/%EC%88%99%EC%84%B1%ED%9A%8C%EC%9E%A5/place/1459540460?entry=plt&c=15,0,0,0,dh&isCorrectAnswer=true',
      distance: 364,
      celebs: [
        {
          id: 3,
          name: '맛객리우A foodie',
          youtubeChannelName: '@Liwoo_foodie',
          profileImageUrl:
            'https://yt3.googleusercontent.com/KhKylYQTqpR3QQ1RuiYZ5xiM2fNsOJ_0jLFYbBBhk9Gh-zjpGTMUUSyPVGOHq4VZHzl6DN6qXQ=s176-c-k-c0x00ffffff-no-rj',
        },
      ],
      images: [
        {
          id: 150,
          name: 'bGl3b29f7IiZ7ISx7ZqM7J6l',
          author: '@Liwoo_foodie',
          sns: '@Liwoo_foodie',
        },
      ],
    },
    {
      lat: 37.5034458,
      lng: 127.0538707,
      id: 117,
      name: '단초식당',
      category: '육류,고기요리',
      roadAddress: '서울 강남구 역삼로69길 27 1층 (우)06197',
      isLiked: true,
      phoneNumber: '02-555-6450',
      naverMapUrl: 'https://place.map.kakao.com/1580574843',
      distance: 377,
      celebs: [
        {
          id: 1,
          name: '먹적 - (스시에 대출 박는 놈)',
          youtubeChannelName: '@monstergourmet',
          profileImageUrl:
            'https://yt3.googleusercontent.com/ytc/AOPolaQ0vUJt9JWhig6GY1lWLPt_qIRiH-cKgO5Nnl5uicQ=s176-c-k-c0x00ffffff-no-rj',
        },
      ],
      images: [
        {
          id: 48,
          name: 'bW9uc3RlcmdvdXJtZXRf64uo7LSI7Iud64u5',
          author: '@monstergourmet',
          sns: '@monstergourmet',
        },
      ],
    },
    {
      lat: 37.505202,
      lng: 127.0553917,
      id: 61,
      name: '스시아오마츠',
      category: '일식당',
      roadAddress: '서울 강남구 테헤란로78길 16 지하1층 12호',
      isLiked: true,
      phoneNumber: '0507-1421-4224',
      naverMapUrl:
        'https://map.naver.com/v5/search/%EC%8A%A4%EC%8B%9C%EC%95%84%EC%98%A4%EB%A7%88%EC%B8%A0/place/1051920261?entry=plt&c=15,0,0,0,dh&isCorrectAnswer=true',
      distance: 415,
      celebs: [
        {
          id: 3,
          name: '맛객리우A foodie',
          youtubeChannelName: '@Liwoo_foodie',
          profileImageUrl:
            'https://yt3.googleusercontent.com/KhKylYQTqpR3QQ1RuiYZ5xiM2fNsOJ_0jLFYbBBhk9Gh-zjpGTMUUSyPVGOHq4VZHzl6DN6qXQ=s176-c-k-c0x00ffffff-no-rj',
        },
      ],
      images: [
        {
          id: 143,
          name: 'bGl3b29f7Iqk7Iuc7JWE7Jik66eI7Lig',
          author: '@Liwoo_foodie',
          sns: '@Liwoo_foodie',
        },
      ],
    },
  ],
  totalPage: 1,
  currentPage: 0,
  pageSize: 18,
  totalElementsCount: 5,
  currentElementsCount: 5,
};

export const mockProfileData: ProfileData = {
  memberId: 1,
  nickname: '푸만능',
  profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
  oauthServer: 'google',
};
