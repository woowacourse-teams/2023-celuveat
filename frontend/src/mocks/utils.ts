// 사용자가 올린 이미지를 CDN에 저장, 그로 인한 url 변경하는 동작을 msw 코드로 구현
export const makeImage = (count: number) => {
  return Array.from({ length: count }).map(() => ({
    imgUrl: 'https://t1.daumcdn.net/cfile/tistory/224CEE3C577E3C7503',
    imgFile: 'asdf' as unknown as Blob,
  }));
};
