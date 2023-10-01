interface FormDataObject {
  [key: string]: any;
}

// FormData 코드 디코딩하는 로직
export const parseFormDataForReview = (formDataString: string): FormDataObject => {
  const formDataArray: string[] = formDataString.split('\n').filter(line => line.trim() !== '');
  const formDataObject: FormDataObject = {};

  for (let i = 0; i < formDataArray.length; i++) {
    const line: string = formDataArray[i];
    const [name, value]: string[] = line.split(': ').map(item => item.trim());

    if (name === 'Content-Disposition') {
      const [, paramName]: RegExpMatchArray | null = value.match(/name="(.+?)"/);
      if (paramName) {
        i++;
        const paramValue: string = formDataArray[i].trim();
        formDataObject[paramName] = paramValue;
      }
    }
  }

  // 문자열 배열을 배열로 변환
  if (formDataObject['images']) {
    formDataObject['images'] = JSON.parse(formDataObject['images']);
  }

  // rate: 문자 -> 숫자
  if (formDataObject['rate']) {
    formDataObject['rate'] = parseInt(formDataObject['rate']);
  }

  // restaurantId: 문자 -> 숫자
  if (formDataObject['restaurantId']) {
    formDataObject['restaurantId'] = parseInt(formDataObject['restaurantId']);
  }

  return formDataObject;
};
