import { css, keyframes, styled } from 'styled-components';
import { shallow } from 'zustand/shallow';
import useToastState from '~/hooks/store/useToastState';
import useMediaQuery from '~/hooks/useMediaQuery';
import { BORDER_RADIUS, FONT_SIZE } from '~/styles/common';
import Portal from '../Portal';
import getImgUrl from '~/utils/image';

interface StyledToastProps {
  isSuccess?: boolean;
  isMobile: boolean;
}

function Toast() {
  const { isMobile } = useMediaQuery();
  const [text, isSuccess, image, isOpen, close] = useToastState(
    state => [state.text, state.isSuccess, state.image, state.isOpen, state.close],
    shallow,
  );

  return (
    <Portal close={close} isOpen={isOpen}>
      {isOpen && (
        <StyledToastWrapper isSuccess={isSuccess} isMobile={isMobile}>
          {image && (
            <picture>
              <StyledToastSource type="image/webp" srcSet={`${getImgUrl(image.url, 'webp')}`} />
              <StyledToastImg src={`${getImgUrl(image.url, 'jpeg')}`} alt={image.alt} />
            </picture>
          )}
          <StyledToastText>{text}</StyledToastText>
        </StyledToastWrapper>
      )}
    </Portal>
  );
}

export default Toast;

const styledToastImgVariable = css`
  width: 44px;
  height: 44px;

  border-radius: ${BORDER_RADIUS.sm};
`;

const StyledToastImg = styled.img`
  ${styledToastImgVariable}
`;

const StyledToastSource = styled.source`
  ${styledToastImgVariable}
`;

const StyledToastText = styled.span`
  color: var(--gary-4);
`;

const StyledToastWrapper = styled.div<StyledToastProps>`
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

  ${StyledToastImg} + ${StyledToastText} {
    margin-left: 1.2rem;
  }

  ${({ isSuccess }) =>
    isSuccess
      ? css`
          transform: translateX(-50%);
          animation: ${StyledViewToastAnimation} 2.5s forwards;
        `
      : css`
          text-align: center;

          opacity: 1;
          animation: ${StyledViewToastAnimation} 2.5s forwards;
        `}
`;

const StyledViewToastAnimation = keyframes`
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
