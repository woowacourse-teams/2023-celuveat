import type { RestaurantData, RestaurantListData } from '~/@types/api.types';

export let restaurantListData: RestaurantListData = {
  content: [
    {
      id: 116,
      name: '60년전통신촌황소곱창 선릉직영점',
      category: '곱창,막창,양',
      roadAddress: '서울 강남구 선릉로86길 32',
      isLiked: true,
      lat: 37.5036994,
      lng: 127.0524166,
      naverMapUrl: 'https://naver.me/5HSEqwkA',
      phoneNumber: '02-553-6698',
      celebs: [
        {
          id: 1,
          name: '먹적 - (스시에 대출 박는 놈)',
          profileImageUrl:
            'https://yt3.googleusercontent.com/ytc/AOPolaQ0vUJt9JWhig6GY1lWLPt_qIRiH-cKgO5Nnl5uicQ=s176-c-k-c0x00ffffff-no-rj',
          youtubeChannelName: '@monstergourmet',
        },
      ],
      images: [
        {
          author: '@Liwoo_foodie',
          id: 143,
          name: 'bGl3b29f7Iqk7Iuc7JWE7Jik66eI7Lig.jpeg',
          sns: '@Liwoo_foodie',
        },
      ],
    },
    {
      id: 228,
      name: '그저 그런 식당',
      category: '일식',
      isLiked: false,
      roadAddress: '서울 강남구 선릉로86길 325',
      lat: 37.503,
      lng: 127.052,
      naverMapUrl: 'https://naver.me/5HSEqwkA',
      phoneNumber: '010-2123-2345',
      celebs: [
        {
          id: 1,
          name: '먹적 - (스시에 대출 박는 놈)',
          profileImageUrl:
            'https://yt3.googleusercontent.com/ytc/AOPolaQ0vUJt9JWhig6GY1lWLPt_qIRiH-cKgO5Nnl5uicQ=s176-c-k-c0x00ffffff-no-rj',
          youtubeChannelName: '@monstergourmet',
        },
      ],
      images: [
        {
          author: '@Liwoo_foodie',
          id: 143,
          name: 'bGl3b29f7Iqk7Iuc7JWE7Jik66eI7Lig.jpeg',
          sns: '@Liwoo_foodie',
        },
      ],
    },
  ],
  currentElementsCount: 4,
  currentPage: 0,
  pageSize: 18,
  totalElementsCount: 4,
  totalPage: 1,
};

export let restaurantWishList: RestaurantData[] = [];
