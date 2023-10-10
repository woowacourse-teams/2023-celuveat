// 사용자가 올린 이미지를 CDN에 저장, 그로 인한 url 변경하는 동작을 msw 코드로 구현
export const makeImage = (count: number) => {
  return Array.from({ length: count }).map(() => ({
    imgUrl: '4YSJ4YWz4YSP4YWz4YSF4YW14Yar4YSJ4YWj4Ya6IDIwMjMtMDktMjcg4YSL4YWp4YSS4YWuIDEyLjA2LjA4',
    imgFile: 'asdf' as unknown as Blob,
  }));
};
