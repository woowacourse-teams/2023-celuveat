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
  lng: number;
  phoneNumber: string;
  naverMapUrl: string;
  celebs: { id: number; name: string; youtubeChannelName: string; profileImageUrl: string }[];
  images: { id: number; name: string; author: string; sns: string }[];
}
