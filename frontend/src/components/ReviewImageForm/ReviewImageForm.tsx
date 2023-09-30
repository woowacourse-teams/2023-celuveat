import { styled } from 'styled-components';
import ImageForm from '~/components/@common/ImageForm';
import { FONT_SIZE } from '~/styles/common';

import DeleteIcon from '~/assets/icons/exit.svg';

interface ReviewImageFormProps {
  images?: string[];
  upload: React.ChangeEventHandler<HTMLInputElement>;
  deleteImage: (id: number) => void;
}

function ReviewImageForm({ images, upload, deleteImage }: ReviewImageFormProps) {
  const hasImage = images.length > 0;

  return (
    <StyledReviewImageFormWrapper>
      <ImageForm onChange={upload} />
      {hasImage &&
        images.map((image, id) => (
          <StyledImageWrapper>
            <StyledImage src={image} alt="PREVIEW" />
            <StyledDeleteButton type="button" onClick={() => deleteImage(id)}>
              <DeleteIcon />
            </StyledDeleteButton>
          </StyledImageWrapper>
        ))}
    </StyledReviewImageFormWrapper>
  );
}

export default ReviewImageForm;

const StyledImageWrapper = styled.div`
  position: relative;

  font-size: ${FONT_SIZE.lg};
`;

const StyledReviewImageFormWrapper = styled.div`
  display: flex;
`;

const StyledImage = styled.img`
  width: 120px;
  height: 120px;

  border-radius: 20px;

  object-fit: cover;
`;

const StyledDeleteButton = styled.button`
  position: absolute;
  top: 5px;
  right: 5px;

  border: none;
  background-color: transparent;
`;
