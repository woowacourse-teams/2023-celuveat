import { ChangeEvent, MouseEvent, useRef, useState } from 'react';
import styled, { css } from 'styled-components';
import imageCompression from 'browser-image-compression';
import { Link, useNavigate } from 'react-router-dom';
import { useMutation } from '@tanstack/react-query';
import ReviewImageForm from '~/components/ReviewImageForm';
import { FONT_SIZE } from '~/styles/common';
import { changeImgFileExtension } from '~/utils/image';
import TextButton from '~/components/@common/Button';
import { postEventForm } from '~/api/event';
import useMediaQuery from '~/hooks/useMediaQuery';

const options = {
  maxSizeMB: 1,
  maxWidthOrHeight: 350,
  useWebWorker: true,
};

interface BlobType extends Blob {
  name?: string;
}

function EventFormPage() {
  const [images, setImages] = useState<string[]>([]);
  const [files, setFiles] = useState<BlobType[]>([]);
  const { isMobile } = useMediaQuery();
  const instagramIdRef = useRef<string>('');
  const restaurantNameRef = useRef<string>('');
  const navigate = useNavigate();

  const { mutate } = useMutation(postEventForm, {
    onSuccess: () => {
      alert('이벤트 참가완료!');
      navigate('/');
    },
    onError: () => alert('알 수 없는 오류가 발생하였습니다. 잠시 후에 다시 시도해주세요!'),
  });

  const onUploadImage: React.ChangeEventHandler<HTMLInputElement> = async e => {
    const imageFile = e.target.files[0];

    const blob = new Blob([imageFile], { type: 'image/webp' });
    const webpFile = new File([blob], changeImgFileExtension(imageFile.name), { type: 'image/webp' });

    try {
      const compressedFile = await imageCompression(webpFile, options);
      const compressedImageUrl = URL.createObjectURL(compressedFile);

      setImages([...images, compressedImageUrl]);
      setFiles([...files, compressedFile]);
    } catch (error) {
      setImages([]);
    }
  };

  const deleteImage = (reviewImageId: number) => {
    setFiles(prev => prev.filter((_, id) => id !== reviewImageId));
    setImages(prev => prev.filter((_, id) => id !== reviewImageId));
  };

  const submitForm = (e: MouseEvent) => {
    e.preventDefault();
    const formData = new FormData();

    files.forEach(file => {
      formData.append('images', file, file.name);
    });
    formData.append('instagramId', instagramIdRef.current);
    formData.append('restaurantName', restaurantNameRef.current);

    mutate(formData);
  };

  const onChangeInstagramIdHandler = (e: ChangeEvent<HTMLInputElement>) => {
    instagramIdRef.current = e.target.value;
  };

  const onChangeRestaurantNameHandler = (e: ChangeEvent<HTMLInputElement>) => {
    restaurantNameRef.current = e.target.value;
  };

  return (
    <StyledContainer isMobile={isMobile}>
      <StyledLink to="/event">
        <h3>← 사진 등록 이벤트 참여하기</h3>
      </StyledLink>
      <StyledSection>
        <h4>인스타아이디</h4>
        <StyledDescription>사진이 활용되면 해당 계정으로 DM을 보내드립니다.</StyledDescription>
        <StyledInput placeholder="ex) celuveat" onChange={onChangeInstagramIdHandler} />
      </StyledSection>
      <StyledSection>
        <h4>음식점 이름</h4>
        <StyledDescription>사진에 해당하는 음식점 명을 입력해주세요</StyledDescription>
        <StyledInput placeholder="ex) 꿉당 성수점" onChange={onChangeRestaurantNameHandler} />
      </StyledSection>
      <StyledSection>
        <h4>사진 등록하기</h4>
        <ReviewImageForm images={images} upload={onUploadImage} deleteImage={deleteImage} limitLength={10} />
      </StyledSection>
      <TextButton type="button" onClick={submitForm} colorType="dark" text="제출하기" />
    </StyledContainer>
  );
}

export default EventFormPage;

const StyledContainer = styled.div<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;
  gap: 4.8rem;

  width: 100%;
  max-width: 1240px;
  min-height: 100vh;

  margin: 0 auto;

  ${({ isMobile }) =>
    isMobile
      ? css`
          padding: 2rem 1.2rem 8rem;
        `
      : css`
          padding: 2rem;
        `}
`;

const StyledSection = styled.section`
  display: flex;
  flex-direction: column;
  gap: 1.4rem;

  width: 100%;

  font-size: ${FONT_SIZE.md};
`;

const StyledInput = styled.input`
  padding: 1.2rem;

  border: none;
  border-bottom: 1px solid black;
`;

const StyledDescription = styled.div`
  color: var(--gray-3);
`;

const StyledLink = styled(Link)`
  text-decoration: none;
`;
