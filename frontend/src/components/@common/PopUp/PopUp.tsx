import styled, { css, keyframes } from 'styled-components';
import useMediaQuery from '~/hooks/useMediaQuery';
import { FONT_SIZE } from '~/styles/common';

interface StyledPopUpProps {
  isSuccess?: boolean;
  isMobile: boolean;
}

interface PopUpProps {
  text: string;
  imgUrl: string;
  isSuccess?: boolean;
}

function PopUp({ text, isSuccess = false, imgUrl }: PopUpProps) {
  const { isMobile } = useMediaQuery();

  return (
    <StyledPopUpWrapper isSuccess={isSuccess} isMobile={isMobile}>
      <StyledPopUpImg src={`${process.env.BASE_URL}/images-data/${imgUrl}`} alt="좋아요한 음식점" />
      <StyledPopUpText>{text}</StyledPopUpText>
    </StyledPopUpWrapper>
  );
}

export default PopUp;

const StyledPopUpImg = styled.img`
  width: 36px;
  height: 36px;
`;

const StyledPopUpText = styled.span`
  color: var(--gary-4);
`;

const StyledPopUpWrapper = styled.div<StyledPopUpProps>`
  display: flex;
  align-items: center;

  position: fixed;
  top: 95%;
  left: ${({ isMobile }) => (isMobile ? '50%' : '10%')};
  z-index: 9999;

  width: 224px;
  height: 64px;

  padding: 1rem;

  border: 1px solid var(--gray-2);
  border-radius: 8px;
  background: #fff;

  font-size: ${FONT_SIZE.md};

  box-shadow: var(--shadow);

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
