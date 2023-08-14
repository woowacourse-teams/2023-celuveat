import type { Meta, StoryObj } from '@storybook/react';
import RestaurantReviewItem from '~/components/RestaurantReviewItem';

const meta: Meta<typeof RestaurantReviewItem> = {
  title: 'RestaurantReviewItem',
  component: RestaurantReviewItem,
};

export default meta;

type Story = StoryObj<typeof RestaurantReviewItem>;

export const Default: Story = {
  args: {
    review: {
      id: 1,
      nickname: '푸만능',
      profileImageUrl: 'https://a0.muscache.com/im/pictures/user/93c7d7c8-86d9-4390-ba09-a8e6f4eb7f0f.jpg?im_w=240',
      content:
        'ㅁㄴ아ㅣ럼니ㅓ리ㅏㄴ멀;ㅣㄴ머ㅣㅋ틏ㅍㅊ퉄,ㅡ풐트,추파ㅓ투팥추카ㅣ파티커퍄ㅐ더ㅐ뱌ㅓㅈ댁ㄹ만ㅇ;ㅣㅏㅍ추키투픝ㅋ춮;ㅜ;ㅁㅇ나ㄴㅁ어리ㅏ넘아ㅣ러니마어리ㅏㅓㄴㅁ이ㅏ루팣ㅌ쿠팣투ㅏㅣㅌ추파ㅣㅁ누ㅐ품ㄴ애ㅑㅜㅐㅑㄹㅇ누먀ㅜ라ㅁㄴ어라ㅣㅁ넝리ㅏㅓㅁ니;아ㅓ리;먼이러ㅣㅁ나어리ㅏ니ㅏㅇㄴ마ㅓㄹ;ㅣㅁㄴ어리ㅏ;ㅁ넝리머닝러ㅣㄴㅁ어리ㅏ;ㄴ머이라ㅓ님어리;ㅁ너이ㅏ런미ㅏ어린마ㅓ리ㅏㄴ머;ㅣㅓㄴㅁ이러ㅣㄴ마ㅓ리먼이러;ㅁㅇㄴ럼ㄴ;ㅣㅓㄹ;ㅣㅁㄴㅣ',
      createdDate: '2023-08-13',
    },
  },
};
