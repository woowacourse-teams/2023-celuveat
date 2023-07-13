import { PropsWithChildren } from 'react';
import styled from 'styled-components';

interface MarqueeProps {
  width: number;
}

function Marquee({ width, children }: PropsWithChildren<MarqueeProps>) {
  return <StyledMarquee width={width}>{children}</StyledMarquee>;
}

const StyledMarquee = styled.div<MarqueeProps>`
  position: relative;

  width: ${({ width }) => `${width}px`};

  height: 31px;

  overflow: hidden;

  & > * {
    position: absolute;

    width: 100%;

    height: fit-content;
    margin: 0;

    line-height: 50px;

    text-align: center;

    white-space: nowrap;

    -moz-transform: translateX(100%);
    -webkit-transform: translateX(100%);
    -moz-animation: scroll-right 33s linear infinite;
    -webkit-animation: scroll-right 33s linear infinite;
    animation: scroll-right 33s linear infinite;
  }

  @-moz-keyframes scroll-right {
    0% {
      -moz-transform: translateX(0%);
    }
    100% {
      -moz-transform: translateX(-50%);
    }
  }

  @-webkit-keyframes scroll-right {
    0% {
      -webkit-transform: translateX(0%);
    }
    100% {
      -webkit-transform: translateX(-50%);
    }
  }

  @keyframes scroll-right {
    0% {
      -moz-transform: translateX(0%);
      -webkit-transform: translateX(0%);
      transform: translateX(0%);
    }

    100% {
      -moz-transform: translateX(-50%);
      -webkit-transform: translateX(-50%);
      transform: translateX(-50%);
    }
  }
`;

export default Marquee;
