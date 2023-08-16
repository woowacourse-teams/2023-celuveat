import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Instagram from '~/assets/icons/instagram.svg';
import Github from '~/assets/icons/github.svg';
import { FONT_SIZE } from '~/styles/common';

function Footer() {
  return (
    <StyledFooter>
      <StyledIntro>
        <p>셀럽들이 다녀간 맛집을 찾아보세요!</p>
        <p>셀럽 기반 맛집 탐색 플랫폼, 셀럽잇입니다.</p>
      </StyledIntro>
      <StyledContact>CONTACT: celuveat@gmail.com</StyledContact>
      <StyledLastLine>
        <div>COPYRIGHT © 2023 CELUVEAT ALL RIGHTS RESERVED</div>
        <StyledSNSLinkButtonList>
          <Link to="https://www.instagram.com/celuveat/" target="blank">
            <Instagram />
          </Link>
          <Link to="https://github.com/woowacourse-teams/2023-celuveat" target="blank">
            <Github />
          </Link>
        </StyledSNSLinkButtonList>
      </StyledLastLine>
    </StyledFooter>
  );
}

export default Footer;

const StyledFooter = styled.footer`
  display: flex;
  flex-direction: column;

  position: relative;

  width: 100%;

  padding: 2.4rem;

  background-color: var(--gray-1);

  font-size: ${FONT_SIZE.sm};
`;

const StyledIntro = styled.div`
  margin-bottom: 1.2rem;

  & > p {
    padding: 0.2rem 0;
  }
`;

const StyledContact = styled.div`
  margin-bottom: 1.2rem;
`;

const StyledLastLine = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledSNSLinkButtonList = styled.div`
  display: flex;
  gap: 0 1.6rem;

  & > * {
    cursor: pointer;
  }
`;
