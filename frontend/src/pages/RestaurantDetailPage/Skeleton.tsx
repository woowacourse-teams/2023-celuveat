import styled, { css } from 'styled-components';
import ProfileImageSkeleton from '~/components/@common/ProfileImage/ProfileImageSkeleton';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BORDER_RADIUS, paintSkeleton } from '~/styles/common';

function Skeleton() {
  const { isMobile } = useMediaQuery();

  return (
    <StyledContainer isMobile={isMobile}>
      <StyledHeader tabIndex={0}>
        <div />
        <div />
      </StyledHeader>

      <StyledImageViewer />

      <StyledDetailInformation>
        <div>
          <div>
            <div />
            <div />
          </div>
          <ProfileImageSkeleton size={56} />
        </div>
        <StyledLine>
          <div />
          <div />
          <div />
        </StyledLine>
      </StyledDetailInformation>

      <StyledBox />
    </StyledContainer>
  );
}

export default Skeleton;

const StyledContainer = styled.main<{ isMobile: boolean }>`
  display: flex;
  flex-direction: column;

  height: 100vh;

  ${({ isMobile }) =>
    isMobile
      ? css`
          position: sticky;

          margin: 0 1.2rem 2rem;
        `
      : css`
          max-width: 1240px;

          margin: 0 auto 8rem;
        `}
`;

const StyledHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem 0;

  padding: 1.2rem 0 2.4rem;

  & > div:first-child {
    ${paintSkeleton}
    width: 70%;
    height: 32px;

    border-radius: ${BORDER_RADIUS.xs};
  }

  & > div:last-child {
    ${paintSkeleton}
    width: 30%;
    height: 24px;

    border-radius: ${BORDER_RADIUS.xs};
  }
`;

const StyledImageViewer = styled.div`
  ${paintSkeleton}
  height: calc((100vw - 2.4rem) * 0.9);

  padding: 2.4rem 0;

  border-radius: ${BORDER_RADIUS.md};
`;

const StyledDetailInformation = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  padding: 4.8rem 0;

  & > div {
    display: flex;
    justify-content: space-between;

    & > div {
      display: flex;
      flex-direction: column;
      gap: 1.2rem;
    }

    & > div > div:first-child {
      ${paintSkeleton}
      height: 24px;

      border-radius: ${BORDER_RADIUS.xs};
    }

    & > div > div:last-child {
      ${paintSkeleton}
      height: 20px;

      border-radius: ${BORDER_RADIUS.xs};
    }
  }
`;

const StyledLine = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;

  padding: 4.8rem 0;

  border-top: 1px solid var(--gray-2);
  border-bottom: 1px solid var(--gray-2);

  & > div {
    ${paintSkeleton}
    height: 24px;

    border-radius: ${BORDER_RADIUS.xs};
  }
`;

const StyledBox = styled.div`
  ${paintSkeleton}
  height: 120px;

  border-radius: ${BORDER_RADIUS.md};
`;
