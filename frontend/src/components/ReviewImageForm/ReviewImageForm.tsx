import { styled } from 'styled-components';
import ImageForm from '~/components/@common/ImageForm';

interface ReviewImageFormProps {
  images: string[];
}

function ReviewImageForm({ images }: ReviewImageFormProps) {
  return (
    <StyledReviewImageFormWrapper>
      <ImageForm
        onChange={() => {
          /*
          추가하는 함수
         */
        }}
      />
      {images.map(image => (
        <StyledImage src={image} alt="PREVIEW" />
      ))}
    </StyledReviewImageFormWrapper>
  );
}

export default ReviewImageForm;

const StyledReviewImageFormWrapper = styled.div`
  display: flex;
`;

const StyledImage = styled.img`
  width: 120px;
  height: 120px;

  border-radius: 20px;

  object-fit: cover;
`;
