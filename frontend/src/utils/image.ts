const serverImgUrl = 'https://www.celuveat.com/images-data/';

const getImgUrl = (imgUrl: string, type: 'webp' | 'jpeg') => `${serverImgUrl}${imgUrl}.${type}`;

export default getImgUrl;
