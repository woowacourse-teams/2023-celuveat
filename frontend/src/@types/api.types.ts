import { Oauth } from '~/@types/oauth.types';
import { HyphenatedDate } from '~/@types/date.types';

export interface RestaurantListData {
  content: RestaurantData[];
  currentElementsCount: number;
  currentPage: number;
  pageSize: number;
  totalElementsCount: number;
  totalPage: number;
}

export interface RestaurantData {
  lat: number;
  lng: number;
  id: number;
  name: string;
  category: string;
  roadAddress: string;
  phoneNumber: string;
  naverMapUrl: string;
  viewCount: number;
  distance: number;
  isLiked: boolean;
  likeCount: number;
  celebs: { id: number; name: string; youtubeChannelName: string; profileImageUrl: string }[];
  images: { id: number; name: string; author: string; sns: string }[];
}

export type RestaurantWishData = Omit<RestaurantData, 'isLiked' | 'viewCount' | 'likeCount' | 'distance'>;

export interface ProfileData {
  memberId: number;
  nickname: string;
  profileImageUrl: string;
  oauthServer: Oauth;
}

export interface Video {
  videoId: number;
  youtubeVideoKey: string;
  uploadDate: string;
  celebId: number;
  name: string;
  youtubeChannelName: string;
  profileImageUrl: string;
}

export interface VideoList {
  content: Video[];
  totalPage: number;
  currentPage: number;
  pageSize: number;
  totalElementsCount: number;
  currentElementsCount: number;
}

export type StarRate = 0 | 0.5 | 1 | 1.5 | 2 | 2.5 | 3 | 3.5 | 4 | 4.5 | 5;

export type ReviewUploadImageType = {
  imgUrl: string;
  imgFile: Blob;
};

export interface RestaurantReview {
  id: number;
  nickname: string;
  memberId: number;
  profileImageUrl: string;
  content: string;
  createdDate: HyphenatedDate;
  isLiked: boolean;
  likeCount: number;
  rating: StarRate;
  reviewImageUrls: ReviewUploadImageType[];
}

export interface RestaurantReviewData {
  totalElementsCount: number;
  reviews: RestaurantReview[];
}

export interface RestaurantReviewPatchBody {
  content: string;
  rating: number;
}

export interface RestaurantReviewPostBody {
  content: string;
  restaurantId: number;
}
