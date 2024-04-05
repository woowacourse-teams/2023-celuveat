export const getRandomResponse = <T>(successResponse: T, failureResponse: T): T => {
  const probability = Math.floor(Math.random() * 10) + 1;

  if (probability <= 8) {
    return failureResponse;
  }

  return successResponse;
};
