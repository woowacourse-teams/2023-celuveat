import type { Meta, StoryObj } from '@storybook/react';
import RestaurantReviewList from '~/components/RestaurantReviewList';
import type { RestaurantReview } from '~/@types/api.types';

const meta: Meta<typeof RestaurantReviewList> = {
  title: 'RestaurantReviewList',
  component: RestaurantReviewList,
};

export default meta;

type Story = StoryObj<typeof RestaurantReviewList>;

const reviews: RestaurantReview[] = [
  {
    id: 1,
    nickname: '오도',
    memberId: 1,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: `꽃이 가득한 정원 사진에 반해서 예약했고 역시나 정말 만족스러운 1박이었습니다
      습한 날씨지만 숙소 안은 쾌적했고 아주 청결했어요
      데크 테이블과 정원 테이블에서의 티타임 역시 아주 좋았어요 사장님도 친절하시고 고양이 금자는 귀여워요 머리는 내주나 궁디팡팡은 하지말라하심 ㅋㅋ
      아지트삼아 사계절 모두 가보고싶은 곳입니다 ♥`,
    createdDate: '2022-02-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 2,
    nickname: '푸만능',
    memberId: 2,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: `숙박에 대한 몇 마디.
      숙소는 좋지만 잠자는 것 외에는 비교적 적은 시간을 보냈습니다. 조용한 동네에 위치하여 상점과 식당까지 버스로 이동해야 합니다.
      방은 편안하고 크기가 적당했고, 공용 욕실은 깨끗하게 유지했습니다. 저는 5월 말에 머물렀기 때문에 아멜리아가 더위를 켤 때까지 숙소가 약간 쌀쌀했습니다.
      우리 둘 다 여행 중이었기 때문에 아멜리아와 비교적 적게 교류했지만, 그녀는 유쾌해 보였습니다. 숙소에는 두 명의 장기 숙박 게스트가 있었고, 둘 다 꽤 매력적이었습니다. 숙소에 친절한 반려동물도 몇 마리 있습니다.
      전반적으로 아멜리아의 숙소에서 긍정적인 경험을 했습니다.`,
    createdDate: '2022-01-25',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 3,
    nickname: '도기',
    memberId: 3,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: '이 집 맛있네요33',
    createdDate: '2014-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 4,
    nickname: '오도',
    memberId: 1,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: `객실은 작지만 매우 아늑하고, 침대가 정말 편안합니다. 머무는 동안 정말 잘 잤습니다! 상점과 큐 가든이 정말 가까운 정말 좋은 동네입니다. 아멜리아는 매우 친절하고 친절한 호스트이며 집 전체가 좋은 분위기를 자랑합니다. 그곳에서 정말 즐거운 시간을 보냈습니다!`,
    createdDate: '2023-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 5,
    nickname: '푸만능',
    memberId: 2,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 6,
    nickname: '푸만능',
    memberId: 2,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 7,
    nickname: '제레미',
    memberId: 4,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 8,
    nickname: '도담',
    memberId: 5,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
  {
    id: 9,
    nickname: '말랑',
    memberId: 6,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
    isLiked: false,
    likeCount: 97,
    rating: 5,
    images: [
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
      '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    ],
  },
];

export const Default: Story = {
  args: {
    reviews,
  },
};

export const Modal: Story = {
  args: {
    reviews,
    isModal: true,
  },
};
