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
    createdDate: '2023-01-23',
  },
  {
    id: 2,
    nickname: '푸만능',
    memberId: 2,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: `꽃이 가득한 정원 사진에 반해서 예약했고 역시나 정말 만족스러운 1박이었습니다
    습한 날씨지만 숙소 안은 쾌적했고 아주 청결했어요
    데크 테이블과 정원 테이블에서의 티타임 역시 아주 좋았어요 사장님도 친절하시고 고양이 금자는 귀여워요 머리는 내주나 궁디팡팡은 하지말라하심 ㅋㅋ
    아지트삼아 사계절 모두 가보고싶은 곳입니다 ♥`,
    createdDate: '2022-01-23',
  },
  {
    id: 3,
    nickname: '도기',
    memberId: 3,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: '이 집 맛있네요33',
    createdDate: '2014-01-23',
  },
  {
    id: 4,
    nickname: '오도',
    memberId: 4,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: `꽃이 가득한 정원 사진에 반해서 예약했고 역시나 정말 만족스러운 1박이었습니다
    습한 날씨지만 숙소 안은 쾌적했고 아주 청결했어요
    데크 테이블과 정원 테이블에서의 티타임 역시 아주 좋았어요 사장님도 친절하시고 고양이 금자는 귀여워요 머리는 내주나 궁디팡팡은 하지말라하심 ㅋㅋ
    아지트삼아 사계절 모두 가보고싶은 곳입니다 ♥`,
    createdDate: '2023-01-23',
  },
  {
    id: 5,
    nickname: '푸만능',
    memberId: 5,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
  },
  {
    id: 6,
    nickname: '도기',
    memberId: 6,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: '이 집 맛있네요33',
    createdDate: '2014-01-23',
  },
  {
    id: 7,
    nickname: '오도',
    memberId: 7,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: `꽃이 가득한 정원 사진에 반해서 예약했고 역시나 정말 만족스러운 1박이었습니다
    습한 날씨지만 숙소 안은 쾌적했고 아주 청결했어요
    데크 테이블과 정원 테이블에서의 티타임 역시 아주 좋았어요 사장님도 친절하시고 고양이 금자는 귀여워요 머리는 내주나 궁디팡팡은 하지말라하심 ㅋㅋ
    아지트삼아 사계절 모두 가보고싶은 곳입니다 ♥`,
    createdDate: '2023-01-23',
  },
  {
    id: 8,
    nickname: '푸만능',
    memberId: 8,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/ef64c5d8-d439-4ad7-8564-27b70213555f.jpg?im_w=240',
    content: '이 집 맛있네요22',
    createdDate: '2022-01-23',
  },
  {
    id: 9,
    nickname: '도기',
    memberId: 9,
    profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
    content: '이 집 맛있네요33',
    createdDate: '2014-01-23',
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
