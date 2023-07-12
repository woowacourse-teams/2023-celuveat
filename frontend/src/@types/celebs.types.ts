export interface Celeb {
  id: number;
  name: string;
  youtubeChannelName: string;
  subscriberCount: number;
  profileImageUrl: string;
}

export type Celebs = Celeb[];

export type CelebsSearchbarOption = Omit<Celeb, 'subscriberCount'>;
