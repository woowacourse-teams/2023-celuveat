import { SERVER_IMG_URL } from '~/constants/url';

export const getImgUrl = (imgUrl: string, type: 'webp' | 'jpeg') => `${SERVER_IMG_URL}${type}/${imgUrl}.${type}`;

export const changeImgFileExtension = (fileName: string) => fileName.replace(/\.(png|jpeg|jpg)$/, '.webp');
