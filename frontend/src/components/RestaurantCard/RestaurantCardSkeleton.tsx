import { styled } from 'styled-components';
import ProfileImageSkeleton from '../@common/ProfileImage/ProfileImageSkeleton';
import { BORDER_RADIUS, paintSkeleton } from '~/styles/common';

function RestaurantCardSkeleton() {
  return (
    <StyledContainer>
      <StyledImage />
      <section>
        <StyledInfo>
          <StyledCategory />
          <StyledName />
          <StyledAddress />
          <StyledAddress />
        </StyledInfo>
        <StyledProfileImageSection>
          <ProfileImageSkeleton size={42} />
        </StyledProfileImageSection>
      </section>
    </StyledContainer>
  );
}

export default RestaurantCardSkeleton;

const StyledContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  gap: 0.8rem;

  width: 100%;
  height: 100%;

  & > section {
    display: flex;
    justify-content: space-between;
  }

  cursor: pointer;
`;

const StyledImage = styled.div`
  ${paintSkeleton}
  width: 100%;
  aspect-ratio: 1.05 / 1;

  object-fit: cover;

  border-radius: ${BORDER_RADIUS.md};
`;

const StyledInfo = styled.div`
  display: flex;
  flex: 1;
  flex-direction: column;
  gap: 0.4rem;

  position: relative;

  width: 100%;

  padding: 0.4rem;
`;

const StyledName = styled.h5`
  ${paintSkeleton}
  width: 100%;
  height: 20px;

  border-radius: ${BORDER_RADIUS.xs};
`;

const StyledAddress = styled.span`
  ${paintSkeleton}
  width: 50%;
  height: 12px;

  border-radius: ${BORDER_RADIUS.xs};
`;

const StyledCategory = styled.span`
  ${paintSkeleton}
  width: 40%;
  height: 12px;

  border-radius: ${BORDER_RADIUS.xs};
`;

const StyledProfileImageSection = styled.div`
  align-self: flex-end;
`;
