const formatDateToKorean = (isoDateString: string) => {
  const date = new Date(isoDateString);

  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  
  const formattedDate = `${year}년 ${month}월 ${day}일`;
  return formattedDate;
};

export default formatDateToKorean;
