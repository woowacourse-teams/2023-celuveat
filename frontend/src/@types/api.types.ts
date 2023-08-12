export interface RestaurantListData {
  content: RestaurantData[];
  currentElementsCount: number;
  currentPage: number;
  pageSize: number;
  totalElementsCount: number;
  totalPage: number;
}

export interface RestaurantData {
  id: number;
  name: string;
  category: string;
  roadAddress: string;
  isLiked?: boolean;
  lat: number;
  distance: number;
  lng: number;
  phoneNumber: string;
  naverMapUrl: string;
  celebs: { id: number; name: string; youtubeChannelName: string; profileImageUrl: string }[];
  images: { id: number; name: string; author: string; sns: string }[];
}


export interface ProfileData {
  nickname: string;
  profileImageUrl: string;
}

export interface RestaurantDetailData extends RestaurantData {
  likeCount: number;
  viewCount: number;
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

