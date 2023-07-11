const getFilteredReviewCount = (reviewCount: number) => {
  const filteredReviewCount = reviewCount > 999 ? '999+' : String(reviewCount);

  return filteredReviewCount;
};

export default getFilteredReviewCount;
