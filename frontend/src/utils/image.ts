import { SERVER_IMG_URL } from '~/constants/url';

const getImgUrl = (imgUrl: string, type: 'webp' | 'jpeg') => `${SERVER_IMG_URL}${type}/${imgUrl}.${type}`;

export default getImgUrl;
