import styled, { css, keyframes } from 'styled-components';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';

interface StyledPopUpProps {
  isSuccess?: boolean;
  isMobile: boolean;
}

interface PopUpProps {
  text: string;
  imgUrl?: string;
  isSuccess?: boolean;
}

function PopUp({ text, imgUrl, isSuccess = false }: PopUpProps) {
  const { isMobile } = useMediaQuery();

  return (
    <StyledPopUpWrapper isSuccess={isSuccess} isMobile={isMobile}>
      {imgUrl && <StyledPopUpImg src={`${process.env.BASE_URL}/images-data/${imgUrl}`} alt="좋아요한 음식점" />}
      <StyledPopUpText>{text}</StyledPopUpText>
    </StyledPopUpWrapper>
  );
}

export default PopUp;

const StyledPopUpImg = styled.img`
  width: 44px;
  height: 44px;

  border-radius: ${BORDER_RADIUS.sm};
`;

const StyledPopUpText = styled.span`
  color: var(--gary-4);
`;

const StyledPopUpWrapper = styled.div<StyledPopUpProps>`
  display: flex;
  justify-content: flex-start;
  align-items: center;

  position: fixed;
  bottom: 0;
  left: ${({ isMobile }) => (isMobile ? '50%' : '160px')};
  z-index: 9999;

  min-width: 280px;
  max-width: fit-content;

  padding: 0.8rem 1.2rem 0.8rem 0.8rem;

  border: 1px solid var(--gray-2);
  border-radius: 8px;
  background: #fff;

  font-size: ${FONT_SIZE.sm};

  box-shadow: 0 3px 10px rgb(0 0 0 / 10%);

  text-align: center;

  ${StyledPopUpImg} + ${StyledPopUpText} {
    margin-left: 1.2rem;
  }

  ${({ isSuccess }) =>
    isSuccess
      ? css`
          transform: translateX(-50%);
          animation: ${StyledViewPopupAnimation} 2.5s forwards;
        `
      : css`
          text-align: center;

          opacity: 1;
          animation: ${StyledViewPopupAnimation} 2.5s forwards;
        `}
`;

const StyledViewPopupAnimation = keyframes`
  0% {
    opacity: 1;
    transform: translate(-50%, 0%);
  }

  20% {
    transform: translate(-50%, -100%);
  }

  80% {
    opacity: 0.9;
  }

  100% {
    opacity: 0;
    transform: translate(-50%, -100%);
    display: none;
  }
`;
